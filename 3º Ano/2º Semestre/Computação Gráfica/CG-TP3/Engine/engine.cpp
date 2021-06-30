#include "engine.h"

#define ALLMODELS "allXML.xml"
#define PLANE "plane.xml"
#define BOX "box.xml"
#define CONE "cone.xml"
#define SPHERE "sphere.xml"
#define CYLINDER "cylinder.xml"
#define TORUS "torus.xml"
#define TEAPOT "teapot.xml"
#define SOLARSYSTEM "solarsystem.xml"
#define SOLARSYSTEMNOR "solarsystemNoR.xml"

using namespace std;
using namespace tinyxml2;

bool g_tracking = false;
int g_lastMouseX = 400;
int g_lastMouseY = 300;


float radians(float degrees) {
    return degrees * (M_PI / 180.0f);
}
float g_alpha = radians(195.f);
float g_beta = radians(20.f);
float g_radius = 40.0f;
float camX, camY, camZ;

// Modo de desenho, por defeito é wired
int draw = GL_LINE; 

//Vetor com vertices lidos dos ficheiros 3d
vector<Point> vertices; 
//Vetor com as operacoes a serem efetuadas
vector<Operation*> ops;
vector<vector<float>> vertVBOs;
//Numero de buffers
int nVBOS = 0;
int iVBO = 0;
GLuint *buffers;


//Vetor com valores para as cores dos triangulos
//vector<float> colors;

/*
* Tem a função de preencher o vetor colors com valores random
* que serão posteriormente usados no desenho dos triângulos.
*
void fillColors() {
    float randColor = 0.0f;

    for (unsigned int i = 0; i < vertices.size(); i++) {
        randColor = ((double)rand() / (RAND_MAX));
        colors.push_back(randColor);
    }
}*/

void prepareData() {

    buffers = (GLuint*)malloc((sizeof(GLuint)) * nVBOS);
    glGenBuffers(nVBOS, buffers);

    //Para cada buffer i
    for (int i = 0; i < nVBOS; ++i) {
        //Set buffer active
        glBindBuffer(GL_ARRAY_BUFFER, buffers[i]);
        //Fill buffer
        glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertVBOs[i].size(), vertVBOs[i].data(), GL_STATIC_DRAW);
    }
}

/*
* Tem a função de, dado um vetor de pontos, criar e preencher 
* um vetor com as coordenadas desses pontos, devolvendo o
* vetor criado.
*/
vector<float> ponto2XYZ(vector<Point> p) {
    vector<float> v;

    for (int i = 0; i < p.size(); i++) {
        v.push_back(p[i].getX());
        v.push_back(p[i].getY());
        v.push_back(p[i].getZ());
    }
    return v;
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
void changeSize(int w, int h)
{
    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0) {
        h = 1;
    }

    // compute window's aspect ratio 
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


void renderScene(void){
    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
        0.0, 0.0, 0.0,
        0.0f, 1.0f, 0.0f);

    
    glPolygonMode(GL_FRONT_AND_BACK, draw);

    for (unsigned int i = 0; i < ops.size(); i++) {
        ops[i]->run();
    }

    iVBO = 0;
 
    glutSwapBuffers();
}

/*
* Tem a função de processar as Special Keys.
*/
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
        advanceCamera(-0.1);
        break;

    case GLUT_KEY_PAGE_UP: 
        advanceCamera(0.1);
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
        case '-':
            advanceCamera(-0.1);
            break;

        case '+':
            advanceCamera(0.1);
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
    if (choice == GL_FILL){

        draw = GL_FILL;
    }
    else if (choice == GL_LINE){

        draw = GL_LINE;

    }
    else if (choice == GL_POINT){

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


/**
* Le o ficheiro 3d e preenche o vetor com os vertices lidos
*/

void readFile(string fich) {
    string linha;
    string novo;
    string delimiter = " ";
    int pos;
    float xx, yy, zz;
    Point p;

    fich = "../Models/" + fich;

    ifstream file(fich);

    if (file.is_open()) {

        while (getline(file, linha)) {

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

            vertices.push_back(p);

        }

        file.close();

    }
    else {

        cout << "ERRO AO LER FICHEIRO" << endl;
    }
}

/**
* Faz o parsing do ficheiro XML de acordo com
* a hierarquia pedida.
*/

void xmlParserTransf(XMLElement* group) {

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

    
    for (XMLElement* t = group->FirstChildElement(); (strcmp(t->Value(), "models") != 0); t = t->NextSiblingElement()) {
        //Translacao
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
            for (XMLElement* pt = t->FirstChildElement("point"); pt; pt = pt->NextSiblingElement("point")) {

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

        //Color
        if (!strcmp(t->Value(), "color")) {
            cR = stof(t->Attribute("R"));
            cG = stof(t->Attribute("G"));
            cB = stof(t->Attribute("B"));
            ops.push_back(new Color(cR, cG, cB));
        }
    }

    for (XMLElement* modelo = group->FirstChildElement("models")->FirstChildElement("model"); modelo; modelo = modelo->NextSiblingElement("model")) {
        
        readFile(modelo->Attribute("file"));

        ops.push_back(new Draw(vertices.size()));
        vertVBOs.push_back(ponto2XYZ(vertices));
        nVBOS++;
        vertices.clear();
    }

    if (group->FirstChildElement("group")) {
        xmlParserTransf(group->FirstChildElement("group"));
    }

    ops.push_back(new Pop());

    if (group->NextSiblingElement("group")) {
        xmlParserTransf(group->NextSiblingElement("group"));
    }
}

/**
* Faz a leitura do ficheiro xml utilizando 
* a funcao xmlParserTransf
*/
void readXML(string fich) {
    string s = "../Scenes/";
    string file = s + fich;
    XMLDocument doc;
    
    if (!(doc.LoadFile(file.c_str()))){
      
        XMLElement* scene = doc.FirstChildElement("scene");
        XMLElement* group = scene->FirstChildElement("group");

        xmlParserTransf(group);
    }
    else {
        cout << "Ficheiro XML não foi encontrado" << endl;
    }
}

int main(int argc, char** argv){
    
    // Caso o programa seja executado no terminal, se o número de argumentos
    // for menor que 1, abre o ficheiro XML que contêm o nome de todas as 
    // primitivas gráficas. Caso contrário, abre o ficheiro xml inserido
    // como primeiro argumento.
    if (argc > 1) {
        readXML(argv[1]);
    }
    else {
        readXML(SOLARSYSTEM);
    }
    
    
//init glut and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(glutGet(GLUT_SCREEN_WIDTH) / 4, glutGet(GLUT_SCREEN_HEIGHT) / 4);
    glutInitWindowSize(glutGet(GLUT_SCREEN_WIDTH)/2, glutGet(GLUT_SCREEN_HEIGHT)/2);
    glutCreateWindow("Engine");

 
// put callback registration here
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
    glEnableClientState(GL_VERTEX_ARRAY);

    // OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

    prepareData();

    

// enter GLUT's main loop
    glutMainLoop();

    return 1;

}
