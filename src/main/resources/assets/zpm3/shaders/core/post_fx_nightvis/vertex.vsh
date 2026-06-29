#version 150

in vec2 position;
in vec2 texture;

out vec2 uv_coordinates;

void main()
{
    gl_Position = vec4(position * 2.0 - 1.0, 0.0, 1.0);
    uv_coordinates = texture;
}