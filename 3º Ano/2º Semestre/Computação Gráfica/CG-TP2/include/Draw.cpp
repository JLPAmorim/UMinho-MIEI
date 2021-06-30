#include "Draw.h"

void Draw::run() {

    glBegin(GL_TRIANGLES);
    for(int i=0; i< vertices.size(); i+=3){

        glVertex3f(vertices[i].getX(), vertices[i].getY(), vertices[i].getZ());
        glVertex3f(vertices[i+1].getX(), vertices[i+1].getY(), vertices[i+1].getZ());
        glVertex3f(vertices[i+2].getX(), vertices[i+2].getY(), vertices[i+2].getZ());
    }
    glEnd();
}