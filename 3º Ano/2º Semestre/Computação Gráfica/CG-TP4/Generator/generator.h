#ifndef _MAIN
#define _MAIN

#define _USE_MATH_DEFINES

#include <math.h>
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <vector>
#include <sstream>

using namespace std;

class Point {

    float x;
    float y;
    float z;

public:
    Point();
    Point(float, float, float);
    float getX() { return x; }
    float getY() { return y; }
    float getZ() { return z; }
    void setX(float a) { x = a; }
    void setY(float a) { y = a; }
    void setZ(float a) { z = a; }


};

class Point2d {
    float u;
    float v;

public:
    Point2d();
    Point2d(float a, float b);
    float getU() { return this->u; }
    float getV() { return this->v; }
    void setU(float b) { this->u = b; }
    void setV(float b) { this->v = b; }
};

#endif