#version 420

in vec4 clipSpaceCoordinates;
in vec2 textureCoordinates;

out vec4 outColor;

uniform sampler2D reflectionTexture, refractionTexture, dudvTexture;
uniform float waveStrength, moveFactor;

void main() {
    vec2 distortion = (texture(dudvTexture, vec2(textureCoordinates.x + moveFactor, textureCoordinates.y)).rg * 2 - 1) * waveStrength;

    vec2 refractionTextureCoordinates = (clipSpaceCoordinates.xy / clipSpaceCoordinates.w) / 2 + 0.5;
    refractionTextureCoordinates += distortion;

    vec2 reflectionTextureCoordinates = vec2(refractionTextureCoordinates.x, 1 - refractionTextureCoordinates.y);
    reflectionTextureCoordinates += distortion;

    vec4 reflectionColor = texture(reflectionTexture, reflectionTextureCoordinates);
    vec4 refractionColor = texture(refractionTexture, refractionTextureCoordinates);

    outColor = mix(reflectionColor, refractionColor, 0.5);
}


