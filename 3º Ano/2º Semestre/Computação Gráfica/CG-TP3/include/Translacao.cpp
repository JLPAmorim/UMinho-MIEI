#include "Translacao.h"
#include <math.h>

#define CC 4

Translacao::Translacao() {
    this->x = this->y = this->z = this->tempo = 0.0;
    cima[0] = 0.0;
    cima[1] = 1.0;
    cima[2] = 0.0;

}

Translacao::Translacao(float x, float y, float z, float t, std::vector<Point> trans) {
    this->x = x;
    this->y = y;
    this->z = z;
    this->tempo = t;
    this->trans = trans;
};

// Matriz de Catmull-Rom
float catmullMatrix[CC][CC] = { { -0.5f,  1.5f, -1.5f,  0.5f },
                                { 1.0f, -2.5f,  2.0f, -0.5f },
                                { -0.5f,  0.0f,  0.5f,  0.0f },
                                { 0.0f,  1.0f,  0.0f,  0.0f } };




// multiplicação de uma matriz por um vetor
void Translacao::multMatrixVector(float* m, float* v, float* res)
{
    for (int j = 0; j < CC; ++j) {
        res[j] = 0;

        for (int k = 0; k < CC; ++k) {
            res[j] += v[k] * m[j * CC + k];
        }
    }
}

void Translacao::getCatmullRomPoint(float t, float* p0, float* p1, float* p2, float* p3, float* pos, float* deriv)
{
    // Calcula A = M * P

    float ax[CC];
    float ay[CC];
    float az[CC];

    float px[CC] = { p0[0], p1[0], p2[0], p3[0] };
    float py[CC] = { p0[1], p1[1], p2[1], p3[1] };
    float pz[CC] = { p0[2], p1[2], p2[2], p3[2] };

    float T[CC] = { t * t * t, t * t, t, 1 };
    float TDeriv[CC] = { 3 * (t * t), 2 * t, 1, 0 };

    multMatrixVector((float*)catmullMatrix, px, ax);
    multMatrixVector((float*)catmullMatrix, py, ay);
    multMatrixVector((float*)catmullMatrix, pz, az);

    // Calcula pos = T * A

    pos[0] = (T[0] * ax[0]) + (T[1] * ax[1]) + (T[2] * ax[2]) + (T[3] * ax[3]);
    pos[1] = (T[0] * ay[0]) + (T[1] * ay[1]) + (T[2] * ay[2]) + (T[3] * ay[3]);
    pos[2] = (T[0] * az[0]) + (T[1] * az[1]) + (T[2] * az[2]) + (T[3] * az[3]);

    // Calcula deriv = T' * A

    deriv[0] = (TDeriv[0] * ax[0]) + (TDeriv[1] * ax[1]) + (TDeriv[2] * ax[2]) + (TDeriv[3] * ax[3]);
    deriv[1] = (TDeriv[0] * ay[0]) + (TDeriv[1] * ay[1]) + (TDeriv[2] * ay[2]) + (TDeriv[3] * ay[3]);
    deriv[2] = (TDeriv[0] * az[0]) + (TDeriv[1] * az[1]) + (TDeriv[2] * az[2]) + (TDeriv[3] * az[3]);
}


// Para um instante de tempo, devolve as coordenadas globais de um ponto da curva  Catmull-Rom
// gt tempo global
// pos vetor com coordenadas a serem retornadas
// deriv vetor da tangente ás coordenadas calculadas
void Translacao::getGlobalCatmullRomPoint(float gt, float* pos, float* deriv)
{
    size_t numPoints = trans.size();
    int indexes[4];

    float t = gt * numPoints;
    int index = floor(t);

    // coordenadas que definem a curva Catmull-Rom
    // para depois calcular o ponto intermédio
    float p[12];

    t = t - index;

    indexes[0] = (index + numPoints - 1) % numPoints;
    indexes[1] = (indexes[0] + 1) % numPoints;
    indexes[2] = (indexes[1] + 1) % numPoints;
    indexes[3] = (indexes[2] + 1) % numPoints;

    for (size_t i = 0; i < 4; i++) {

        // cálculo das coordanas da curva
        p[i * 3] = trans.at(indexes[i]).getX();
        p[i * 3 + 1] = trans.at(indexes[i]).getY();
        p[i * 3 + 2] = trans.at(indexes[i]).getZ();
    }

    getCatmullRomPoint(t, p, p + 3, p + 6, p + 9, pos, deriv);
}

std::vector<Point> Translacao::geraPontosCurva() {
    float res[3];
    float deriv[3];
    float t;

    for (t = 0; t < 1; t += 0.01) {

        getGlobalCatmullRomPoint(t, res, deriv);
        Point p = Point(res[0], res[1], res[2]);
        pontosCatmull.push_back(p);
    }
    return pontosCatmull;
}

void buildRotMatrix(float* x, float* y, float* z, float* m) {

    m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
    m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
    m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
    m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}


void cross(float* a, float* b, float* res) {

    res[0] = a[1] * b[2] - a[2] * b[1];
    res[1] = a[2] * b[0] - a[0] * b[2];
    res[2] = a[0] * b[1] - a[1] * b[0];
}


void normalize(float* a) {

    float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0] / l;
    a[1] = a[1] / l;
    a[2] = a[2] / l;
}



void Translacao::run() {
    if ((int)tempo == 0) {
        glTranslatef(x, y, z);
    }
    else {
        geraPontosCurva();
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < pontosCatmull.size(); ++i) {
            glVertex3f(pontosCatmull[i].getX(), pontosCatmull[i].getY(), pontosCatmull[i].getZ());
        }
        glEnd();

        float res[3];
        float deriv[3];
        float m[4][4];
        float Zi[3];
        float Yi[3] = { 0,1,0 };

        float tAux = glutGet(GLUT_ELAPSED_TIME) % ((int)tempo * 1000);
        float t = tAux / (tempo * 1000);
        getGlobalCatmullRomPoint(t, res, deriv);
        glTranslatef(res[0], res[1], res[2]);

        normalize(deriv);
        cross(deriv, Yi, Zi);
        normalize(Zi);
        cross(Zi, deriv, Yi);
        normalize(Yi);
        buildRotMatrix(deriv, Yi, Zi, (float*)m);
        glMultMatrixf((float*)m);
    }

}