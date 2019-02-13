#version 420

layout (location = 0) out vec4 outColor;

in vec2 textureCoordinate;

uniform sampler2D sampler;

void main() {
    outColor = texture(sampler, textureCoordinate);
}
