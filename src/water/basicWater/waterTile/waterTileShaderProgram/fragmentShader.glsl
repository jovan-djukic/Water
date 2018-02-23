#version 420

in vec4 clipSpaceCoordinates;
in vec2 textureCoordinates;
in vec3 toCameraVector;

out vec4 outColor;

uniform sampler2D reflectionTexture, refractionTexture, dudvTexture;
uniform float waveStrength, moveFactor, distortionStrength, waterReflectivity;

void main() {
    vec2 distortedTextureCoords = texture(dudvTexture, vec2(textureCoordinates.x + moveFactor, textureCoordinates.y)).rg * distortionStrength;
	distortedTextureCoords = textureCoordinates + vec2(distortedTextureCoords.x, distortedTextureCoords.y +	moveFactor);
	vec2 totalDistortion = (texture(dudvTexture, distortedTextureCoords).rg * 2.0 - 1.0) * waveStrength;

    vec2 refractionTextureCoordinates = (clipSpaceCoordinates.xy / clipSpaceCoordinates.w) / 2 + 0.5;
    refractionTextureCoordinates += totalDistortion;

    vec2 reflectionTextureCoordinates = vec2(refractionTextureCoordinates.x, 1 - refractionTextureCoordinates.y);
    reflectionTextureCoordinates += totalDistortion;

    vec4 reflectionColor = texture(reflectionTexture, reflectionTextureCoordinates);
    vec4 refractionColor = texture(refractionTexture, refractionTextureCoordinates);

    vec3 normalizedToCameraVector = normalize(toCameraVector);
    float refractiveFactor = dot(normalizedToCameraVector, vec3(0, 1, 0));

    outColor = mix(reflectionColor, refractionColor, pow(refractiveFactor, waterReflectivity));
}


