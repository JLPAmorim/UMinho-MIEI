#include "generator.h"

void generatePatch(string patchfile, int tesselation, string filesave);

// Algoritmo para a criação da primitiva plane
void plano(float lx, string fich){
    float x;
    
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    // Divisão do lado para ficar centrado na origem
    x = lx/2;

    // Triangulo 1 (inferior)
    file << "" << x << " 0 " << x << "\n";
    file << "" << x << " 0 " << -x << "\n";
    file << "" << -x << " 0 " << -x << "\n";

    // Triangulo 2 (superior)
    file << "" << -x << " 0 " << x << "\n";
    file << "" << x << " 0 " << x << "\n";
    file << "" << -x << " 0 " << -x << "\n";

    file.close();
}


// Algoritmo para a criação da primitiva box
void box(float c, float l, float a, int camadas, string fich){
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    float x,y,z, xx, yy, zz;
    int i, j;
    float pontoX, pontoY, pontoZ, r, s;

    // Definição dos espaços entre as camadas
    float espC = c / camadas;
    float espL = l / camadas;
    float espA = a / camadas;

    // Fazer as faces da frente e face de trás.
    y = a / 2;
    yy = -y;
    x = c / 2;
    xx = -x;
    z = l / 2;
    zz = -z;

    // Faz as camadas percorrendo a linha X e quando acabar sobe uma posiçao de Y.
    for (i = 0; i < camadas; i++) {
        s = yy + (espA * i);

        for (j = 0; j < camadas; j++ ) {
            r = xx + (espC * j);

            pontoX = r + espC; //shift em x
            pontoY = s + espA; //shift em y

            // Face da frente
            file << "" << r << " " << s << " " << z << "\n";
            file << "" << pontoX << " " << s << " " << z << "\n";
            file << "" << pontoX << " " << pontoY << " " << z << "\n";

            file << "" << pontoX << " " << pontoY << " " << z << "\n";
            file << "" << r << " " << pontoY << " " << z << "\n";
            file << "" << r << " " << s << " " << z << "\n";

            // Face de tras
            file << "" << r << " " << s << " " << zz << "\n";
            file << "" << r << " " << pontoY << " " << zz << "\n";
            file << "" << pontoX << " " << pontoY << " " << zz << "\n";

            file << "" << pontoX << " " << pontoY << " " << zz << "\n";
            file << "" << pontoX << " " << s << " " << zz << "\n";
            file << "" << r << " " << s << " " << zz << "\n";
        }
    }

    // Face de cima e de baixo
    // Comeca numa posicao Z e depois faz a linha toda de X e no fim sobe uma posicao de Y

    y = a / 2;
    yy = -y;
    x = c / 2;
    xx = -x;
    z = l / 2;
    zz = -z;

    for (i = 0; i < camadas; i++) {
        s = zz + (espL * i);

        for (j = 0; j < camadas; j ++) {

            r = xx + (espC * j);

            pontoX = r + espC;
            pontoZ = s + espL;

            // Face da cima
            file << "" << r << " " << y << " " << s << "\n";
            file << "" << r << " " << y << " " << pontoZ << "\n";
            file << "" << pontoX << " " << y << " " << s << "\n";

            file << "" << r << " " << y << " " << pontoZ << "\n";
            file << "" << pontoX << " " << y << " " << pontoZ << "\n";
            file << "" << pontoX << " " << y << " " << s<< "\n";

            // Face de baixo
            file << "" << r << " " << yy << " " << s << "\n";
            file << "" << pontoX << " " << yy << " " << s << "\n";
            file << "" << pontoX << " " << yy << " " << pontoZ << "\n";

            file << "" << pontoX << " " << yy << " " << pontoZ << "\n";
            file << "" << r << " " << yy << " " << pontoZ << "\n";
            file << "" << r << " " << yy << " " << s << "\n";
        }
    }

    // Face da esquerda e da direita
    // Começa numa posição de Z faz toda a linha e depois sobe uma unidade de Y.
    y = a / 2;
    yy = -y;
    x = c / 2;
    xx = -x;
    z = l / 2;
    zz = -z;

    for (i = 0; i < camadas; i++) {
        s = yy + (espA * i);

        for (j = 0; j < camadas; j++){
            r = zz + (espL * j);

            pontoZ = r + espL;
            pontoY = s + espA;

            // Face da esquerda
            file << "" << xx << " " << s << " " << r << "\n";
            file << "" << xx << " " << s << " " << pontoZ << "\n";
            file << "" << xx << " " << pontoY << " " << pontoZ << "\n";

            file << "" << xx << " " << pontoY << " " << pontoZ << "\n";
            file << "" << xx << " " << pontoY << " " << r << "\n";
            file << "" << xx << " " << s << " " << r << "\n";

            // Face da direita
            file << "" << x << " " << s << " " << r << "\n";
            file << "" << x << " " << pontoY << " " << r << "\n";
            file << "" << x << " " << s << " " << pontoZ << "\n";

            file << "" << x << " " << pontoY << " " << r << "\n";
            file << "" << x << " " << pontoY << " " << pontoZ << "\n";
            file << "" << x << " " << s << " " << pontoZ << "\n";
        }
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
    }

    // Fazer a parte de cima do cone camada a camada
    for(i = 0; i < cH; i++){
        camadaB = alt + (i * espH);
        camadaA = alt + ((i+1) * espH);

        raioB = r - ((r/cH)*i);
        raioA = r - ((r / cH) * (i+1));

        for(j = 0; j < slices; j++){
            a = espS * j;

            x4 = raioB * sin(a);
            y4 = camadaB;
            z4 = raioB * cos(a);

            x5 = raioA * sin(a + espS);
            y5 = camadaA;
            z5 = raioA * cos(a + espS);

            x6 = raioA * sin(a);
            y6 = camadaA;
            z6 = raioA * cos(a);

            file << x4 << " " << y4 << " " << z4 << "\n";
            file << x5 << " " << y5 << " " << z5 << "\n";
            file << x6 << " " << y6 << " " << z6 << "\n";

            x7 = raioB * sin(a);
            y7 = camadaB;
            z7 = raioB * cos(a);

            x8 = raioB * sin(a + espS);
            y8 = camadaB;
            z8 = raioB * cos(a + espS);

            x9 = raioA * sin(a + espS);
            y9 = camadaA;
            z9 = raioA * cos(a + espS);

            file << "" << x7 << " " << y7 << " " << z7 << "\n";
            file << "" << x8 << " " << y8 << " " << z8 << "\n";
            file << "" << x9 << " " << y9 << " " << z9 << "\n";
        }
    }
    file.close();
}


// Algoritmo para a criacao da primitiva sphere
void sphere(float r, int cv, int ch, string fich){
    
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    float espV = 2 * M_PI / cv;
    float espH = M_PI / ch;
    int i, j;
    float angH, angV;
    float x1, x2, x3, x4, y1, y2, y3, y4, z1, z2, z3, z4;


    for(i = 0; i < ch; i++){

        angH = espH * i;

        for (j=0; j < cv; j++){

            angV = espV * j;


            x1 = r * sin(angV) * sin(angH);
            y1 = r * cos(angH);
            z1 = r * sin(angH) * cos(angV);

            x2 = r * sin(angH) * sin(angV + espV);
            y2 = r * cos(angH);
            z2 = r * sin(angH) * cos(angV + espV);

            x3 = r * sin(angH + espH) * sin(angV + espV);
            y3 = r * cos(angH + espH);
            z3 = r * sin(angH + espH) * cos(angV + espV);

            x4 = r * sin(angH + espH) * sin(angV);
            y4 = r * cos(angH + espH);
            z4 = r * sin(angH + espH) * cos(angV);

            // Dois triangulos que fazem a forma da face da esfera em cada iteracao do circulo
            file << "" << x1 << " " << y1 << " " << z1 << "\n";
            file << "" << x3 << " " << y3 << " " << z3 << "\n";
            file << "" << x2 << " " << y2 << " " << z2 << "\n";
           
            file << "" << x1 << " " << y1 << " " << z1 << "\n";
            file << "" << x4 << " " << y4 << " " << z4 << "\n";
            file << "" << x3 << " " << y3 << " " << z3 << "\n";
        }
    }
    file.close();
}


// Algoritmo para a criacao da primitiva cylinder
void cylinder(float raio, float altura, int slices, int slicesH, string fich){
   
    string path = "../Models/" + fich;

    // Abrir o ficheiro
    ofstream file;
    file.open(path);

    float espS = 2 * M_PI / slices;
    float espSH = altura / slicesH;
    float x1, x2, x3, x4, y1, y2, y3, y4, z1, z2, z3, z4;
    float alt = -(altura/2);
    float yC, ang, altCamada;
    int i, j;

    // Fazer as duas bases(circunferencias) do cilindro
    for (i = 0; i < slices; i++){
        ang = espS * i;

        x1 = 0;
        y1 = alt;
        z1 = 0;

        x2 = raio * sin(ang);
        y2 = alt;
        z2 = raio * cos(ang);

        x3 = raio * sin(ang + espS);
        y3 = alt;
        z3 = raio * cos(ang + espS);

        yC = - alt;

        // Circunferencia de cima
        file << "" << x1 << " " << yC << " " << z1 << "\n";
        file << "" << x2 << " " << yC << " " << z2 << "\n";
        file << "" << x3 << " " << yC << " " << z3 << "\n";

        // Circunferencia de baixo
        file << "" << x2 << " " << y2 << " " << z2 << "\n";
        file << "" << x1 << " " << y1 << " " << z1 << "\n";
        file << "" << x3 << " " << y3 << " " << z3 << "\n";
    }

    // Unir as faces fazendo em N slicesHorizontais
    for(i = 0; i <slicesH; i++){

        altCamada = alt + (espSH * i);

        for(j = 0; j < slices; j++){

            ang = espS * j;

            x1 = raio * sin(ang);
            y1 = altCamada + espSH;
            z1 = raio * cos(ang);

            x2 = raio * sin(ang);
            y2 = altCamada;
            z2 = raio * cos(ang);

            x3 = raio * sin(ang + espS);
            y3 = altCamada;
            z3 = raio * cos(ang + espS);

            x4 = raio * sin(ang + espS);
            y4 = altCamada + espSH;
            z4 = raio * cos(ang + espS);

            // Triangulos para fazer a face correspondente em cada ciclo
            file << "" << x1 << " " << y1 << " " << z1 << "\n";
            file << "" << x2 << " " << y2 << " " << z2 << "\n";
            file << "" << x3 << " " << y3 << " " << z3 << "\n";

            file << "" << x3 << " " << y3 << " " << z3 << "\n";
            file << "" << x4 << " " << y4 << " " << z4 << "\n";
            file << "" << x1 << " " << y1 << " " << z1 << "\n";

        }
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

            file << "" << x3 << " " << y3 << " " << z3 << "\n";
            file << "" << x4 << " " << y4 << " " << z4 << "\n";
            file << "" << x1 << " " << y1 << " " << z1 << "\n";

            phi = shiftP * (j + 1);
        }
        theta = shiftT * (i + 1);
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
            //write << points[i2][0] << "," << points[i2][1] << "," << points[i2][2] << endl;
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
    cout <<"     $gen cylinder raio alt n_fatias n_camadas [file_name.3d] "<< "\n";
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
        cylinder(atof(argv[2]), atof(argv[3]), atof(argv[4]), atof(argv[5]), argv[6]);
    if (strcmp(argv[1], "torus") == 0)
        torus(atof(argv[2]), atof(argv[3]), atof(argv[4]), atof(argv[5]), argv[6]);
    if (strcmp(argv[1], "patch") == 0)
        patch(argv[2], atoi(argv[3]), argv[4]);

    return 0;
}
