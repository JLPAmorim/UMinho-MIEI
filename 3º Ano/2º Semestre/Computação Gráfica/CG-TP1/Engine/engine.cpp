#include "engine.h"


#define ALLMODELS "../Scenes/allXML.xml"
#define PLANE "../Scenes/plane.xml"
#define BOX "../Scenes/box.xml"
#define CONE "../Scenes/cone.xml"
#define SPHERE "../Scenes/sphere.xml"
#define CYLINDER "../Scenes/cylinder.xml"
#define TORUS "../Scenes/torus.xml"

using namespace std;
using namespace tinyxml2;

bool g_tracking = false;
int g_lastMouseX = 400;
int g_lastMouseY = 300;

float randX = 0.0f;
float randY = 0.0f;
float randZ = 0.0f;

float radians(float degrees) {
    return degrees * (M_PI / 180.0f);
}
float g_alpha = radians(45.f);
float g_beta = radians(45.f);
float g_radius = 25.0f;
float camX, camY, camZ;

// Modo de desenho, por defeito é wired
int draw = GL_LINE; 

//Vetor com vertices lidos dos ficheiros 3d
vector<Vertice> vertices; 
// Vetor com valores para as cores dos triangulos
vector<float> colors;

/*
* Tem a função de preencher o vetor colors com valores random
* que serão posteriormente usados no desenho dos triângulos.
*/
void fillColors() {
    float randColor = 0.0f;

    for (unsigned int i = 0; i < vertices.size(); i++) {
        randColor = ((double)rand() / (RAND_MAX));
        colors.push_back(randColor);
    }
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



/**
 * Tem a função de desenhar os triângulos correspondentes às
 * primitivas, cujos vertices estão armazenados no vetor vertices.
 */

void drawVertices(void){   
    unsigned int i = 0;
    
    glBegin(GL_TRIANGLES);

    for (; i < vertices.size(); i += 3) {   
        glColor3f(colors[i], colors[i+1], colors[i+2]);
        glVertex3f(vertices[i].x, vertices[i].y, vertices[i].z);
        glVertex3f(vertices[i + 1].x, vertices[i + 1].y, vertices[i + 1].z);
        glVertex3f(vertices[i + 2].x, vertices[i + 2].y, vertices[i + 2].z);
    }
    glEnd();
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

    glBegin(GL_LINES);
    // X axis in red
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-100.0f, 0.0f, 0.0f);
    glVertex3f(100.0f, 0.0f, 0.0f);
    // Y Axis in Green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -100.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);
    // Z Axis in Blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -100.0f);
    glVertex3f(0.0f, 0.0f, 100.0f);
    glEnd();

    drawVertices();

    
    //glTranslatef(rx, 0, 0);
    //glRotatef(rx, rx, 0, 0);
    

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
    Vertice v;

    fich = "../Models/" + fich;

    ifstream file(fich);

    if (file.is_open()) {

        while (getline(file, linha)) {

            pos = linha.find(delimiter);
            novo = linha.substr(0, pos);
            xx = atof(novo.c_str());
            linha.erase(0, pos + delimiter.length());
            v.x = xx;

            pos = linha.find(delimiter);
            novo = linha.substr(0, pos);
            yy = atof(novo.c_str());
            linha.erase(0, pos + delimiter.length());
            v.y = yy;

            pos = linha.find(delimiter);
            novo = linha.substr(0, pos);
            zz = atof(novo.c_str());
            linha.erase(0, pos + delimiter.length());
            v.z = zz;

            vertices.push_back(v);

        }
        // Preenche vetor com random floats para atribuição das cores aos triangulos.
        fillColors();

        file.close();


    }
    else {

        cout << "ERRO AO LER FICHEIRO" << endl;
    }
}

/**
* Parser para a leitura do ficheiro xml. Tem como objetivo obter 
* o nome do ficheiro 3d que contem os vertices da primitiva
* que se pretende obter.
*/

void xmlParser(string fich) {
    XMLDocument doc;
    XMLElement* root;


    if (!(doc.LoadFile(fich.c_str()))) {
        root = doc.FirstChildElement();
        for (XMLElement* elem = root->FirstChildElement(); elem != NULL; elem = elem->NextSiblingElement()) {
            string ficheiro = elem->Attribute("file");
            cout << "Ficheiro:" << ficheiro << "lido com sucesso" << endl;
            readFile(ficheiro);
        }
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
        xmlParser(argv[1]);
    }
    else {
        xmlParser(TORUS);
    }

    // Caso se pretenda ver apenas um ou determinadas primitivas, basta inserir "//" antes 
    // das primitivas que não se pretende que sejam desenhadas.
    //xmlParser(PLANE);
    //xmlParser(BOX);
    //xmlParser(SPHERE);
    //xmlParser(CONE);
    //xmlParser(CYLINDER);

    //parser(ALLMODELS); // Desenha todas as primitivas

   // xmlParser(CYLINDER);
    
//init glut and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(glutGet(GLUT_SCREEN_WIDTH) / 4, glutGet(GLUT_SCREEN_HEIGHT) / 4);
    glutInitWindowSize(glutGet(GLUT_SCREEN_WIDTH)/2, glutGet(GLUT_SCREEN_HEIGHT)/2);
    glutCreateWindow("Engine");

 
// put callback registration here
    glutDisplayFunc(renderScene);
    glutReshapeFunc(changeSize);
  
  
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

    // OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

    updateCamera();

// enter GLUT's main loop
    glutMainLoop();

    return 1;

}
