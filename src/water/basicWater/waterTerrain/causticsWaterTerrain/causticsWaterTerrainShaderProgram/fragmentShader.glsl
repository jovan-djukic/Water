#version 420

in vec2 textureCoordinates;
in vec3 waterSurfaceCoordinates;

out vec4 outColor;

uniform sampler2D terrainTexture;
uniform sampler2D dudvTexture, normalMapTexture;
uniform float moveFactor, distortionStrength, shineDamper, lightReflectivity, normalEqualizationFactor;
uniform vec3 lightPosition;
uniform vec4 lightColor;

void main() {
    vec2 distortedTextureCoords = texture(dudvTexture, vec2(textureCoordinates.x + moveFactor, textureCoordinates.y)).rg * distortionStrength;
	distortedTextureCoords = textureCoordinates + vec2(distortedTextureCoords.x, distortedTextureCoords.y +	moveFactor);

    vec4 normalMapColor = texture(normalMapTexture, distortedTextureCoords);
    vec3 normal = vec3(
        normalMapColor.r * 2 - 1,
        normalMapColor.b * normalEqualizationFactor,
        normalMapColor.g * 2 - 1
    );
    normal = normalize(normal);

    vec3 refractoredLight = refract(vec3(0, 1, 0), normal, 1.33);
    vec3 normalizedToLightVector = normalize(lightPosition - waterSurfaceCoordinates);
    float specular = max(dot(normalizedToLightVector, refractoredLight), 0);
    vec3 specularHiglights = lightColor.rgb * pow(specular, shineDamper) * lightReflectivity;

    outColor = texture(terrainTexture, textureCoordinates) + vec4(specularHiglights, 0);
}

