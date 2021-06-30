#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#ifndef PUSH_H
#define PUSH_H

#include "Operation.h"

class Push: public Operation{

public:
    Push();
    void run();
};


#endif //PUSH_H