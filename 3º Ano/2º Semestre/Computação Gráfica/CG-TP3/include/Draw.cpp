#include <GL/glew.h>
#include "Draw.h"

extern GLuint* buffers;
extern int iVBO;

void Draw::run() {

    glBindBuffer(GL_ARRAY_BUFFER, buffers[iVBO]);
    iVBO++;
    glVertexPointer(3, GL_FLOAT, 0, 0);

    glDrawArrays(GL_TRIANGLES, 0, vertices);
}