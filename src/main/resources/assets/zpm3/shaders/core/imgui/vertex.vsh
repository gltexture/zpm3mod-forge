    #version 150

in vec2 position;
in vec2 texture;
in vec4 color;

out vec2 uv_coordinates;
out vec4 out_color;

uniform vec2 scale;

void main()
{
    uv_coordinates = texture;
    out_color = color;

    gl_Position = vec4(position * scale + vec2(-1.0, 1.0), 0.0f, 1.0);
}