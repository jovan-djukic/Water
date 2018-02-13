#version 420

out vec4 outColor;

in vec2 textureCoordinate;
in float y;

uniform sampler2D terrainTexture;

void main() {
       outColor = texture(terrainTexture, textureCoordinate);
}


