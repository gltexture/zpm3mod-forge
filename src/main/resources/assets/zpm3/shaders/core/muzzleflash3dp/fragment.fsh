#version 150
#extension GL_ARB_explicit_attrib_location : enable

in vec2 uv_coordinates;
in vec4 out_color;

layout(location = 0) out vec4 frag_color;
layout(location = 1) out vec4 bright_color;

uniform sampler2D Sampler0;
uniform float scissor;

void main()
{
    float cicle = scissor * 2.;
    frag_color = out_color * texture(Sampler0, uv_coordinates);

    vec4 frag_mult = vec4(1. + pow((cicle <= 1.) ? cicle : (2. - cicle), 6.));
    frag_color *= frag_mult;
    float dist = distance(uv_coordinates, vec2(0.5));
    float radius = scissor;
    float fade = 1.0;
    float smoothNess = 0.175;

    if (scissor <= 0.5) {
        fade = smoothstep(radius, radius - smoothNess, dist);
    }
    else {
        float shrinkRadius = 1.0 - (scissor - 0.5) * 2.0;
        fade = smoothstep(shrinkRadius, shrinkRadius - smoothNess, 1. - dist);
    }

    frag_color *= vec4(vec3(fade), fade);
    bright_color = frag_color;
}