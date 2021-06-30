#include "Light.h"
#include <GL/glew.h>

void Light::run() {
    if(type == "POINT")
        if(enable == true && sun == true) {
            glEnable(GL_LIGHT0);
            glEnable(GL_LIGHTING);
            float pos[4] = { x,y,z,1};
            glLightfv(GL_LIGHT0, GL_POSITION, pos);

            GLfloat amb[4] = { 0.0, 0.0, 0.0, 1.0 };
            GLfloat diff[4] = { 1.0, 1.0, 1.0, 1.0 };
            GLfloat spec[4] = { 0, 0, 0, 1 };

            glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
            glLightfv(GL_LIGHT0, GL_DIFFUSE, diff);
            glLightfv(GL_LIGHT0, GL_SPECULAR, spec);

            GLfloat matt[3] = { 5, 5, 5 };
            glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, matt);
        }
        else if (enable == true && sun == false) {
            glEnable(GL_LIGHT0);
            glEnable(GL_LIGHTING);

            GLfloat amb[4] = { 1, 0.8, 0.8, 1.0 };
            glLightfv(GL_LIGHT0, GL_AMBIENT, amb);

            GLfloat matt[3] = { 0.1, 0.1, 0.1 };
            glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, matt);
        }
        else if(enable == false){
            glDisable(GL_LIGHTING);
        }
    else if (type == "DIRECTIONAL") {
            if (enable == true) {
                glEnable(GL_LIGHT0);
                glEnable(GL_LIGHTING);

                float pos[4] = { x,y,z,0 };
                glLightfv(GL_LIGHT0, GL_POSITION, pos);

                GLfloat amb[4] = { 0.0, 0.0, 0.0, 1.0 };
                GLfloat diff[4] = { 1.0, 1.0, 1.0, 1.0 };
                GLfloat spec[4] = { 0, 0, 0, 1 };

                glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
                glLightfv(GL_LIGHT0, GL_DIFFUSE, diff);
                glLightfv(GL_LIGHT0, GL_SPECULAR, spec);
            }
            else {
                glDisable(GL_LIGHTING);
            }
    }
    else if (type == "SPOT") {
            if (enable == true){
                glEnable(GL_LIGHT0);
                glEnable(GL_LIGHTING);

                float pos[4] = { x,y,z,1 };
                glLightfv(GL_LIGHT0, GL_POSITION, pos);

                GLfloat amb[4] = { 0.0, 0.0, 0.0, 1.0 };
                GLfloat diff[4] = { 1.0, 1.0, 1.0, 1.0 };
                GLfloat spec[4] = { 0, 0, 0, 1 };

                glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
                glLightfv(GL_LIGHT0, GL_DIFFUSE, diff);
                glLightfv(GL_LIGHT0, GL_SPECULAR, spec);

                GLfloat spotDir[3] = { 0.0, 0.0, -1.0 };

                glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, spotDir);
                glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 45.0);
                glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 0.0);

            }else{
                glDisable(GL_LIGHTING);
            }
    }
    else if (type == "COLOR") {
            GLfloat diff[4] = { 1.0, 1.0, 1.0, 1.0 };
            GLfloat spec[4] = { 0.0, 0.0, 0.0, 1.0 };
            GLfloat emis[4] = { 0.0, 0.0, 0.0, 1.0 };
            GLfloat amb[4] = { 1.0, 1.0, 1.0, 1.0 };
            
            glMaterialfv(GL_FRONT, GL_DIFFUSE, diff);
            glMaterialfv(GL_FRONT, GL_SPECULAR, spec);
            glMaterialfv(GL_FRONT, GL_EMISSION, emis);
            glMaterialfv(GL_FRONT, GL_AMBIENT, amb);
    }
    



        

}