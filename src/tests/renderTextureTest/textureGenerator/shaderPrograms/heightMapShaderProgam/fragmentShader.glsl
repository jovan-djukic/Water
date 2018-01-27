#version 420

layout (location = 0) out vec4 heightMapTexture;

uniform float width, height;

void main() {
	float x = gl_FragCoord.x / width;
	float y = gl_FragCoord.y / height;
	
	heightMapTexture = vec4(1 - (x + y) / 2, 0, 0, 1);
}
