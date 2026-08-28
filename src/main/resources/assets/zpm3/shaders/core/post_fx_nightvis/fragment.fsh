#version 150

out vec4 frag_color;
in vec2 uv_coordinates;

uniform float timer;
uniform sampler2D texture_map;
uniform float adjBrightnessNormalized;

float random(vec2 p)
{
    return fract(sin(dot(p + tan(timer), vec2(12.9898, 78.233))) * 43758.5453123);
}

void main()
{
    float intensity = cos(timer * 6.) * 0.02;
    vec2 tex_size = textureSize(texture_map, 0);
    vec2 pixel = 1. / textureSize(texture_map, 0);
    vec2 quanted_uv = floor(uv_coordinates * tex_size / 2.) * 2. * pixel;
    float rand_fl = 0.75 + random(quanted_uv.xy) * 0.25;

    vec4 screen = texture(texture_map, quanted_uv);
    screen = (screen - 0.5) * ((1.18 + intensity) - (rand_fl * 0.02)) + 0.5;
    float core = dot(screen.rgb * vec3(rand_fl), vec3(0.2126, 0.7152, 0.0722));
    frag_color = vec4(0., core, 0., 1.) * vec4(vec3((1.0f + (1.5f * adjBrightnessNormalized)) + rand_fl * 0.1), 1.);
}