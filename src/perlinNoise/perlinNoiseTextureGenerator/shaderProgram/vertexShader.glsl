#version 420 core

const vec2 positions[] = {
		vec2(-1, +1),
		vec2(-1, -1),
		vec2(+1, +1),
		vec2(+1, -1)
};

out vec2 textureCoordinate;

void main() {
	gl_Position = vec4(positions[gl_VertexID], 0, 1);
}
