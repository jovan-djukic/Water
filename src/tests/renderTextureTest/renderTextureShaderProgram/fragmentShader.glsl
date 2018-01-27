#version 420

uniform sampler2D heightMapTexture, normalMapTexture;

in vec2 textureCoordinate;

out vec4 outColor;

void main() {
	vec4 heightMapColor = texture(heightMapTexture, textureCoordinate);
	vec4 normalColor = texture(normalMapTexture, textureCoordinate);
	
	outColor = mix(heightMapColor, normalColor, 0.5);
}

