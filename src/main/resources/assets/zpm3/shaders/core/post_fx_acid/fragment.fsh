#version 150

out vec4 frag_color;
in vec2 uv_coordinates;

uniform float timer;
uniform float value;
uniform sampler2D texture_map;

float random(vec2 p)
{
    return fract(sin(dot(p + tan(timer), vec2(12.9898, 78.233))) * 43758.5453123);
}

void main()
{
    vec2 tex_size = textureSize(texture_map, 0);
    vec2 pixel = 1. / textureSize(texture_map, 0);
    vec2 quanted_uv = floor(uv_coordinates * tex_size / 4.) * 4. * pixel;
    float rand_fl = 0.25 + random(quanted_uv.xy) * 0.75;

    vec4 screen = texture(texture_map, uv_coordinates);
    screen = (screen - 0.5) * (1.0 + (value * 0.5)) + (0.5);

    vec3 colorMutation = mix(vec3(1.), vec3(0.8, 0.5 + rand_fl * 1.5, 0.8), value);
    frag_color = vec4(screen.rgb * colorMutation, 1.);
}