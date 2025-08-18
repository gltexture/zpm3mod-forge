#version 150

in vec3 position;
in vec2 texture;

out vec2 uv_coordinates;
uniform mat4 sModelViewMat;
uniform mat4 sProjMat;

void main()
{
    gl_Position = sProjMat * sModelViewMat * vec4(position, 1.0);
    uv_coordinates = texture;
}
