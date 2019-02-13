#version 420

in vec4 clipSpaceCoordinates;
in vec2 textureCoordinates;
in vec3 toCameraVector;
in vec3 fromLightVector;

out vec4 outColor;

uniform sampler2D reflectionTexture, refractionTexture, dudvTexture, normalMapTexture;
uniform float waveStrength, moveFactor, distortionStrength, waterReflectivity, shineDamper, lightReflectivity, normalEqualizationFactor;
uniform vec4 lightColor;

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

    vec4 normalMapColor = texture(normalMapTexture, distortedTextureCoords);
    vec3 normal = vec3(
        normalMapColor.r * 2 - 1,
        normalMapColor.b * normalEqualizationFactor,
        normalMapColor.g * 2 - 1
    );
    normal = normalize(normal);

    vec3 normalizedToCameraVector = normalize(toCameraVector);
    float refractiveFactor = dot(normalizedToCameraVector, normal);

    vec3 reflectedLight = reflect(normalize(fromLightVector), normal);
    float specular = max(dot(normalizedToCameraVector, reflectedLight), 0);
    vec3 specularHiglights = lightColor.rgb * pow(specular, shineDamper) * lightReflectivity;

    outColor = mix(reflectionColor, refractionColor, pow(refractiveFactor, waterReflectivity));
    outColor = mix(outColor, vec4(0, 0.2, 0.5, 0.2), 0.2);
    outColor = outColor + vec4(specularHiglights, 0);
}


