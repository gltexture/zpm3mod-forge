#version 150

in vec2 uv_coordinates;
in vec4 out_color;

out vec4 frag_color;

uniform sampler2D texture_map;

void main()
{
    frag_color = out_color * texture(texture_map, uv_coordinates);
}