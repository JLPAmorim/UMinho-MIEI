#ifndef POINT_H
#define POINT_H



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


};;


#endif //POINT_H