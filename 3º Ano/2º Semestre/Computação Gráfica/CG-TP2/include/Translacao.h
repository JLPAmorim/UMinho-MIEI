#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#ifndef TRANSLACAO_H
#define TRANSLACAO_H

#include "Operation.h"

class Translacao: public Operation{
    float x, y, z;

public:
    Translacao();
    Translacao(float x, float y, float z);
    float getX() {return this->x;}
    float getY() {return this->y;}
    float getZ() {return this->z;}
    void setX(float x){this->x=x;}
    void setY(float y){this->y=y;}
    void setZ(float z){this->z=z;}
    void run();
};


#endif //TRANSLACAO_H