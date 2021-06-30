#include "engine.h"

using namespace std;

// Variáveis para a definiçãod a câmara
bool g_tracking = false;
int g_lastMouseX = 400;
int g_lastMouseY = 300;
float g_alpha = radians(195.f);
float g_beta = radians(20.f);
float g_radius = 40.0f;
float camX, camY, camZ;

// Modo de desenho, por defeito é FILL
int draw = GL_FILL;

//Vetor com vertices lidos dos ficheiros 3d
vector<Point> vertexes;
//Vetor com as operacoes a serem efetuadas
vector<Operation*> ops;

// Vetores auxiliares
vector<vector<float>> vertVBOs;
vector<vector<float>> texVBOs;
vector<vector<float>> normVBOs;
vector<string> texFiles;

//Numero de buffers de cada tipo
int nVBOVert = 0;
int nVBOTex = 0;
int nVBONorm = 0;

// Tratamento das luzes
int countTimes = 0;
string type;

// VBOs
GLuint* buffersVerts;
GLuint* buffersNorms;
GLuint* buffersTextsCoords;
GLuint* buffersTexID;

// Vetores para guardar os pontos das normais 
// e texturas lidos do ficheiro .3d
vector<Point> normal;
vector<Point2d> texturas;

// Ponto que contêm as coordenadas lidas do XML para a posição da luz
Point pos(0.0f, 0.0f, 1.0f);      

void prepareData() {
    //vertices
    buffersVerts = (GLuint*)malloc((sizeof(GLuint)) * nVBOVert);
    //normais
    buffersNorms = (GLuint*)malloc((sizeof(GLuint)) * nVBONorm);
    //textures
    buffersTextsCoords = (GLuint*)malloc((sizeof(GLuint)) * nVBOTex);

    //vbos dos vertices
    glGenBuffers(nVBOVert, buffersVerts);
    for (int i = 0; i < nVBOVert; ++i) {
        glBindBuffer(GL_ARRAY_BUFFER, buffersVerts[i]);
        glBufferData(GL_ARRAY_BUFFER, sizeof(float) * 
            vertVBOs[i].size(), vertVBOs[i].data(), GL_STATIC_DRAW);
    }
    //vbos das normais
    glGenBuffers(nVBONorm, buffersNorms);
    for (int i = 0; i < nVBONorm; ++i) {
        glBindBuffer(GL_ARRAY_BUFFER, buffersNorms[i]);
        glBufferData(GL_ARRAY_BUFFER, sizeof(float) * normVBOs[i].size(), 
            normVBOs[i].data(), GL_STATIC_DRAW);

    }
    //vbos das texturas
    glGenBuffers(nVBOTex, buffersTextsCoords);
    for (int i = 0; i < nVBOTex; ++i) {

        glBindBuffer(GL_ARRAY_BUFFER, buffersTextsCoords[i]);
        glBufferData(GL_ARRAY_BUFFER, sizeof(float) * texVBOs[i].size(), 
            texVBOs[i].data(), GL_STATIC_DRAW);
    }
}

int loadTex(std::string s) {
    unsigned int t, tw, th;
    unsigned char* texData;
    unsigned int texID;
    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
    ilGenImages(1, &t);
    ilBindImage(t);
    ilLoadImage((ILstring)s.c_str());
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    std::cout << s << std::endl;
    std::cout << tw << "Withd" << std::endl;
    std::cout << th << std::endl;
    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    glGenTextures(1, &texID);

    glBindTexture(GL_TEXTURE_2D, texID);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
    glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);

    return texID;
}

void loadTextures() {

    buffersTexID = (GLuint*)malloc((sizeof(GLuint)) * nVBOTex);

    for (int i = 0; i < nVBOTex; ++i) {

        buffersTexID[i] = loadTex(texFiles[i]);
    }
}

vector<float> ponto2XYZ(vector<Point> p) {
    vector<float> xyz;

    for (unsigned int i = 0; i < p.size(); i++) {
        xyz.push_back(p[i].getX());
        xyz.push_back(p[i].getY());
        xyz.push_back(p[i].getZ());
    }
    return xyz;
}

vector<float> ponto2DtoUV(vector<Point2d> p) {
    vector<float> uv;

    for (unsigned int i = 0; i < p.size(); i++) {
        uv.push_back(p[i].getU());
        uv.push_back(p[i].getV());
    }
    return uv;
}

/*
* Tem a função de alterar o raio, que por sua vez é
* responsável por afastar ou aproximar a câmara do centro.
*/
void advanceCamera(float delta) {
    g_radius += delta;
}

/*
* Tem a função de alterar os campos presentes no gluLookAt
* sempre que o alpha, beta ou raio são alterados. Sempre
* que o mesmo se verificar, esta função é chamada.
*/
void updateCamera() {
    camX = g_radius * cos(g_beta) * sin(g_alpha);
    camY = g_radius * sin(g_beta);
    camZ = g_radius * cos(g_beta) * cos(g_alpha);
}

void renderScene(void) {

    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
        0, 0, 0,
        0.0f, 1.0f, 0.0f);

    //draw instructions
    glPolygonMode(GL_FRONT_AND_BACK, draw);
    
    glEnable(GL_LIGHT0);
    // fazer as operações que estão no vetor de operações

    for (unsigned int i = 0; i < ops.size(); i++) {
         ops[i]->run();
    }
    glutSwapBuffers();
}

void changeSize(int w, int h) {
    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0)
        h = 1;
    float ratio = w * 1.0 / h;
    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load Identity Matrix
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set perspective
    gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}

void processSpecialKeys(int key, int xx, int yy) {

    switch (key) {

    case GLUT_KEY_LEFT:
        g_alpha -= 0.05; break;

    case GLUT_KEY_RIGHT:
        g_alpha += 0.05; break;

    case GLUT_KEY_UP:
        g_beta += 0.1f;
        if (g_beta > 1.5f)
            g_beta = 1.5f;
        break;

    case GLUT_KEY_DOWN:
        g_beta -= 0.1f;
        if (g_beta < -1.5f)
            g_beta = -1.5f;
        break;

    case GLUT_KEY_PAGE_DOWN:
        advanceCamera(0.4);
        break;

    case GLUT_KEY_PAGE_UP:
        advanceCamera(-0.4);
        break;
    }
    updateCamera();
    glutPostRedisplay();

}

/*
* Tem a função de processar as teclas do teclado
* que não são consideradas Special Keys.
*/
void keyboard(unsigned char key, int a, int b) {
    switch (key) {
    case '+':
        advanceCamera(-0.4);
        break;

    case '-':
        advanceCamera(0.4);
        break;

    case 'l':
    case 'L':
        draw = GL_LINE;
        break;

    case 'f':
    case 'F':
        draw = GL_FILL;
        break;

    case 'p':
    case 'P':
        draw = GL_POINT;
        break;

    case 'i':
    case 'I':
        g_alpha = radians(45.f);
        g_beta = radians(45.f);
        g_radius = 25.0f;
        draw = GL_LINE;
        break;
    }
    updateCamera();
    glutPostRedisplay();
}


/**
* Tem como função lidar com as opções escolhidas atraves do menu
*/

void processMenuEvents(int choice)
{   //
    if (choice == GL_FILL) {

        draw = GL_FILL;
    }
    else if (choice == GL_LINE) {

        draw = GL_LINE;

    }
    else if (choice == GL_POINT) {

        draw = GL_POINT;
    }
    glutPostRedisplay();
}

/**
* Em conjunto com a função on_mouse_motion, processam o
* movimento da câmara utilizando o rato.
*/
void on_mouse_button(int button, int state, int x, int y) {

    if (button == GLUT_LEFT_BUTTON && state == GLUT_UP) {
        g_tracking = false;
    }
}

/**
* Em conjunto com a função on_mouse_button, processam o
* movimento da câmara utilizando o rato.
*/
void on_mouse_motion(int x, int y) {
    if (g_tracking) {
        int deltaX = x - g_lastMouseX;
        int deltaY = y - g_lastMouseY;
 
        g_alpha += -0.01f * deltaX;
        g_beta += 0.01 * deltaY;
        if (g_beta < -1.5f)
            g_beta = -1.5f;
        if (g_beta > 1.5f)
            g_beta = 1.5f;
        
        updateCamera();

        g_lastMouseX = x;
        g_lastMouseY = y;
    }
    else {
        g_lastMouseX = x;
        g_lastMouseY = y;
        g_tracking = true;
    }
    glutPostRedisplay();
}

//método para ler o ficheiro e preencher vetor

void readFile(string fich) {
    string linha;
    string novo;
    string delimiter = " ";
    int pos;
    float xx, yy, zz;
    Point p;
    Point2d pT;
    fich = "../Models/" + fich;

    ifstream file(fich);

    if (file.is_open()) {

        while (getline(file, linha) && (linha.compare("texturas")) != 0 && (linha.compare("normais")) != 0) {

            pos = linha.find(delimiter);
            novo = linha.substr(0, pos);
            xx = atof(novo.c_str());
            linha.erase(0, pos + delimiter.length());
            p.setX(xx);

            pos = linha.find(delimiter);
            novo = linha.substr(0, pos);
            yy = atof(novo.c_str());
            linha.erase(0, pos + delimiter.length());
            p.setY(yy);

            pos = linha.find(delimiter);
            novo = linha.substr(0, pos);
            zz = atof(novo.c_str());
            linha.erase(0, pos + delimiter.length());
            p.setZ(zz);
            vertexes.push_back(p);
        }
        if ((linha.compare("normais")) == 0) {
            while (getline(file, linha) && (linha.compare("texturas")) != 0) {
                pos = linha.find(delimiter);
                novo = linha.substr(0, pos);
                xx = atof(novo.c_str());
                linha.erase(0, pos + delimiter.length());
                p.setX(xx);

                pos = linha.find(delimiter);
                novo = linha.substr(0, pos);
                yy = atof(novo.c_str());
                linha.erase(0, pos + delimiter.length());
                p.setY(yy);

                pos = linha.find(delimiter);
                novo = linha.substr(0, pos);
                zz = atof(novo.c_str());
                linha.erase(0, pos + delimiter.length());
                p.setZ(zz);
                normal.push_back(p);
            }
        }
        if ((linha.compare("texturas")) == 0) {
            while (getline(file, linha)) {
                pos = linha.find(delimiter);
                novo = linha.substr(0, pos);
                xx = atof(novo.c_str());
                linha.erase(0, pos + delimiter.length());
                pT.setU(xx);

                pos = linha.find(delimiter);
                novo = linha.substr(0, pos);
                yy = atof(novo.c_str());
                linha.erase(0, pos + delimiter.length());
                pT.setV(yy);
                texturas.push_back(pT);
            }
        }
        file.close();
    }
    else {

        cout << "ERRO AO LER FICHEIRO" << endl;
    }
}

//método para ler um  xml group

void xmlParserTransf(TiXmlElement* group) {

    //Variáveis para a Translacao
    float tX, tY, tZ, timeT;
    //Variáveis para a Rotation
    float angleR, rX, rY, rZ, timeR;
    //Variáveis para a Scale
    float sX, sY, sZ;
    //Variáveis para a Color
    float cR, cG, cB;
    //Point para a Translacao
    float px, py, pz;

    if (strcmp(group->FirstChildElement()->Value(), "group") == 0) {
        group = group->FirstChildElement();
    }

    ops.push_back(new Push());

    for (TiXmlElement* t = group->FirstChildElement(); (strcmp(t->Value(), "models") != 0); t = t->NextSiblingElement()) {   

        if (!strcmp(t->Value(), "translate")) {
            const char* a1 = t->Attribute("X");
            const char* a2 = t->Attribute("Y");
            const char* a3 = t->Attribute("Z");
            const char* a4 = t->Attribute("time");

            if (a1) {
                tX = stof(a1);
            }
            else tX = 0;

            if (a2) {
                tY = stof(a2);
            }
            else tY = 0;

            if (a3) {
                tZ = stof(a3);
            }
            else tZ = 0;

            if (a4) {
                timeT = stof(a4);
            }
            else timeT = 0;

            std::vector<Point> pTrans;

            //Adicionar pontos da Translacao a um vetor
            for (TiXmlElement* pt = t->FirstChildElement("point"); pt; pt = pt->NextSiblingElement("point")) {

                const char* a5 = pt->Attribute("X");
                const char* a6 = pt->Attribute("Y");
                const char* a7 = pt->Attribute("Z");

                if (a5) {
                    px = stof(a5);
                }
                else px = 0;

                if (a6) {
                    py = stof(a6);
                }
                else py = 0;

                if (a7) {
                    pz = stof(a7);
                }
                else pz = 0;

                Point point = Point(px, py, pz);
                cout << "x=" << point.getX() << "y=" << point.getY() << "z=" << point.getZ() << endl;
                pTrans.push_back(point);

            }
            ops.push_back(new Translacao(tX, tY, tZ, timeT, pTrans));
        }
        //Rotation
        if (!strcmp(t->Value(), "rotate")) {
            const char* a8 = t->Attribute("time");
            const char* a9 = t->Attribute("angle");

            if (a8) {
                timeR = stof(a8);
            }
            else timeR = 0;

            if (a9) {
                angleR = stof(a9);
            }
            else angleR = 0;

            rX = stof(t->Attribute("axisX"));
            rY = stof(t->Attribute("axisY"));
            rZ = stof(t->Attribute("axisZ"));

            ops.push_back(new Rotation(timeR, angleR, rX, rY, rZ));
        }
        //Scale
        if (!strcmp(t->Value(), "scale")) {
            sX = stof(t->Attribute("X"));
            sY = stof(t->Attribute("Y"));
            sZ = stof(t->Attribute("Z"));

            ops.push_back(new Scale(sX, sY, sZ));
        }
    }

    for (TiXmlElement* modelo = group->FirstChildElement("models")->FirstChildElement("model"); modelo; modelo = modelo->NextSiblingElement("model")) {
        bool sun=false;
        
        readFile(modelo->Attribute("file"));// lê as coordenados dos vertices gerados pelo gerador
        //adicionado o nome do ficheiro ao vetor de texturas
        texFiles.push_back(modelo->Attribute("textura"));
        //caso sem normais nem texturas
        if ((normal.size() == 0) && (texturas.size() == 0)) {
            if (sun) {
                ops.push_back(new Light(pos.getX(), pos.getY(), pos.getZ(), true, true, type));
                ops.push_back(new Draw(vertexes.size(), nVBOVert++, -1, -1));
                ops.push_back(new Light(0, 0, 0, false, true, type));
            }
            else {
                ops.push_back(new Light(pos.getX(), pos.getY(), pos.getZ(), true, false, type));
                ops.push_back(new Draw(vertexes.size(), nVBOVert++, -1, -1));
                ops.push_back(new Light(0, 0, 0, false, true, type));
            }    
        } //caso com texturas e normais
        else {
            if (countTimes == 0) {
                countTimes++;
                sun = true;
            }
            else {
                sun = false;
            }
            if(sun==true) {
                cout << "Dentro do primeiro ciclo" << endl;
                ops.push_back(new Light(pos.getX(), pos.getY(), pos.getZ(), true, true, type));
                ops.push_back(new Draw(vertexes.size(), nVBOVert++, nVBOTex++, nVBONorm++));
                ops.push_back(new Light(0, 0, 0, false, true, type));
                
            }
            else if(sun == false){
                cout << "Dentro do segundo ciclo" << endl;
                ops.push_back(new Light(pos.getX(), pos.getY(), pos.getZ(), true, false, type));
                ops.push_back(new Draw(vertexes.size(), nVBOVert++, nVBOTex++, nVBONorm++));
                ops.push_back(new Light(0, 0, 0, false, true, type));
            }
        }
        
        vertVBOs.push_back(ponto2XYZ(vertexes));
        normVBOs.push_back(ponto2XYZ(normal));
        texVBOs.push_back(ponto2DtoUV(texturas));

        vertexes.clear();//limpa o vector dos vertices
        normal.clear(); //limpa o vector das normais
        texturas.clear();//limpa o vector das texturas

    }
    // child parsing
    if (group->FirstChildElement("group")) {
        xmlParserTransf(group->FirstChildElement("group"));
    }

    ops.push_back(new Pop());

    //brother parsing
    if (group->NextSiblingElement("group")) {
        xmlParserTransf(group->NextSiblingElement("group"));

    }


}
float readFloat(std::string str)
{
    float ret = 0.0f;
    std::stringstream ss(str);

    ss >> ret;

    return ret;
}

std::string toLower(const char* str)
{
    std::string aux(str);

    std::transform(aux.begin(), aux.end(), aux.begin(), ::tolower);

    return aux;
}

void xmlParseLight(TiXmlElement* light){

    for (TiXmlAttribute* a = light->FirstAttribute(); a; a = a->Next()) {

        if (!strcmp(a->Name(), "posX")) {
            pos.setX(readFloat(a->Value()));
        }
        else if (!strcmp(a->Name(), "posY")) {
            pos.setY(readFloat(a->Value()));
        }
        else if (!strcmp(a->Name(), "posZ")) {
            pos.setZ(readFloat(a->Value()));
        }

        else if (!strcmp(a->Name(), "type")) {
            if (!strcmp(a->Value(), "POINT")) {
                type = "POINT";
            }
            else if (!strcmp(a->Value(), "DIRECTIONAL")) {
                type = "DIRECTIONAL";
            }
            else if (!strcmp(a->Value(), "SPOT")) {
                type = "SPOT";
            }
        }
    }
}

void parseLights(TiXmlElement* lights){

    for (TiXmlElement* light = lights->FirstChildElement(); light; light = light->NextSiblingElement()){

        std::string lightName(toLower(light->Value()));

        if (lightName == "light") {

            xmlParseLight(light);
        }
        else {
            cout << "problema no xml com as luzes" << endl;
        }
    }
}

void xmlread(const char* pFilename) {
    string a = "../Scenes/";
    string c = a + pFilename;
    pFilename = c.c_str();
    TiXmlDocument doc(pFilename);


    bool loadOkay = doc.LoadFile();
    if (loadOkay) {

        TiXmlElement* scene = doc.FirstChildElement("scene");
        TiXmlElement* group = scene->FirstChildElement("group");
        TiXmlElement* luzes = scene->FirstChildElement("lights");
        parseLights(luzes);
        xmlParserTransf(group);
    }
    else {
        cout << "Ficheiro XML não foi encontrado" << endl;
    }
}

int main(int argc, char** argv) {

    if (argc > 1) {
        xmlread(argv[1]);
    }
    else {
        xmlread("solarsystem.xml");
    }

    //init glut and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(1500, 800);
    glutCreateWindow("CG_Project");

    glutDisplayFunc(renderScene);
    glutReshapeFunc(changeSize);
    glutIdleFunc(renderScene);

    // Eventos do teclado
    glutSpecialFunc(processSpecialKeys);
    glutKeyboardFunc(keyboard);
    // Eventos do rato
    glutMotionFunc(on_mouse_motion);
    glutMouseFunc(on_mouse_button);

    int menu = glutCreateMenu(processMenuEvents);
    glutAddMenuEntry("GL_FILL", GL_FILL);
    glutAddMenuEntry("GL_LINE", GL_LINE);
    glutAddMenuEntry("GL_POINT", GL_POINT);
    // Tecla que mostra o menu
    glutAttachMenu(GLUT_RIGHT_BUTTON);

#ifndef __APPLE__
    glewInit();
#endif

    // OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    glEnable(GL_LIGHTING);
    //glClearColor(0, 0, 0, 0);

    //glEnable(GL_TEXTURE_2D);

    prepareData();
    loadTextures();

    updateCamera();

    // enter GLUT's main loop
    glutMainLoop();

    return 1;
}