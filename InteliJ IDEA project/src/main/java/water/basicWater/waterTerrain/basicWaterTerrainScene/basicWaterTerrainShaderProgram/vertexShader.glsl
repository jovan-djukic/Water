#version 420 core

layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec2 inTextureCoordinate;

uniform mat4 transform, projection, view;
uniform vec4 clippingPlane0;

out vec2 textureCoordinate;

void main() {
    textureCoordinate = inTextureCoordinate;
    vec4 worldPosition = transform * vec4(vertexPosition, 1.0);

    gl_ClipDistance[0] = dot(worldPosition, clippingPlane0);

	gl_Position = projection * view * worldPosition;
}
