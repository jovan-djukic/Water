#version 420 core

layout (location = 0) in vec3 vertexPosition;

uniform mat4 transform, projection, view;

void main() {
	gl_Position = projection * view * transform * vec4(vertexPosition, 1.0);
}
