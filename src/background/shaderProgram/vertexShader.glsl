#version 420 core

const vec2 positions[] = {
		vec2(-1, +1),
		vec2(-1, -1),
		vec2(+1, +1),
		vec2(+1, -1)
};

const vec2 textureCoordinates[] = {
		vec2( 0, +1),
		vec2( 0,  0),
		vec2(+1, +1),
		vec2(+1,  0)
};

out vec2 textureCoordinate;

void main() {
    textureCoordinate = textureCoordinates[gl_VertexID];
	gl_Position = vec4(positions[gl_VertexID], 0, 1);
}
