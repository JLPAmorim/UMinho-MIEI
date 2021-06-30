#include "Color.h"

Color :: Color (){
    this->r = 0.0;
    this->g = 0.0;
    this->b = 0.0;
}

Color:: Color (float r, float g, float b){
    this->r = r;
    this->g = g;
    this->b = b;
}

void Color::run() {
    glColor3f(this->r, this->g, this->b);
}