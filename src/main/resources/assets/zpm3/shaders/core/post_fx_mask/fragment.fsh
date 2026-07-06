#version 150

out vec4 frag_color;
in vec2 uv_coordinates;
uniform sampler2D texture_map;

void main()
{
    float dist_raw = 1.- distance(uv_coordinates, vec2(0.5, 0.5));
    float adist = (dist_raw * dist_raw);
    vec4 screen = texture(texture_map, uv_coordinates) * adist;
    frag_color = screen;
}