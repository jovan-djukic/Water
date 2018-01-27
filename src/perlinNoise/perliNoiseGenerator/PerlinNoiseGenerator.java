package perlinNoise.perliNoiseGenerator;

public class PerlinNoiseGenerator {
	private static PerlinNoiseGenerator instance;
	
	public static PerlinNoiseGenerator getInstance() {
		if (PerlinNoiseGenerator.instance == null) {
			PerlinNoiseGenerator.instance = new PerlinNoiseGenerator();
		}
		
		return PerlinNoiseGenerator.instance;
	}
	
	private static final int permutations[] = {
			151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142,
			8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203,
			117, 35, 11, 32, 57, 177, 33,
			88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
			77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
			102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
			135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
			5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
			223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
			129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
			251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
			49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
			138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180,
			151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142,
			8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203,
			117, 35, 11, 32, 57, 177, 33,
			88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
			77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
			102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
			135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
			5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
			223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
			129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
			251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
			49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
			138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
	};
	
	private static final float gradients[][]	= {
				{ 0, +1},
				{+1, +1},
				{+1,  0},
				{+1, -1},
				{ 0, -1},
				{-1, -1},
				{-1,  0},
				{-1, +1},
			};
	
	private float fade(float t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}
	
	private float lerp(float t, float a, float b) {
		float theta = (float) (t * Math.PI);
		t = (float) ((1 - Math.cos(theta)) * 0.5);
		return a + t * (b - a);
	}
	
	private float dot2D(int g, float x, float y) {
		return gradients[g][0] * x + gradients[g][1] * y;
	}
	
	private float perlinNoise(float x, float y) {
		int ix = (int) x;
		int iy = (int) y;
		
		x = x - ix;
		y = y - iy;
		
		float fx = fade(x);
		float fy = fade(y);
		
		ix = ix & 255;
		iy = iy & 255;
		
		int g00 = PerlinNoiseGenerator.permutations[ix + 0 + PerlinNoiseGenerator.permutations[iy + 0]];
		int g10 = PerlinNoiseGenerator.permutations[ix + 1 + PerlinNoiseGenerator.permutations[iy + 0]];
		int g01 = PerlinNoiseGenerator.permutations[ix + 0 + PerlinNoiseGenerator.permutations[iy + 1]];
		int g11 = PerlinNoiseGenerator.permutations[ix + 1 + PerlinNoiseGenerator.permutations[iy + 1]];
		
		float n00 = dot2D(g00 & 7, x - 0, y - 0);
		float n10 = dot2D(g10 & 7, x - 1, y - 0);
		float n01 = dot2D(g01 & 7, x - 0, y - 1);
		float n11 = dot2D(g11 & 7, x - 1, y - 1);
		
		float y0 = lerp(fx, n00, n10);
		float y1 = lerp(fx, n01, n11);
		
		return (lerp(fy, y0, y1) + 1) / 2;
	}
	
	public float perlinNoise(float x, float y, int numberOfOctaves, float persistence) {
		float total = 0;
		float frequency = 1;
		float amplitude = 1;
		float maxValue = 0;
		
		for (int k = 0; k < numberOfOctaves; k++) {
			total += perlinNoise(x * frequency, y * frequency) * amplitude;
			
			maxValue += amplitude;
			
			amplitude *= persistence;
			frequency *= 2;
		}
		
		return total / maxValue;
	}
}
