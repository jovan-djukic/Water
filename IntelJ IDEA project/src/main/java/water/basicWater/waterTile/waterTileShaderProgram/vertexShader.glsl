#version 420 core

layout (location = 0) in vec3 vertexCoordinates;
layout (location = 1) in vec2 inTexelCoordinate;

out vec4 clipSpaceCoordinates;
out vec2 textureCoordinates;
out vec3 toCameraVector;
out vec3 fromLightVector;

uniform mat4 transform, projection, view;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

void main() {
    textureCoordinates = inTexelCoordinate;

    vec4 worldPosition = transform * vec4(vertexCoordinates, 1);

    clipSpaceCoordinates = projection * view * worldPosition;

	gl_Position = clipSpaceCoordinates;

	toCameraVector = cameraPosition - worldPosition.xyz;

	fromLightVector = worldPosition.xyz - lightPosition;
}
