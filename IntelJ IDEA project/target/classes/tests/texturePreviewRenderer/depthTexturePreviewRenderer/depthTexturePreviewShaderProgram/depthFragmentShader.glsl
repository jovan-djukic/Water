#version 420

layout (location = 0) out vec4 outColor;

in vec2 textureCoordinate;

uniform sampler2D sampler;
uniform float nearClippingPlane, farClippingPlane;

float linearizeDepth(float z) {
  return (2.0 * nearClippingPlane) / (farClippingPlane + nearClippingPlane - z * (farClippingPlane - nearClippingPlane));
}

void main() {
    outColor = vec4(vec3(linearizeDepth(texture(sampler, textureCoordinate).r)), 1);
}
