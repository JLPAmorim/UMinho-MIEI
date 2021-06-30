#include "generator.h"

Point::Point() {
    x = 0;
    y = 0;
    z = 0;
}

Point::Point(float a, float b, float c) {
    x = a;
    y = b;
    z = c;
}

Point2d::Point2d() {
    this->u = this->v = 0.0;
}

Point2d::Point2d(float a, float b) {
    this->u = a;
    this->v = b;

}



// Algoritmo para a criação da primitiva plane
void plano(float lx, string fich){
    float x;
    
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);


    // Divisão do lado para ficar centrado na origem
    x = lx/2;

    // Triangulo 1 
    file << "" << x << " 0 " << x << "\n";
    file << "" << x << " 0 " << -x << "\n";
    file << "" << -x << " 0 " << -x << "\n";

    // Triangulo 2 
    file << "" << -x << " 0 " << x << "\n";
    file << "" << x << " 0 " << x << "\n";
    file << "" << -x << " 0 " << -x << "\n";

    file << "normais" << "\n";
    file << 0 << " " << 1 << " " << 0 << "\n";
    file << 0 << " " << 1 << " " << 0 << "\n";
    file << 0 << " " << 1 << " " << 0 << "\n";
    file << 0 << " " << 1 << " " << 0 << "\n";
    file << 0 << " " << 1 << " " << 0 << "\n";
    file << 0 << " " << 1 << " " << 0 << "\n";
    file << "texturas" << "\n";
    file << 0 << " " << 0 << "\n";
    file << 0 << " " << 1 << "\n";
    file << 1 << " " << 1 << "\n";
    file << 0 << " " << 0 << "\n";
    file << 1 << " " << 1 << "\n";
    file << 1 << " " << 0 << "\n";

    file.close();
}


// Algoritmo para a criação da primitiva box
void box(float c, float l, float a, int div, string fich){
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    // Definição das variáveis para desenho das faces
    float xx = c / -2;
    float yy = l / -2;
    float zz = a / -2;

    float espC = c / div;
    float espL = l / div;
    float espA = a / div;

    // Definição das variáveis para texturas
    vector<Point> normal;
    vector<Point2d> text;

    float textX = 0.25 / div;
    float textY = (1.0f / 3.0f) / div;
 
    // Face da frente e de tras

    float xfrente = 0.25;
    float yfrente = (1.0f / 3.0f);
    float xtras = 0.75;
    float ytras = (1.0f / 3.0f);

    for (int i = 0; i < div; i++) {

        for (int j = 0; j < div; j++ ) {

            // Face da frente
            file << xx + espC * j << ", " << yy + espL * (i + 1) << ", " << -zz << "\n";
            file << xx + espC * (j) << ", " << yy + espL * (i) << ", " << -zz << "\n";
            file << xx + espC * (j + 1) << ", " << yy + espL * (i) << ", " << -zz << "\n";
            file << xx + espC * j << ", " << yy + espL * (i + 1) << ", " << -zz << "\n";
            file << xx + espC * (j + 1) << ", " << yy + espL * (i) << ", " << -zz << "\n";
            file << xx + espC * (j + 1) << ", " << yy + espL * (i + 1) << ", " << -zz << "\n";

            //normal
            normal.push_back(Point(0, 0, 1));
            normal.push_back(Point(0, 0, 1));
            normal.push_back(Point(0, 0, 1));
            normal.push_back(Point(0, 0, 1));
            normal.push_back(Point(0, 0, 1));
            normal.push_back(Point(0, 0, 1));

            //texturas
            text.push_back(Point2d(xfrente, yfrente + textY));
            text.push_back(Point2d(xfrente, yfrente));
            text.push_back(Point2d(xfrente + textX, yfrente));

            text.push_back(Point2d(xfrente, yfrente + textY));
            text.push_back(Point2d(xfrente + textX, yfrente));
            text.push_back(Point2d(xfrente + textX, yfrente + textY));

            // Face de tras
            file << xx + espC * j << ", " << yy + espL * (i + 1) << ", " << zz << "\n";
            file << xx + espC * (j + 1) << ", " << yy + espL * (i) << ", " << zz << "\n";
            file << xx + espC * (j) << ", " << yy + espL * (i) << ", " << zz << "\n";
            file << xx + espC * j << ", " << yy + espL * (i + 1) << ", " << zz << "\n";
            file << xx + espC * (j + 1) << ", " << yy + espL * (i + 1) << ", " << zz << "\n";
            file << xx + espC * (j + 1) << ", " << yy + espL * (i) << ", " << zz << "\n";

            //normal
            normal.push_back(Point(0, 0, -1));
            normal.push_back(Point(0, 0, -1));
            normal.push_back(Point(0, 0, -1));
            normal.push_back(Point(0, 0, -1));
            normal.push_back(Point(0, 0, -1));
            normal.push_back(Point(0, 0, -1));

            //texturas
            text.push_back(Point2d(xtras + textX, ytras + textY));
            text.push_back(Point2d(xtras, ytras));
            text.push_back(Point2d(xtras + textX, ytras));

            text.push_back(Point2d(xtras + textX, ytras + textY));
            text.push_back(Point2d(xtras, ytras + textY));
            text.push_back(Point2d(xtras, ytras));

            xfrente += textX;
            xtras += textX;
        }
        yfrente += textY;
        ytras += textY;
    }

    // Face de cima e de baixo
    float xcima = 0.25;
    float ycima = (2.0f / 3.0f);
    float xbaixo = 0.25;
    float ybaixo = 0;

    for (int i = 0; i < div; i++) {

        for (int j = 0; j < div; j ++) {

            // Face da cima
            file << xx + espC * (j + 1) << ", " << -yy << ", " << zz + espA * i << "\n";
            file << xx + espC * j << ", " << -yy << ", " << zz + espA * i << "\n";
            file << xx + espC * (j) << ", " << -yy << ", " << zz + espA * (i + 1) << "\n";
            file << xx + espC * (j + 1) << ", " << -yy << ", " << zz + espA * i << "\n";
            file << xx + espC * j << ", " << -yy << ", " << zz + espA * (i + 1) << "\n";
            file << xx + espC * (j + 1) << ", " << -yy << ", " << zz + espA * (i + 1) << "\n";

            //normal
            normal.push_back(Point(0, 1, 0));
            normal.push_back(Point(0, 1, 0));
            normal.push_back(Point(0, 1, 0));
            normal.push_back(Point(0, 1, 0));
            normal.push_back(Point(0, 1, 0));
            normal.push_back(Point(0, 1, 0));

            //texturas
            text.push_back(Point2d(xcima + textX, ycima + textY));
            text.push_back(Point2d(xcima, ycima + textY));
            text.push_back(Point2d(xcima, ycima));

            text.push_back(Point2d(xcima + textX, xcima + textX));
            text.push_back(Point2d(xcima, ycima));
            text.push_back(Point2d(xcima + textX, ycima));

            // Face de baixo
            file << xx + espC * (j + 1) << ", " << yy << ", " << zz + espA * i << "\n";
            file << xx + espC * (j) << ", " << yy << ", " << zz + espA * (i + 1) << "\n";
            file << xx + espC * j << ", " << yy << ", " << zz + espA * i << "\n";
            file << xx + espC * (j + 1) << ", " << yy << ", " << zz + espA * i << "\n";
            file << xx + espC * (j + 1) << ", " << yy << ", " << zz + espA * (i + 1) << "\n";
            file << xx + espC * j << ", " << yy << ", " << zz + espA * (i + 1) << "\n";

            //normal
            normal.push_back(Point(0, -1, 0));
            normal.push_back(Point(0, -1, 0));
            normal.push_back(Point(0, -1, 0));
            normal.push_back(Point(0, -1, 0));
            normal.push_back(Point(0, -1, 0));
            normal.push_back(Point(0, -1, 0));

            //texturas
            text.push_back(Point2d(xbaixo + textX, ybaixo));
            text.push_back(Point2d(xbaixo, ybaixo + textY));
            text.push_back(Point2d(xbaixo, ybaixo));

            text.push_back(Point2d(xbaixo + textX, ybaixo));
            text.push_back(Point2d(xbaixo + textX, ybaixo + textY));
            text.push_back(Point2d(xbaixo, ybaixo + textY));

            xcima += textX;
            xbaixo += textX;
        }
        ycima += textY;
        ybaixo += textY;
    }

    // Face da esquerda e da direita

    float xesquerda = 0;
    float yesquerda = (1.0f / 3.0f);
    float xdireita = 0.5;
    float ydireita = (1.0f / 3.0f);

    for (int i = 0; i < div; i++) {

        for (int j = 0; j < div; j++){

            // Face da esquerda
            file << xx << ", " << yy + espL * (i + 1) << ", " << zz + espA * j << "\n";
            file << xx << ", " << yy + espL * (i) << ", " << zz + espA * j << "\n";
            file << xx << ", " << yy + espL * (i) << ", " << zz + espA * (j + 1) << "\n";
            file << xx << ", " << yy + espL * (i + 1) << ", " << zz + espA * j << "\n";
            file << xx << ", " << yy + espL * (i) << ", " << zz + espA * (j + 1) << "\n";
            file << xx << ", " << yy + espL * (i + 1) << ", " << zz + espA * (j + 1) << "\n";

            normal.push_back(Point(-1, 0, 0));
            normal.push_back(Point(-1, 0, 0));
            normal.push_back(Point(-1, 0, 0));
            normal.push_back(Point(-1, 0, 0));
            normal.push_back(Point(-1, 0, 0));
            normal.push_back(Point(-1, 0, 0));

            //texturas
            text.push_back(Point2d(xesquerda, yesquerda + textY));
            text.push_back(Point2d(xesquerda, yesquerda));
            text.push_back(Point2d(xesquerda + textX, yesquerda));

            text.push_back(Point2d(xesquerda, yesquerda + textY));
            text.push_back(Point2d(xesquerda + textX, yesquerda));
            text.push_back(Point2d(xesquerda + textX, yesquerda + textY));

            // Face da direita
            file << -xx << ", " << yy + espL * (i + 1) << ", " << zz + espA * j << "\n";
            file << -xx << ", " << yy + espL * (i) << ", " << zz + espA * (j + 1) << "\n";
            file << -xx << ", " << yy + espL * (i) << ", " << zz + espA * j << "\n";
            file << -xx << ", " << yy + espL * (i + 1) << ", " << zz + espA * j << "\n";
            file << -xx << ", " << yy + espL * (i + 1) << ", " << zz + espA * (j + 1) << "\n";
            file << -xx << ", " << yy + espL * (i) << ", " << zz + espA * (j + 1) << "\n";

            //normal
            normal.push_back(Point(1, 0, 0));
            normal.push_back(Point(1, 0, 0));
            normal.push_back(Point(1, 0, 0));
            normal.push_back(Point(1, 0, 0));
            normal.push_back(Point(1, 0, 0));
            normal.push_back(Point(1, 0, 0));

            //texturas
            text.push_back(Point2d(xdireita + textX, ydireita + textY));
            text.push_back(Point2d(xdireita, ydireita));
            text.push_back(Point2d(xdireita + textX, ydireita));

            text.push_back(Point2d(xdireita + textX, ydireita + textY));
            text.push_back(Point2d(xdireita, ydireita + textY));
            text.push_back(Point2d(xdireita, ydireita));

            xesquerda += textX;
            xdireita += textX;
        }
        ydireita += textY;
        yesquerda += textY;
    
    }

    file << "normais" << "\n";
    for (int i = 0; i < normal.size(); i++) {
        file << normal[i].getX() << " " << normal[i].getY() << " " << normal[i].getZ() << "\n";
    }

    file << "texturas" << "\n";
    for (int i = 0; i < text.size(); i++) {
        file << text[i].getU() << " " << text[i].getV() << "\n";
    }
    file.close();
}


// Algoritmo para a criacao da primitiva cone
void cone(float r, float a, int slices, int cH, string fich){
    
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    float x1, x2, x3, y1, y2, y3, z1, z2, z3;
    float x4, x5, x6, y4, y5, y6, z4, z5, z6;
    float x7, x8, x9, y7, y8, y9, z7, z8, z9;

    float espS = (2 * M_PI) / slices;
    float espH = a / cH;
    // Centra o cone no referencial
    float alt = -a/2;
    int i, j;
    float ang, camadaB, camadaA, raioB, raioA;

    // Vetores para as normais e texturas
    vector<Point> normal;
    vector<Point2d> text;

    // Fazer a circunferencia da base
    for (i = 0; i < slices; i++){

        ang = espS * i;

        x1 = 0;
        y1 = alt;
        z1 = 0;

        x2 = r * sin(ang + espS);
        y2 = alt;
        z2 = r * cos(ang + espS);

        x3 = r * sin(ang);
        y3 = alt;
        z3 = r * cos(ang);

        file << "" << x1 << " " << y1 << " " << z1 << "\n";
        file << "" << x2 << " " << y2 << " " << z2 << "\n";
        file << "" << x3 << " " << y3 << " " << z3 << "\n";

        // Normais
        normal.push_back(Point(0, -1, 0));
        normal.push_back(Point(0, -1, 0));
        normal.push_back(Point(0, -1, 0));
        // Texturas
        text.push_back(Point2d(0.25f, 0.5f));
        text.push_back(Point2d((0.25f + cos(ang + espS) / 4.0f), (0.5f + sin(ang + espS) / 2.0f)));
        text.push_back(Point2d((0.25f + cos(ang) / 4.0f), (0.5f + sin(ang) / 2.0f)));
    }

    // Fazer a parte de cima do cone camada a camada
    for (i = 0; i < cH; i++) {
        camadaB = alt + (i * espH);
        camadaA = alt + ((i + 1) * espH);

        raioB = r - ((r / cH) * i);
        raioA = r - ((r / cH) * (i + 1));

        for (j = 0; j < slices; j++) {
            float alpha = espS * j;

            x4 = raioB * sin(alpha);
            y4 = camadaB;
            z4 = raioB * cos(alpha);

            //normal
            normal.push_back(Point(sin(alpha), a / cH, cos(alpha)));

            x5 = raioA * sin(alpha + espS);
            y5 = camadaA;
            z5 = raioA * cos(alpha + espS);

            //normal
            normal.push_back(Point(sin(alpha + espS), a / cH, cos(alpha + espS)));

            x6 = raioA * sin(alpha);
            y6 = camadaA;
            z6 = raioA * cos(alpha);

            //normal
            normal.push_back(Point(sin(alpha), a / cH, cos(alpha)));


            file << x4 << " " << y4 << " " << z4 << endl;
            file << x5 << " " << y5 << " " << z5 << endl;
            file << x6 << " " << y6 << " " << z6 << endl;

            x7 = raioB * sin(alpha);
            y7 = camadaB;
            z7 = raioB * cos(alpha);

            //normal
            normal.push_back(Point(sin(alpha), a / cH, cos(alpha)));

            x8 = raioB * sin(alpha + espS);
            y8 = camadaB;
            z8 = raioB * cos(alpha + espS);

            //normal
            normal.push_back(Point(sin(alpha + espS), a / cH, cos(alpha + espS)));

            x9 = raioA * sin(alpha + espS);
            y9 = camadaA;
            z9 = raioA * cos(alpha + espS);

            //normal
            normal.push_back(Point(sin(alpha + espS), a / cH, cos(alpha + espS)));

            file << x7 << " " << y7 << " " << z7 << endl;
            file << x8 << " " << y8 << " " << z8 << endl;
            file << x9 << " " << y9 << " " << z9 << endl;

            // Texturas
            float res = (float)(cH - i) / cH;
            float resplus = (float)(cH - (i + 1)) / cH;

            text.push_back(Point2d((0.75f + 0.25f * cos(alpha) * resplus), (0.5f + 0.5f * sin(alpha) * resplus)));
            text.push_back(Point2d((0.75f + 0.25f * cos(alpha) * res), (0.5f + 0.5f * sin(alpha) * res)));
            text.push_back(Point2d((0.75f + 0.25f * cos(alpha + espS) * res), (0.5f + 0.5f * sin(alpha + espS) * res)));
            
            text.push_back(Point2d((0.75f + 0.25f * cos(alpha) * resplus), (0.5f + 0.5f * sin(alpha) * resplus)));
            text.push_back(Point2d((0.75f + 0.25f * cos(alpha + espS) * res), (0.5f + 0.5f * sin(alpha + espS) * res)));
            text.push_back(Point2d((0.75f + 0.25f * cos(alpha + espS) * resplus), (0.5f + 0.5f * sin(alpha + espS) * resplus)));
        
        }

    }
    file << "normais" << "\n";
    for (i = 0; i < normal.size(); i++) {
        file << normal[i].getX() << " " << normal[i].getY() << " " << normal[i].getZ() << endl;
    }
    file << "texturas" << "\n";
    for (int i = 0; i < text.size(); i++) {
        file << text[i].getU() << " " << text[i].getV() << "\n";
    }

    file.close();
}



void sphere(float radius, int slices, int stacks, string nome)
{
    string path = "../Models/" + nome;

    ofstream file;
    file.open(path);

    // Figure
    float alpha = 0;
    float deltaAlpha = (2 * M_PI) / slices;
    float beta = 0;
    float deltaBeta = M_PI / stacks;

    // Textures
    float s;
    float deltaS = 1.0f / slices;
    float t;
    float deltaT = 1.0f / stacks;

    float p1x_S, p1y_S, p1z_S, p2x_S, p2y_S, p2z_S, p3x_S, p3y_S, p3z_S, p4x_S, p4y_S, p4z_S;

    vector<Point> normais;
    vector<Point2d> texturas;

    if (file.is_open()) {
        for (int i = 0; i < stacks; i++) {
            alpha = 0;
            t = (stacks - i) * deltaT;
            for (int j = 0; j < slices; j++) {
                s = j * deltaS;

                p1x_S = radius * sin(beta) * sin(alpha);
                p1y_S = radius * cos(beta);
                p1z_S = radius * sin(beta) * cos(alpha);

                p2x_S = radius * sin(beta + deltaBeta) * sin(alpha);
                p2y_S = radius * cos(beta + deltaBeta);
                p2z_S = radius * sin(beta + deltaBeta) * cos(alpha);

                p3x_S = radius * sin(beta) * sin(alpha + deltaAlpha);
                p3y_S = radius * cos(beta);
                p3z_S = radius * sin(beta) * cos(alpha + deltaAlpha);

                p4x_S = radius * sin(beta + deltaBeta) * sin(alpha + deltaAlpha);
                p4y_S = radius * cos(beta + deltaBeta);
                p4z_S = radius * sin(beta + deltaBeta) * cos(alpha + deltaAlpha);


                /* Ponto da esfera <-----> Ponto da normal <-----> Ponto da textura */
                file << p1x_S << " " << p1y_S << " " << p1z_S << "\n";
                file << p2x_S << " " << p2y_S << " " << p2z_S << "\n";
                file << p4x_S << " " << p4y_S << " " << p4z_S << "\n";
                
                normais.push_back(Point(sin(beta) * sin(alpha), cos(beta), sin(beta) * cos(alpha)));
                normais.push_back(Point(sin(beta + deltaBeta) * sin(alpha), cos(beta + deltaBeta), sin(beta + deltaBeta) * cos(alpha)));
                normais.push_back(Point(sin(beta + deltaBeta) * sin(alpha + deltaAlpha), cos(beta + deltaBeta), sin(beta + deltaBeta) * cos(alpha + deltaAlpha)));

                texturas.push_back(Point2d(s,t));
                texturas.push_back(Point2d(s, (t - deltaT)));
                texturas.push_back(Point2d((s + deltaS), (t - deltaT)));

                file << p1x_S << " " << p1y_S << " " << p1z_S << "\n";
                file << p4x_S << " " << p4y_S << " " << p4z_S << "\n";
                file << p3x_S << " " << p3y_S << " " << p3z_S << "\n";

                normais.push_back(Point(sin(beta) * sin(alpha), cos(beta), sin(beta) * cos(alpha)));
                normais.push_back(Point(sin(beta + deltaBeta) * sin(alpha + deltaAlpha), cos(beta + deltaBeta), sin(beta + deltaBeta) * cos(alpha + deltaAlpha)));
                normais.push_back(Point(sin(beta) * sin(alpha + deltaAlpha), cos(beta), sin(beta) * cos(alpha + deltaAlpha)));

                texturas.push_back(Point2d(s, t));
                texturas.push_back(Point2d((s + deltaS), (t - deltaT)));
                texturas.push_back(Point2d((s + deltaS), t));

                alpha += deltaAlpha;
            }
            beta += deltaBeta;
        }
    }
    file << "normais" << "\n";
    for (int i = 0; i < normais.size(); i++) {
        file << normais[i].getX() << " " << normais[i].getY() << " " << normais[i].getZ() << "\n";
    }

    file << "texturas" << "\n";
    for (int i = 0; i < texturas.size(); i++) {
        file << texturas[i].getU() << " " << texturas[i].getV() << "\n";
    }

    file.close();
}

void cylinder(float altura, float raio, int lados, string fich) {

    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    float* v = (float*)malloc(sizeof(float) * 4 * 3 * 3 * lados);
    float* n = (float*)malloc(sizeof(float) * 4 * 3 * 3 * lados);
    float* t = (float*)malloc(sizeof(float) * 4 * 3 * 3 * lados);

    int vertex = 0;
    float delta = 2.0f * M_PI / lados;

    vector<Point> normais;
    vector<Point2d> texturas;

    for (int i = 0; i < lados; ++i) {
        // topo
        n[vertex * 3 + 0] = 0.0f;
        n[vertex * 3 + 1] = 1.0f;
        n[vertex * 3 + 2] = 0.0f;
        v[vertex * 3 + 0] = 0.0f;
        v[vertex * 3 + 1] = altura / 2.0f;
        v[vertex * 3 + 2] = 0.0f;
        t[vertex * 2 + 0] = 0.4375f;
        t[vertex * 2 + 1] = 0.1875f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = 0;
        n[vertex * 3 + 1] = 1.0f;
        n[vertex * 3 + 2] = 0;
        v[vertex * 3 + 0] = raio * sin(i * delta);
        v[vertex * 3 + 1] = altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos(i * delta);
        t[vertex * 2 + 0] = 0.4375f + 0.1875 * sin(i * delta);
        t[vertex * 2 + 1] = 0.1875f + 0.1875 * cos(i * delta);

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = 0;
        n[vertex * 3 + 1] = 1.0f;
        n[vertex * 3 + 2] = 0;
        v[vertex * 3 + 0] = raio * sin((i + 1) * delta);
        v[vertex * 3 + 1] = altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos((i + 1) * delta);
        t[vertex * 2 + 0] = 0.4375f + 0.1875 * sin((i + 1) * delta);
        t[vertex * 2 + 1] = 0.1875f + 0.1875 * cos((i + 1) * delta);

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";
        
        normais.push_back(Point(0, 1, 0));
        normais.push_back(Point(0, 1, 0));
        normais.push_back(Point(0, 1, 0));

        texturas.push_back(Point2d(0.4375f, 0.1875f));
        texturas.push_back(Point2d(0.4375f + 0.1875 * sin(i * delta), 0.1875f + 0.1875 * cos(i * delta)));
        texturas.push_back(Point2d(0.4375f + 0.1875 * sin((i + 1) * delta), 0.1875f + 0.1875 * cos((i + 1) * delta)));

        // corpo

        vertex++;
        n[vertex * 3 + 0] = sin((i + 1) * delta);
        n[vertex * 3 + 1] = 0.0f;
        n[vertex * 3 + 2] = cos((i + 1) * delta);
        v[vertex * 3 + 0] = raio * sin((i + 1) * delta);
        v[vertex * 3 + 1] = altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos((i + 1) * delta);
        t[vertex * 2 + 0] = (i + 1) / static_cast<float>(lados);
        t[vertex * 2 + 1] = 1.0f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = sin(i * delta);
        n[vertex * 3 + 1] = 0.0f;
        n[vertex * 3 + 2] = cos(i * delta);
        v[vertex * 3 + 0] = raio * sin(i * delta);
        v[vertex * 3 + 1] = altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos(i * delta);
        t[vertex * 2 + 0] = i / static_cast<float>(lados);
        t[vertex * 2 + 1] = 1.0f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = sin(i * delta);
        n[vertex * 3 + 1] = 0.0f;
        n[vertex * 3 + 2] = cos(i * delta);
        v[vertex * 3 + 0] = raio * sin(i * delta);
        v[vertex * 3 + 1] = -altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos(i * delta);
        t[vertex * 2 + 0] = i / static_cast<float>(lados);
        t[vertex * 2 + 1] = 0.375f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";
       

        normais.push_back(Point(sin((i + 1) * delta), 0.0f, cos((i + 1) * delta)));
        normais.push_back(Point(sin(i * delta), 0, cos(i * delta)));
        normais.push_back(Point(sin(i * delta), 1, cos(i * delta)));

        texturas.push_back(Point2d((i + 1) / static_cast<float>(lados), 1.0f));
        texturas.push_back(Point2d(i / static_cast<float>(lados), 1.0f));
        texturas.push_back(Point2d(i / static_cast<float>(lados), 0.375f));

        vertex++;
        n[vertex * 3 + 0] = sin((i + 1) * delta);
        n[vertex * 3 + 1] = 0.0f;
        n[vertex * 3 + 2] = cos((i + 1) * delta);
        v[vertex * 3 + 0] = raio * sin((i + 1) * delta);
        v[vertex * 3 + 1] = -altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos((i + 1) * delta);
        t[vertex * 2 + 0] = (i + 1) / static_cast<float>(lados);
        t[vertex * 2 + 1] = 0.375f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = sin((i + 1) * delta);
        n[vertex * 3 + 1] = 0.0f;
        n[vertex * 3 + 2] = cos((i + 1) * delta);
        v[vertex * 3 + 0] = raio * sin((i + 1) * delta);
        v[vertex * 3 + 1] = altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos((i + 1) * delta);
        t[vertex * 2 + 0] = (i + 1) / static_cast<float>(lados);
        t[vertex * 2 + 1] = 1.0f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = sin(i * delta);
        n[vertex * 3 + 1] = 0.0f;
        n[vertex * 3 + 2] = cos(i * delta);
        v[vertex * 3 + 0] = raio * sin(i * delta);
        v[vertex * 3 + 1] = -altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos(i * delta);
        t[vertex * 2 + 0] = i / static_cast<float>(lados);
        t[vertex * 2 + 1] = 0.375f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        normais.push_back(Point(sin((i + 1)* delta), 0, cos((i + 1)* delta)));
        normais.push_back(Point(sin((i + 1)* delta), 0, cos((i + 1)* delta)));
        normais.push_back(Point(sin(i* delta), 0, cos(i* delta)));

        texturas.push_back(Point2d((i + 1) / static_cast<float>(lados), 0.375f));
        texturas.push_back(Point2d((i + 1) / static_cast<float>(lados), 1.0f));
        texturas.push_back(Point2d(i / static_cast<float>(lados), 0.375f));

        // base
        vertex++;
        n[vertex * 3 + 0] = 0.0f;
        n[vertex * 3 + 1] = -1.0f;
        n[vertex * 3 + 2] = 0.0f;
        v[vertex * 3 + 0] = 0.0f;
        v[vertex * 3 + 1] = -altura / 2.0f;
        v[vertex * 3 + 2] = 0.0f;
        t[vertex * 2 + 0] = 0.8125f;
        t[vertex * 2 + 1] = 0.1875f;

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = 0.0f;
        n[vertex * 3 + 1] = -1.0f;
        n[vertex * 3 + 2] = 0.0f;
        v[vertex * 3 + 0] = raio * sin((i + 1) * delta);
        v[vertex * 3 + 1] = -altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos((i + 1) * delta);
        t[vertex * 2 + 0] = 0.8125f + 0.1875 * sin((i + 1) * delta);
        t[vertex * 2 + 1] = 0.1875f + 0.1875 * cos((i + 1) * delta);

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        vertex++;
        n[vertex * 3 + 0] = 0.0f;
        n[vertex * 3 + 1] = -1.0f;
        n[vertex * 3 + 2] = 0.0f;
        v[vertex * 3 + 0] = raio * sin(i * delta);
        v[vertex * 3 + 1] = -altura / 2.0f;
        v[vertex * 3 + 2] = raio * cos(i * delta);
        t[vertex * 2 + 0] = 0.8125f + 0.1875 * sin(i * delta);
        t[vertex * 2 + 1] = 0.1875f + 0.1875 * cos(i * delta);

        file << "" << v[vertex * 3 + 0] << " " << v[vertex * 3 + 1] << " " << v[vertex * 3 + 2] << "\n";

        normais.push_back(Point(0, -1, 0));
        normais.push_back(Point(0, -1, 0));
        normais.push_back(Point(0, -1, 0));

        texturas.push_back(Point2d(0.8125f, 0.1875f));
        texturas.push_back(Point2d(0.8125f + 0.1875 * sin((i + 1) * delta), 0.1875f + 0.1875 * cos((i + 1) * delta)));
        texturas.push_back(Point2d(0.8125f + 0.1875 * sin(i * delta), 0.1875f + 0.1875 * cos(i * delta)));

        vertex++;
    }
    file << "normais" << "\n";
    for (int i = 0; i < normais.size(); i++) {
        file << normais[i].getX() << " " << normais[i].getY() << " " << normais[i].getZ() << "\n";
    }

    file << "texturas" << "\n";
    for (int i = 0; i < texturas.size(); i++) {
        file << texturas[i].getU() << " " << texturas[i].getV() << "\n";
    }
    file.close();
}

// Algoritmo para a criacao da primitiva torus
void torus(float raioI, float raioE, float slices, float stacks, string fich) {
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    int i, j;
    float x1, x2, x3, x4, y1, y2, y3, y4, z1, z2, z3, z4;
    float theta = 0, phi = 0, shiftT, shiftP;
    shiftT = 2 * M_PI / slices;
    shiftP = 2 * M_PI / stacks;

    vector<Point> normal;
    vector<Point2d> text;

    float textureS = float(1) / stacks;
    float textureR = float(1) / slices;

    for (i = 0; i < slices; i++) {
        for (j = 0; j < stacks; j++) {

            x1 = cos(theta) * (raioI + raioE * cos(phi));
            y1 = sin(theta) * (raioI + raioE * cos(phi));
            z1 = raioE * sin(phi);

            x2 = cos(theta + shiftT) * (raioI + raioE * cos(phi));
            y2 = sin(theta + shiftT) * (raioI + raioE * cos(phi));
            z2 = raioE * sin(phi);

            x3 = cos(theta + shiftT) * (raioI + raioE * cos(phi + shiftP));
            y3 = sin(theta + shiftT) * (raioI + raioE * cos(phi + shiftP));
            z3 = raioE * sin(phi + shiftP);

            x4 = cos(theta) * (raioI + raioE * cos(phi + shiftP));
            y4 = sin(theta) * (raioI + raioE * cos(phi + shiftP));
            z4 = raioE * sin(phi + shiftP);

            file << "" << x1 << " " << y1 << " " << z1 << "\n";
            file << "" << x2 << " " << y2 << " " << z2 << "\n";
            file << "" << x3 << " " << y3 << " " << z3 << "\n";

            //normal
            normal.push_back(Point(cos(theta) * cos(phi), sin(theta) * cos(phi), sin(phi)));
            normal.push_back(Point(cos(theta + shiftT) * cos(phi), sin(theta + shiftT) * cos(phi), sin(phi)));
            normal.push_back(Point(cos(theta + shiftT) * cos(phi + shiftP), sin(theta + shiftT) * cos(phi + shiftP), sin(phi + shiftP)));

            //texturas
            text.push_back(Point2d(i * textureR, j * textureS));
            text.push_back(Point2d((i + 1) * textureR, j * textureS));
            text.push_back(Point2d((i + 1) * textureR, (j + 1) * textureS));

            file << "" << x3 << " " << y3 << " " << z3 << "\n";
            file << "" << x4 << " " << y4 << " " << z4 << "\n";
            file << "" << x1 << " " << y1 << " " << z1 << "\n";

            //normal
            normal.push_back(Point(cos(theta + shiftT) * cos(phi + shiftP), sin(theta + shiftT) * cos(phi + shiftP), sin(phi + shiftP)));
            normal.push_back(Point(cos(theta) * cos(phi + shiftP), sin(theta) * cos(phi + shiftP), sin(phi + shiftP)));
            normal.push_back(Point(cos(theta) * cos(phi), sin(theta) * cos(phi), sin(phi)));

            //texturas
            text.push_back(Point2d((i + 1) * textureR, (j + 1) * textureS));
            text.push_back(Point2d(i * textureR, (j + 1) * textureS));
            text.push_back(Point2d(i * textureR, j * textureS));

            phi = shiftP * (j + 1);
        }
        theta = shiftT * (i + 1);
    }
    file << "normais" << "\n";
    for (int i = 0; i < normal.size(); i++) {
        file << normal[i].getX() << " " << normal[i].getY() << " " << normal[i].getZ() << "\n";
    }

    file << "texturas" << "\n";
    for (int i = 0; i < text.size(); i++) {
        file << text[i].getU() << " " << text[i].getV() << "\n";
    }

    file.close();
}

float* bezierFormula(float t, float* p0, float* p1, float* p2, float* p3) {
    float* result = new float[3];

    float aux = (1 - t);

    float x0 = aux * aux * aux;
    float x1 = 3 * (aux * aux) * t;
    float x2 = 3 * aux * (t * t);
    float x3 = t * t * t;

    result[0] = x0 * p0[0] + x1 * p1[0] + x2 * p2[0] + x3 * p3[0];
    result[1] = x0 * p0[1] + x1 * p1[1] + x2 * p2[1] + x3 * p3[1];
    result[2] = x0 * p0[2] + x1 * p1[2] + x2 * p2[2] + x3 * p3[2];

    return result;
}

float* bezier(float n, float m, float** points, int* index) {
    int i;
    float* point = new float[3];
    float* result = new float[3];
    int j = 0;
    int N = 0;
    float pointsAcumulator[4][3];
    float bezierAcumulator[4][3];

    for (i = 0; i < 16; i++) {

        pointsAcumulator[j][0] = points[index[i]][0];

        pointsAcumulator[j][1] = points[index[i]][1];

        pointsAcumulator[j][2] = points[index[i]][2];

        j++;

        if (j % 4 == 0) {

            point = bezierFormula(n, pointsAcumulator[0], pointsAcumulator[1], pointsAcumulator[2], pointsAcumulator[3]);
           
            bezierAcumulator[N][0] = point[0];
            bezierAcumulator[N][1] = point[1];
            bezierAcumulator[N][2] = point[2];
            j = 0;
            N++;
        }
    }
    result = bezierFormula(m, bezierAcumulator[0], bezierAcumulator[1], bezierAcumulator[2], bezierAcumulator[3]);

    printf("RESULT: %f , %f, %f\n", result[0], result[1], result[2]);

    return result;
}

void patch(string patchFile, int tesselation, string fileSave) {

    string pathRead = "../Models/" + patchFile + ".patch";
    string pathWrite = "../Models/" + fileSave;

    ifstream read(pathRead);

    ofstream file;
    file.open(pathWrite);

    string line, value;
    int i1, i2, i3, p1, p2;
    int position;
    string delimiter = ",";
    float increment = 1.0 / tesselation;


    if (read.is_open()) {

        getline(read, line);
        int nPatches = atoi(line.c_str());
        int** index = new int* [nPatches];
        float*** resultPoints = new float** [nPatches];

        for (i1 = 0; i1 < nPatches; i1++) {
            getline(read, line);
            index[i1] = new int[16];

            for (p1 = 0; p1 < 16; p1++) {
                position = line.find(delimiter);
                value = line.substr(0, position);
                index[i1][p1] = atoi(value.c_str());
                line.erase(0, position + 1);
                //write << index[i1][p1] << endl;
            }
        }

        getline(read, line);
        int cPoints = atoi(line.c_str());
        float** points = new float* [cPoints];



        for (i2 = 0; i2 < cPoints; i2++) {
            getline(read, line);
            points[i2] = new float[3];

            for (p2 = 0; p2 < 3; p2++) {
                position = line.find(delimiter);
                value = line.substr(0, position);
                points[i2][p2] = atof(value.c_str());

                line.erase(0, position + 1);
            }
            
        }

        for (i3 = 0; i3 < nPatches; i3++) {
            resultPoints[i3] = new float* [4];

            for (int xx = 0; xx < tesselation; xx++) {

                for (int yy = 0; yy < tesselation; yy++) {

                    float x1 = increment * xx;
                    float y1 = increment * yy;
                    float x2 = increment * (xx + 1);
                    float y2 = increment * (yy + 1);

                    resultPoints[i3][0] = bezier(x1, y1, points, index[i3]);
                    resultPoints[i3][1] = bezier(x1, y2, points, index[i3]);
                    resultPoints[i3][2] = bezier(x2, y1, points, index[i3]);
                    resultPoints[i3][3] = bezier(x2, y2, points, index[i3]);



                    file << resultPoints[i3][0][0] << " " << resultPoints[i3][0][1] << " " << resultPoints[i3][0][2] << endl;
                    file << resultPoints[i3][2][0] << " " << resultPoints[i3][2][1] << " " << resultPoints[i3][2][2] << endl;
                    file << resultPoints[i3][3][0] << " " << resultPoints[i3][3][1] << " " << resultPoints[i3][3][2] << endl;

                    file << resultPoints[i3][0][0] << " " << resultPoints[i3][0][1] << " " << resultPoints[i3][0][2] << endl;
                    file << resultPoints[i3][3][0] << " " << resultPoints[i3][3][1] << " " << resultPoints[i3][3][2] << endl;
                    file << resultPoints[i3][1][0] << " " << resultPoints[i3][1][1] << " " << resultPoints[i3][1][2] << endl;
                }
            }
        }
        file.close();
        read.close();
    }
    else {
        printf("Ficheiro de Input Inválido!");
    }
}

/**
* Informacao sobre a utilizacao da aplicacao
*/
void info(){
    cout <<" Info:                                                        "<< "\n";
    cout <<"                                                              "<< "\n";
    cout <<"       Executavel Gerador / Motor:                            "<< "\n";
    cout <<"       $ g++ generator.cpp -o gen                             "<< "\n";
    cout <<"       $ g++ engine.cpp -o eng                                "<< "\n";
    cout <<"                                                              "<< "\n";
    cout <<" Geracao das Primitivas:                                      "<< "\n";
    cout <<"                                                              "<< "\n";
    cout <<"     $gen plane lado [file_name.3d]                           "<< "\n";
    cout <<"     $gen box comp largura alt n_camadas[file_name.3d]        "<< "\n";
    cout <<"     $gen cone raio alt n_fatias n_camadas [file_name.3d]     "<< "\n";
    cout <<"     $gen sphere raio n_fatias n_camadas [file_name.3d]       "<< "\n";
    cout <<"     $gen cylinder altura raio n_fatias [file_name.3d] "<< "\n";
    cout <<"     $gen torus raioI raioE slices stacks [file_name.3d]      "<< "\n";
    cout <<"                                                              "<< "\n";
    cout <<" Engine - Interacao com as Primitivas:                        "<< "\n";
    cout <<"                                                              "<< "\n";
    cout <<"      Rotacao da camara -> Arrow Keys (UP, DOWN, LEFT, RIGHT) "<< "\n";
    cout <<"      Zoom In/Out -> + / -  or PAGE UP / PAGE DOWN            "<< "\n";
    cout <<"      Representacao do solido:                                "<< "\n";
    cout <<"           GL_LINE -> l | L                                   "<< "\n";
    cout <<"           GL_FILL -> f | F                                   "<< "\n";
    cout <<"           GL_POINT -> p | P                                  "<< "\n";
    cout <<"           Menu -> Botao Direito do Rato                      " << "\n";
    cout <<"      Inicio/Reset -> i / I                                   "<< "\n";
}

int main(int argc, char **argv){
    if(strcmp(argv[1], "info") == 0)
        info();
    if(strcmp(argv[1], "plane") == 0)
        plano(atof(argv[2]), argv[3]);
    if(strcmp(argv[1], "box") == 0)
        box(atof(argv[2]), atof(argv[3]), atof(argv[4]), atof(argv[5]), argv[6]);
    if(strcmp(argv[1], "cone") == 0)
        cone(atof(argv[2]), atof(argv[3]), atof(argv[4]), atof(argv[5]), argv[6]);
    if(strcmp(argv[1], "sphere") == 0)
        sphere(atof(argv[2]), atof(argv[3]), atof(argv[4]), argv[5]);
    if(strcmp(argv[1], "cylinder") == 0)
        cylinder(atof(argv[2]), atof(argv[3]), atof(argv[4]), argv[5]);
    if (strcmp(argv[1], "torus") == 0)
        torus(atof(argv[2]), atof(argv[3]), atof(argv[4]), atof(argv[5]), argv[6]);
    if (strcmp(argv[1], "patch") == 0)
        patch(argv[2], atoi(argv[3]), argv[4]);

    return 0;
}
