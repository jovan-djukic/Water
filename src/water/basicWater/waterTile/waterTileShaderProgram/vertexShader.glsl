#version 420 core

layout (location = 0) in vec3 vertexPosition;

out vec4 clipSpaceCoordinates;

uniform mat4 transform, projection, view;

void main() {
    clipSpaceCoordinates = projection * view * transform * vec4(vertexPosition, 1.0);
	gl_Position = clipSpaceCoordinates;
}
