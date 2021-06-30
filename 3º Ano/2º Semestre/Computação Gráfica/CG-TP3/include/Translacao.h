#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#ifndef TRANSLACAO_H
#define TRANSLACAO_H
#include <vector>
#include "Operation.h"
#include "Point.h"

class Translacao: public Operation{
    float x, y, z;
    float tempo;
    std::vector<Point> trans;
    std::vector<Point> pontosCatmull;
    float cima[3]{};

public:
    Translacao();
    Translacao(float x, float y, float z, float t, std::vector<Point> trans);
    float getX() { return this->x; }
    float getY() { return this->y; }
    float getZ() { return this->z; }
    float getTempo() { return this->tempo; }
    float* getCima() { return this->cima; }
    std::vector<Point> getTrans() { return this->trans; }
    std::vector<Point> getPontosCatmull() { return this->pontosCatmull; }
    void setX(float x) { this->x = x; }
    void setY(float y) { this->y = y; }
    void setZ(float z) { this->z = z; }
    void setTempo(float x) { this->tempo = x; }
    void setTrans(std::vector<Point> t) { this->trans = t; }
    void setPontosCatmull(std::vector<Point> pC) { this->pontosCatmull = pC; }
    static void multMatrixVector(float* m, float* v, float* res);
    void getCatmullRomPoint(float t, float* p0, float* p1, float* p2, float* p3, float* pos, float* deriv);
    void getGlobalCatmullRomPoint(float gt, float* pos, float* deriv);
    std::vector<Point> geraPontosCurva();
    void run();
};


#endif //TRANSLACAO_H