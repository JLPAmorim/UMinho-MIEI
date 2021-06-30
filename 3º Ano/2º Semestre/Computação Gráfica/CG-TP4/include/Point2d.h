#ifndef POINT2D_H
#define POINT2D_H

class Point2d {
    float u;
    float v;

public:
    Point2d();
    Point2d(float a, float b);
    float getU() { return this->u; }
    float getV() { return this->v; }
    void setU( float b) { this->u = b; }
    void setV( float b) { this->v = b; }
};


#endif //POINT2D_H
