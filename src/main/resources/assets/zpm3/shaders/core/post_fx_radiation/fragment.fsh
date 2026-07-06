#version 150

out vec4 frag_color;
in vec2 uv_coordinates;

uniform float value;
uniform float timer;
uniform sampler2D texture_map;

float random(vec2 p)
{
    return fract(sin(dot(p + tan(timer), vec2(12.9898, 78.233))) * 43758.5453123);
}

void main()
{
    //vec2 tex_size = textureSize(texture_map, 0);
    //vec2 pixel = 1. / textureSize(texture_map, 0);
    //vec2 quanted_uv = floor(uv_coordinates * tex_size / 2.) * 2. * pixel;
    float rand_fl = 1. - random(uv_coordinates) * (sqrt(value) * 0.7);

    vec4 screen = texture(texture_map, uv_coordinates);
    screen = (screen - 0.5) * (1. + value * 0.5) + 0.5;
    frag_color = vec4(screen.rgb * vec3(1.0f, rand_fl, rand_fl), 1.);
}