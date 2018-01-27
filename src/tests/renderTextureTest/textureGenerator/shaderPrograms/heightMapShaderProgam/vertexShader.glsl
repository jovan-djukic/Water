#version 420 core

const vec2[] position = {
		vec2(-1, +1),
		vec2(-1, -1),
		vec2(+1, +1),
		vec2(+1, -1)
};

void main() {
	gl_Position = vec4(position[gl_VertexID], 0, 1);
}
