#version 150

in vec3 position;
in vec2 texture;
in vec4 color;

out vec2 uv_coordinates;
out vec4 out_color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

void main()
{
    uv_coordinates = texture;
    out_color = color;

    vec4 pos = ProjMat * ModelViewMat * vec4(position, 1.0);
    gl_Position = pos.xyww;
}