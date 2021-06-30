#include "Point.h"

Point::Point() {
    this->x = 0.0;
    this->y = 0.0;
    this->z = 0.0;
}

Point::Point(float a, float b, float c){
    this->x=a;
    this->y=b;
    this->z=c;
}