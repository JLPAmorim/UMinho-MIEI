#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <vector>
#include "Point.h"
#include "Point2d.h"
#include "Operation.h"

#ifndef DRAW_H
#define DRAW_H

class Draw : public Operation {

    int vertexes;
    int nVBOVert;
    int nVBOText;
    int nVBONorm;

public:
    Draw(int vertexes, int nVBOVert, int nVBOText, int nVBONorm) {
        this->vertexes = vertexes;
        this->nVBOVert = nVBOVert;
        this->nVBOText = nVBOText;
        this->nVBONorm = nVBONorm;
    };

    void run();

};


#endif //DRAW_H