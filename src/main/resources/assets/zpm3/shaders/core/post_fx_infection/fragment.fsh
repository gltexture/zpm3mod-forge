#version 150

out vec4 frag_color;
in vec2 uv_coordinates;

uniform float value;
uniform sampler2D texture_map;

void main()
{
    vec4 screen = texture(texture_map, uv_coordinates);
    screen = (screen - 0.5) * (1. + value) + 0.5;
    frag_color = vec4(screen.rgb * vec3(1.0f, 1. - value, 1. - value), 1.);
}