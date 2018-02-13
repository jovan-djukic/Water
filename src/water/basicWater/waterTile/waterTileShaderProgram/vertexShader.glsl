#version 420 core

layout (location = 0) in vec3 vertexCoordinates;
layout (location = 1) in vec2 inTexelCoordinate;

out vec4 clipSpaceCoordinates;
out vec2 textureCoordinates;

uniform mat4 transform, projection, view;
uniform float scaleX, scaleY;

void main() {
    textureCoordinates = vec2(inTexelCoordinate.x * scaleX, inTexelCoordinate.y * scaleY);

    clipSpaceCoordinates = projection * view * transform * vec4(vertexCoordinates, 1.0);

	gl_Position = clipSpaceCoordinates;
}
