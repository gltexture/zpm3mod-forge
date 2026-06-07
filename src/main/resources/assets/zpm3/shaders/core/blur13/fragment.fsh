#version 150

in vec2 uv_coordinates;
out vec4 frag_color;
uniform sampler2D texture_map;
uniform vec2 direction;

vec4 blur(sampler2D txt, vec2 uv)
{
  vec2 texel = 1.0 / vec2(textureSize(txt, 0));
  vec4 color = vec4(0.0);
  vec2 off1 = direction * 1.411764705882353;
  vec2 off2 = direction * 3.2941176470588234;
  vec2 off3 = direction * 5.176470588235294;
  color += texture(txt, uv) * 0.1964825501511404;
  color += texture(txt, uv + off1 * texel) * 0.2969069646728344;
  color += texture(txt, uv - off1 * texel) * 0.2969069646728344;
  color += texture(txt, uv + off2 * texel) * 0.09447039785044732;
  color += texture(txt, uv - off2 * texel) * 0.09447039785044732;
  color += texture(txt, uv + off3 * texel) * 0.010381362401148057;
  color += texture(txt, uv - off3 * texel) * 0.010381362401148057;
  return color;
}

void main()
{
  frag_color = blur(texture_map, uv_coordinates);
}