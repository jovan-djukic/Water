#version 420

in vec4 clipSpaceCoordinates;

out vec4 outColor;

uniform sampler2D reflectionTexture, refractionTexture;

void main() {
    vec3 normalizedDeviceCoordinates = clipSpaceCoordinates.xyz / clipSpaceCoordinates.w;

    vec2 textureCoordinates = normalizedDeviceCoordinates.xy / 2 + 0.5;

    vec2 reflectionTextureCoordinates = vec2(textureCoordinates.x, 1 - textureCoordinates.y);
    vec2 refractionTextureCoordinates = textureCoordinates;

    vec4 reflectionColor = texture(reflectionTexture, reflectionTextureCoordinates);
    vec4 refractionColor = texture(refractionTexture, refractionTextureCoordinates);

    outColor = mix(reflectionColor, refractionColor, 0.5);
}


