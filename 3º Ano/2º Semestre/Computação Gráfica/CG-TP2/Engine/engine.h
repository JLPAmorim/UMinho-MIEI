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
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include "tinyxml2.h"
#include "Operation.h"
#include "Translacao.h"
#include "Rotation.h"
#include "Scale.h"
#include "Point.h"
#include "Color.h"
#include "Push.h"
#include "Pop.h"
#include "Draw.h"
