package me.themgrf.avalon.terrain;

import me.themgrf.avalon.Avalon;
import me.themgrf.avalon.renderer.ResourceLocation;
import me.themgrf.avalon.renderer.models.Loader;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Terrain {

    private static final float SIZE = 800;
    //private static final int VERTEX_COUNT = 64;
    private static final float MAX_HEIGHT = 40;
    private static final float MIN_HEIGHT = -40;
    private static final float MAX_PIXEL_COLOUR = 256 * 256* 256;

    private final float x, y;
    private final RawModel rawModel;
    private final ModelTexture texture;

    public Terrain(int x, int y, Loader loader, ModelTexture texture, ResourceLocation heightMap) {
        this.texture = texture;
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.rawModel = generateTerrain(loader, heightMap);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    private RawModel generateTerrain(Loader loader, ResourceLocation heightMap) {
        BufferedImage image = null;
        int vertexCount = 0;
        try {
            image = ImageIO.read(Avalon.getInstance().getTexturePack().getResourceStream(heightMap));
            vertexCount = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        int count = vertexCount * vertexCount;

        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];
        int vertexPointer = 0;

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) vertexCount - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = getHeight(j, i, image);
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
                Vector3f normal = calculateNormal(j, i, image);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int gz = 0; gz < vertexCount - 1; gz++) {
            for (int gx = 0; gx < vertexCount - 1; gx++) {
                int topLeft = (gz * vertexCount) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * vertexCount) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }

        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

    private float getHeight(int x, int y, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || y < 0 || y >= image.getHeight()) {
            return 0;
        }
        float height = image.getRGB(x, y);
        height += MAX_PIXEL_COLOUR / 2;
        height /= MAX_PIXEL_COLOUR / 2;
        height *= MAX_HEIGHT;
        return height;
    }

    private Vector3f calculateNormal(int x, int y, BufferedImage image) {
        float heightL = getHeight(x -1, y, image);
        float heightR = getHeight(x + 1, y, image);
        float heightD = getHeight(x, y - 1, image);
        float heightU = getHeight(x, y + 1, image);

        Vector3f normal = new Vector3f(heightL - heightR, 2, heightD - heightU);
        normal.normalise();

        return normal;
    }

}
