package me.themgrf.avalon.terrain.generation;

import me.themgrf.avalon.utils.Maths;
import me.themgrf.avalon.utils.colour.RGB;

public class ColourGenerator {

    private final float spread, halfSpread, part;
    private final RGB[] biomeColours;

    public ColourGenerator(RGB[] biomeColours, float spread) {
        this.biomeColours = biomeColours;
        this.spread = spread;
        this.halfSpread = spread / 2f;
        this.part = 1f / (biomeColours.length - 1);
    }

    /**
     * Linearly interpolates the calculated colour for each vertex on a
     * terrain between the biome colours dependant on the vertex's height.
     *
     * @param heights The terrain's vertex's heights
     * @param amplitude The generation range of the used terrain
     * @return All the colours of the vertices in the terrain as a grid
     */
    public RGB[][] generateColours(float[][] heights, float amplitude) {
        RGB[][] colours = new RGB[heights.length][heights.length];
        for (int z = 0; z < heights.length; z++) {
            for (int x = 0; x < heights[z].length; x++) {
                colours[z][x] = calculateColour(heights[z][x], amplitude);
            }
        }
        return colours;
    }

    /**
     * Calculates a vertex's colour based on its height.
     *
     * @param height The height of the vertex
     * @param amplitude The maximum height of the vertex
     * @return The interpolated colour of the vertex
     */
    private RGB calculateColour(float height, float amplitude) {
        float value = (height + amplitude) / (amplitude * 2);
        value = Maths.clamp((value - halfSpread) * (1f / spread), 0, 0.9999f);
        int firstBiome = (int) Math.floor(value/ part);
        float blend = (value - (firstBiome * part) / part);
        return RGB.interpolateColours(biomeColours[firstBiome], biomeColours[firstBiome + 1], blend, null);
    }


}
