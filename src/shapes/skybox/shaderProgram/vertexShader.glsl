#version 420 core

layout (location = 0) in vec3 position;

out vec3 textureCoordinate;

uniform mat4 view, projection;

void main() {
    textureCoordinate = position;
	gl_Position = projection * view * vec4(position, 1);
}
