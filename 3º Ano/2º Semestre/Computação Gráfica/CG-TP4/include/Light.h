
#ifndef LIGHT_H
#define LIGHT_H
#include <string>
#include <iostream>
#include "Operation.h"

using namespace std;


class Light : public Operation {

    float x;
    float y;
    float z;
    bool enable;
    bool sun;
    string type;


public:
    Light(float x, float y, float z, bool enable, bool sun, string type) {
        this->x = x;
        this->y = y;
        this->z = z;
        this->enable = enable;
        this->sun = sun;
        this->type = type;
    };

    void run();
};

#endif