#version 420 core

layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec2 inTextureCoordinate;

uniform mat4 transform;

out vec2 textureCoordinate;

void main() {
	textureCoordinate = inTextureCoordinate;
	gl_Position = transform * vec4(vertexPosition, 1.0);
}
