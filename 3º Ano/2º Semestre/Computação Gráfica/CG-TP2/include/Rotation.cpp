#include "Rotation.h"

Rotation :: Rotation (){
   this->angle = 0.0;
   this->x = 0.0;
   this->y = 0.0;
   this->z = 0.0;
}

Rotation:: Rotation (float angle, float x, float y, float z){
    this->angle = angle;
    this->x = x;
    this->y = y;
    this->z = z;
}

void Rotation::run() {
    glRotatef(this->angle, this->x, this->y, this->z);
}