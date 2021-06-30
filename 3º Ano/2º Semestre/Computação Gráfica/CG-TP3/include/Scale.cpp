#include "Scale.h"

Scale :: Scale (){
    this->x = 0.0;
    this->y = 0.0;
    this->z = 0.0;
}

Scale:: Scale (float x, float y, float z){
    this->x = x;
    this->y = y;
    this->z = z;
}

void Scale::run() {
    glScalef(this->x, this->y, this->z);
}