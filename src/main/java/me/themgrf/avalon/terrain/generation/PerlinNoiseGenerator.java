package me.themgrf.avalon.terrain.generation;

import me.themgrf.avalon.Avalon;

import java.util.Random;

/**
 * Perlin noise generator for creating the random height of terrain
 */
public class PerlinNoiseGenerator {

    private final int seed, octaves;
    private final float roughness, amplitude;

    /**
     * Constructor for creating a perlin noise generator with a specified seed
     * @param seed The seed to use in generation
     * @param octaves The amount of octaves to use
     * @param roughness The roughness of the terrain
     * @param amplitude The amplitude of the terrain
     */
    public PerlinNoiseGenerator(int seed, int octaves, float roughness, float amplitude) {
        this.seed = seed;
        this.octaves = octaves;
        this.roughness = roughness;
        this.amplitude = amplitude;
    }

    /**
     * Constructor for creating a perlin noise generator with a random seed
     * @param octaves The amount of octaves to use
     * @param roughness The roughness of the terrain
     * @param amplitude The amplitude of the terrain
     */
    public PerlinNoiseGenerator(int octaves, float roughness, float amplitude) {
        this.seed = Avalon.getInstance().getRandom().nextInt(Integer.MAX_VALUE);
        this.octaves = octaves;
        this.roughness = roughness;
        this.amplitude = amplitude;
    }

    public int getSeed() {
        return seed;
    }

    public int getOctaves() {
        return octaves;
    }

    public float getRoughness() {
        return roughness;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public float getPerlinNoise(int x, int y) {
        float total = 0;
        float d = (float) Math.pow(2, octaves - 1);
        for (int i = 0; i < octaves; i++) {
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(roughness, i) * amplitude;
            total += getInterpolatedNoise(x * freq, y * freq) * amp;
        }
        return total;
    }

    private float getSmoothNoise(int x, int y) {
        float corners = (getNoise(x - 1, y - 1) + getNoise(x + 1, y - 1) + getNoise(x - 1, y + 1)
                + getNoise(x + 1, y + 1)) / 16f;
        float sides = (getNoise(x - 1, y) + getNoise(x + 1, y) + getNoise(x, y - 1) + getNoise(x, y + 1)) / 8f;
        float center = getNoise(x, y) / 4f;

        return corners + sides + center;
    }

    private float getNoise(int x, int y) {
        Random random = Avalon.getInstance().getRandom();
        random.setSeed(x * 49632 + y * 325176 + seed);
        return random.nextFloat() * 2f - 1f;
    }

    private float getInterpolatedNoise(float x, float y) {
        int intX = (int) x;
        float fracX = x - intX;
        int intY = (int) y;
        float fracY = y - intY;

        float v1 = getSmoothNoise(intX, intY);
        float v2 = getSmoothNoise(intX + 1, intY);
        float v3 = getSmoothNoise(intX, intY + 1);
        float v4 = getSmoothNoise(intX + 1, intY + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracY);
    }

    private float interpolate(float a, float b, float blend) {
        double theta = blend * Math.PI;
        float f = (float) ((1f - Math.cos(theta)) * 0.5f);
        return a * (1 - f) + b * f;
    }
}
