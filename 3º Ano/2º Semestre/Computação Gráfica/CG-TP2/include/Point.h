#ifndef POINT_H
#define POINT_H



class Point {
    float x;
    float y;
    float z;

public:
    Point();
    Point(float a, float b, float c);
    float getX() { return this->x; }
    float getY() { return this->y; }
    float getZ() { return this->z; }
    void setX( float b) { this->x = b; }
    void setY( float b) { this->y = b; }
    void setZ( float b) { this->z = b; }

};


#endif //POINT_H