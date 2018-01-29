#version 420 core

layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec2 inTextureCoordinate;

uniform mat4 transform, projection, view;

out float y;
out vec2 textureCoordinate;

void main() {
    y = vertexPosition.y;
    textureCoordinate = inTextureCoordinate;
	gl_Position = projection * view * transform * vec4(vertexPosition, 1.0);
}
