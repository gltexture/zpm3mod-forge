#version 150

out vec4 frag_color;
in vec2 uv_coordinates;

uniform sampler2D texture_map;

void main()
{
    float dist_raw = 1.- distance(uv_coordinates, vec2(0.5, 0.5));
    float adist = (dist_raw);

    vec2 offset = normalize(uv_coordinates - 0.5) * (1. - adist) * 0.01;

    float r = texture(texture_map, uv_coordinates + offset).r;
    float g = texture(texture_map, uv_coordinates).g;
    float b = texture(texture_map, uv_coordinates - offset).b;

    vec4 screen = vec4(r, g, b, 1.);
    screen = (screen - 0.5) * (1.0 + (1. - adist) * 0.3) + (0.5);
    frag_color = vec4(screen.rgb * vec3(1.0 + adist * 0.4, 1.0, 1.0 + adist * 0.15), 1.);
}