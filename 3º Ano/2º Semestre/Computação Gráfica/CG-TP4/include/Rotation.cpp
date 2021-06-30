#include "Rotation.h"

Rotation::Rotation() {
        this->time = 0.0;
        this->angle = 0.0;
        this->x = 0.0;
        this->y = 0.0;
        this->z = 0.0;
}

Rotation::Rotation(float time, float angle, float x, float y, float z) {
    this->time = time;
    this->angle = angle;
    this->x = x;
    this->y = y;
    this->z = z;
}

void Rotation::run() {

    if ((int)time == 0) {
        glRotatef(this->angle, this->x, this->y, this->z);
    }
    else {
        float rAux = glutGet(GLUT_ELAPSED_TIME) % ((int)time * 1000);
        float r = (rAux * 360) / (time * 1000);
        glRotatef(r, x, y, z);

    }
}