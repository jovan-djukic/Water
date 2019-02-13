#version 420 core

layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec2 inTextureCoordinates;

uniform mat4 transform, projection, view;
uniform vec4 clippingPlane0;
uniform float waterHeight;

out vec2 textureCoordinates;
out vec3 waterSurfaceCoordinates;

void main() {
    textureCoordinates = inTextureCoordinates;
    vec4 worldPosition = transform * vec4(vertexPosition, 1.0);

    waterSurfaceCoordinates = vec3(worldPosition.x, waterHeight, worldPosition.z);

    gl_ClipDistance[0] = dot(worldPosition, clippingPlane0);

	gl_Position = projection * view * worldPosition;
}
