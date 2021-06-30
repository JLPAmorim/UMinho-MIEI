#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <vector>
#include "Point.h"
#include "Operation.h"

#ifndef DRAW_H
#define DRAW_H


class Draw: public Operation{

    std::vector<Point> vertices;

public:
    Draw(std::vector<Point> vertices){
        this->vertices=vertices;
    };

    void run();

};


#endif //DRAW_H