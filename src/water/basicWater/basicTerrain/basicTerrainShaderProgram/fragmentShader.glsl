#version 420

out vec4 outColor;

in vec2 textureCoordinate;
in float y;

uniform sampler2D grassTexture, sandTexture;

void main() {
    if (y > 0) {
        outColor = texture(grassTexture, textureCoordinate);
    } else {
        outColor = texture(sandTexture, textureCoordinate);
    }
}


