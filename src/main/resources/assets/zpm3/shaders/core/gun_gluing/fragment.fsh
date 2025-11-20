#version 150

out vec4 frag_color;
in vec2 uv_coordinates;

uniform sampler2D texture_map;
uniform sampler2D mfash_map;
uniform sampler2D mfash_bloom_map;

uniform sampler2D Sampler4;
uniform sampler2D Sampler5;

uniform vec3 mflash_posRight;
uniform vec3 mflash_posLeft;
uniform float mflash_scissorRight;
uniform float mflash_scissorLeft;

vec3 calcMFlashBlink(vec2 uv, vec3 flash_pos) {
  float dist = distance(uv, flash_pos.xy);
  float effect = 1. - clamp(dist, 0.0, 1.0);
  return pow(effect, 10.) * (vec3(1.0, 0.85, 0.6) * vec3(2.0));
}

float scissorFactor(float orig) {
  orig = clamp(orig, 0., 1.);
  return pow(((orig <= 0.5f) ? (orig) : (1. - orig)) * 2., 4.);
}

vec4 getItemColFixed(vec4 color, vec2 texelSize) {
  vec4 up = texture(texture_map, uv_coordinates + vec2(0.0, texelSize.y));
  vec4 down = texture(texture_map, uv_coordinates - vec2(0.0, texelSize.y));
  vec4 left = texture(texture_map, uv_coordinates - vec2(texelSize.x, 0.0));
  vec4 right = texture(texture_map, uv_coordinates + vec2(texelSize.x, 0.0));

  vec4 upLeft = texture(texture_map, uv_coordinates + vec2(-texelSize.x, texelSize.y));
  vec4 upRight = texture(texture_map, uv_coordinates + vec2( texelSize.x, texelSize.y));
  vec4 downLeft = texture(texture_map, uv_coordinates + vec2(-texelSize.x, -texelSize.y));
  vec4 downRight = texture(texture_map, uv_coordinates + vec2(texelSize.x, -texelSize.y));

  if (up.a > 0.0 && down.a > 0.0) return vec4((up.rgb + down.rgb) * 0.5, up.a);
  if (left.a > 0.0 && right.a > 0.0) return vec4((left.rgb + right.rgb) * 0.5, left.a);
  if (upLeft.a > 0.0 && downRight.a > 0.0) return vec4((upLeft.rgb + downRight.rgb) * 0.5, upLeft.a);
  if (upRight.a > 0.0 && downLeft.a > 0.0) return vec4((upRight.rgb + downLeft.rgb) * 0.5, upRight.a);

  return color;
}

void main()
{
    vec4 gun = getItemColFixed(texture(texture_map, uv_coordinates), vec2(1.) / textureSize(texture_map, 0));
    vec4 mflash = texture(mfash_map, uv_coordinates);
    vec4 bloom = texture(mfash_bloom_map, uv_coordinates);

    vec3 finalColor = (calcMFlashBlink(uv_coordinates, mflash_posLeft) * scissorFactor(mflash_scissorLeft) + calcMFlashBlink(uv_coordinates, mflash_posRight) * scissorFactor(mflash_scissorRight)) * gun.a;

    vec4 mixedGunMFlash = mix(gun + vec4(finalColor, 0.0), vec4(bloom.rgb, 1.0), bloom.a);
    frag_color = mixedGunMFlash + mflash;
}
