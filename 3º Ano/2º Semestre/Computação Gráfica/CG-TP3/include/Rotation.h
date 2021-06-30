#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#ifndef ROTATION_H
#define ROTATION_H

#include "Operation.h"

class Rotation : public Operation {
    float time, angle, x, y, z;

public:
    Rotation();
    Rotation(float time, float angle, float x, float y, float z);
    float getTime() { return this->time; }
    float getAngle() { return this->angle; }
    float getX() { return this->x; }
    float getY() { return this->y; }
    float getZ() { return this->z; }
    void setTime(float tempo) { this->time = tempo; }
    void setAngle(float angle) { this->angle = angle; }
    void setX(float x) { this->x = x; }
    void setY(float y) { this->y = y; }
    void setZ(float z) { this->z = z; }
    void run();
};

#endif //ROTATION_H
