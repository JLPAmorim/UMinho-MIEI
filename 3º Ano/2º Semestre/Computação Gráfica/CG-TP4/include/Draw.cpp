#include <GL/glew.h>
#include "Draw.h"

extern GLuint* buffersVerts;
extern GLuint* buffersTextsCoords;
extern GLuint* buffersNorms;
extern GLuint* buffersTexID;

void Draw::run() {

    glBindBuffer(GL_ARRAY_BUFFER, buffersVerts[nVBOVert]);
    glVertexPointer(3, GL_FLOAT, 0, 0);

    if (nVBONorm >= 0) {
        glBindBuffer(GL_ARRAY_BUFFER, buffersNorms[nVBONorm]);
        glNormalPointer(GL_FLOAT, 0, 0);
    }

    if (nVBOText >= 0) {
        glBindBuffer(GL_ARRAY_BUFFER, buffersTextsCoords[nVBOText]);
        glTexCoordPointer(2, GL_FLOAT, 0, 0);

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, buffersTexID[nVBOText]);

        glDrawArrays(GL_TRIANGLES, 0, vertexes);

        glBindTexture(GL_TEXTURE_2D, 0);
    }
    else {
        glDrawArrays(GL_TRIANGLES, 0, vertexes);
    }
}