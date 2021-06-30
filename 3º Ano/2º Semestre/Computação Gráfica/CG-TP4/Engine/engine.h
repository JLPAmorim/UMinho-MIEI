#include <stdlib.h>
//
// Created by Joao Amorim
//
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif
#include <IL/il.h>

#define _USE_MATH_DEFINES

#include <math.h>
#include <algorithm>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>
#include "tinyxml2.h"
#include "tinyxml.cpp"
#include "tinyxml.h"
#include "tinyxmlerror.cpp"
#include "tinyxmlparser.cpp"
#include "tinystr.cpp"
#include "tinystr.h"
#include "Operation.h"
#include "Translacao.h"
#include "Rotation.h"
#include "Scale.h"
#include "Point.h"
#include "Color.h"
#include "Push.h"
#include "Pop.h"
#include "Draw.h"
#include "Light.h"

extern GLuint* buffersVerts;
extern GLuint* buffersTextsCoords;
extern GLuint* buffersNorms;
extern GLuint* buffersTexID;

float radians(float degrees) {
    return degrees * (M_PI / 180.0f);
}
