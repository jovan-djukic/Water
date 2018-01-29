#version 420

layout (location = 0) out vec4 outColor;

in vec3 textureCoordinate;

uniform samplerCube cubeMap;

void main() {
    outColor = texture(cubeMap, textureCoordinate);
}
