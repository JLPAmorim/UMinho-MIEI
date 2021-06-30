#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#ifndef COLOR_H
#define COLOR_H

#include "Operation.h"

class Color: public Operation {
    float r, g, b;
public:
    Color();
    Color(float r, float g, float b);
    float getR() {return this->r;}
    float getG() {return this->g;}
    float getB() {return this->b;}
    void setR(float x){this->r=r;}
    void setG(float y){this->g=g;}
    void setB(float z){this->b=b;}
    void run();
};

#endif //COLOR_H