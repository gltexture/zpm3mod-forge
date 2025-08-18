#version 150

in vec2 uv_coordinates;
out vec4 frag_color;
uniform sampler2D texture_map;
uniform float blur_radius;

void main()
{
  vec2 texel_size = 1.0 / textureSize(texture_map, 0);

  vec4 accum = vec4(0.0);
  float alphaAccum = 0.0;
  int radius = int(blur_radius);

  for (int y = -radius; y <= radius; ++y)
  {
    for (int x = -radius; x <= radius; ++x)
    {
      vec2 offset = vec2(float(x), float(y)) * texel_size;
      vec4 samplet = texture(texture_map, uv_coordinates + offset);

      samplet.rgb *= samplet.a;

    accum.rgb += samplet.rgb;
    alphaAccum += samplet.a;
    }
  }

  float num_samples = float((2 * radius + 1) * (2 * radius + 1));

  accum.rgb /= num_samples;
  alphaAccum /= num_samples;

  if (alphaAccum > 0.0) {
    accum.rgb /= alphaAccum;
  }

  frag_color = vec4(accum.rgb, alphaAccum);
}