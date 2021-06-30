#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#ifndef POP_H
#define POP_H

#include "Operation.h"

class Pop: public Operation {

public:
    Pop();
    void run();
};

#endif //POP_H
