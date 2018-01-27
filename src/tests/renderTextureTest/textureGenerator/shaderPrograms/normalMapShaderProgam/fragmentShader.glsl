#version 420

layout (location = 0) out vec4 normalTexture;

uniform float width, height;

void main() {
	float x = gl_FragCoord.x / width;
	float y = gl_FragCoord.y / height;
	
	normalTexture = vec4(0, 0, (x + y) / 2, 1);
}
