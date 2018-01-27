#version 420#version 420

in vec2 textureCoordinate;

out vec4 outColor;

void main() {
	outColor = vec4(1, textureCoordinate.x, textureCoordinate.y, 1);
}


in vec2 textureCoordinate;

out vec4 outColor;

void main() {
	outColor = vec4(1, textureCoordinate.x, textureCoordinate.y, 1);
}

