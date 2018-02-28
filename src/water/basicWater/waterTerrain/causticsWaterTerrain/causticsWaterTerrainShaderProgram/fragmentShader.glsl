#version 420

#define eta 0.75

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
        normalMapColor.b,
        normalMapColor.g * 2 - 1
    );
    normal = normalize(normal);

    vec3 refractedRay = vec3(0, -1, 0);
    float theta = dot(-normal, refractedRay);
    vec3 incidentRay = eta * refractedRay + normal * (eta * theta - eta * sqrt(1 - eta * eta * sqrt(1 - theta * theta)));
    incidentRay = normalize(-incidentRay);

    vec3 normalizedToLightVector = normalize(lightPosition - waterSurfaceCoordinates);
    float specular = max(dot(normalizedToLightVector, incidentRay), 0);
    specular = pow(specular, shineDamper);
    vec3 specularHiglights = lightColor.rgb * specular * lightReflectivity;

    outColor = texture(terrainTexture, textureCoordinates) + vec4(specularHiglights, 0);
}

