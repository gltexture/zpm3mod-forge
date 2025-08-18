#version 150

out vec4 frag_color;
in vec2 uv_coordinates;
uniform sampler2D texture_map;

void main()
{
  vec4 tex = texture(texture_map, uv_coordinates);
  frag_color = tex;
}
