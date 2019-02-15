#version 420 core

const vec2[] positions = vec2[4](
  vec2(-1.0, 1.0),
  vec2(-1.0, -1.0),
  vec2(1.0, 1.0),
  vec2(1.0, -1.0)
);

const vec2[] textureCoordinates = vec2[4](
  vec2(0.0, 1.0),
  vec2(0.0, 0.0),
  vec2(1.0, 1.0),
  vec2(1.0, 0.0)
);

out vec2 textureCoordinate;

void main() {
  gl_Position = vec4(positions[gl_VertexID], 0.0, 1.0);
  textureCoordinate = textureCoordinates[gl_VertexID];
}
