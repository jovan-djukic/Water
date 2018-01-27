#version 420

layout (location = 0) out vec4 outColor;

in vec2 textureCoordinate;

uniform sampler2D background;

void main() {
    outColor = texture(background, textureCoordinate);
}
