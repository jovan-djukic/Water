#version 420 core

layout (location = 0) in vec3 vertexPosition;

uniform mat4 view, projection;

void main() {
	gl_Position = projection * view * vec4(vertexPosition, 1.0);
}
