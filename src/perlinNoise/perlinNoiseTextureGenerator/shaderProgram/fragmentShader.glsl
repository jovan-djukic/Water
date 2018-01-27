#version 420

#define M_PI 3.1415926535897932384626433832795

layout (location = 0) out float outColor;

uniform int permutation[512];
uniform int numberOfOctaves;
uniform float persistence;
uniform float scaleX, scaleY;

const vec2[] gradients = {
    vec2( 0, +1),
    vec2(+1, +1),
    vec2(+1,  0),
    vec2(+1, -1),
    vec2( 0, -1),
    vec2(-1, -1),
    vec2(-1,  0),
    vec2(-1, +1),
};

float fade(float t) {
    return t * t * t * (t * (t * 6 - 15) + 10);
}

float lerp(float t, float a, float b) {
    float theta = t * M_PI;
    t = (1 - cos(theta)) * 0.5;
    return a + t * (b - a);
}

float dot2D(int g, float x, float y) {
    return gradients[g].x * x + gradients[g].y * y;
}

float perlinNoise(float x, float y) {
    int ix = int(x);
    int iy = int(y);

    x = x - ix;
    y = y - iy;

    float fx = fade(x);
    float fy = fade(y);

    ix = ix & 255;
    iy = iy & 255;

    int g00 = permutation[ix + 0 + permutation[iy + 0]];
    int g10 = permutation[ix + 1 + permutation[iy + 0]];
    int g01 = permutation[ix + 0 + permutation[iy + 1]];
    int g11 = permutation[ix + 1 + permutation[iy + 1]];

    float n00 = dot2D(g00 & 7, x - 0, y - 0);
    float n10 = dot2D(g10 & 7, x - 1, y - 0);
    float n01 = dot2D(g01 & 7, x - 0, y - 1);
    float n11 = dot2D(g11 & 7, x - 1, y - 1);

    float y0 = lerp(fx, n00, n10);
    float y1 = lerp(fx, n01, n11);

    return (lerp(fy, y0, y1) + 1) / 2;
}

void main() {
    float x = gl_FragCoord.x * scaleX;
    float y = gl_FragCoord.y * scaleY;
    float total = 0;
    float frequency = 1;
    float amplitude = 1;
    float maxValue = 0;

    for (int i = 0; i < numberOfOctaves; i++) {
        total += perlinNoise(x * frequency, y * frequency) * amplitude;

        maxValue += amplitude;

        amplitude *= persistence;
        frequency *= 2;
    }

    outColor = total / maxValue;
}
