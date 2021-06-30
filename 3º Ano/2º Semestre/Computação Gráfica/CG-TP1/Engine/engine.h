#include <stdlib.h>
//
// Created by Joao Amorim
//
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES

#include <math.h>
#include "tinyxml2.h"
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <fstream>
#include <string>
#include <iostream>
#include <vector>

struct Vertice {
    float x;
    float y;
    float z;
};