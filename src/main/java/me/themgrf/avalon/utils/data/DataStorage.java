package me.themgrf.avalon.utils.data;

import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.util.vector.Vector3f;

import java.nio.ByteBuffer;

public class DataStorage {

    private static final int TWO_BITS_MAX = (int) (Math.pow(2, 2) - 1);
    private static final int TEN_BITS_MAX = (int) (Math.pow(2, 10) - 1);
    private static final int BYTE_MAX = (int) (Math.pow(2, 8) - 1);

    public static void packVertexData(Vector3f position, Vector3f normal, RGB colour, ByteBuffer buffer) {
        packVertexData(position.x, position.y, position.z, normal, colour, buffer);
    }

    public static void packVertexData(float x, float y, float z, Vector3f normal, RGB colour, ByteBuffer buffer) {
        storePosition(buffer, x, y, z);
        storeNormal(buffer, normal);
        storeColour(buffer, colour);
    }

    public static void packVertexData(float x, float y, float z, RGB colour, ByteBuffer buffer) {
        storePosition(buffer, x, y, z);
        storeColour(buffer, colour);
    }

    private static void storePosition(ByteBuffer buffer, float x, float y, float z) {
        buffer.putFloat(x);
        buffer.putFloat(y);
        buffer.putFloat(z);
    }

    private static void storeNormal(ByteBuffer buffer, Vector3f normal) {
        int packedInt = pack(normal.x, normal.y, normal.z, 0);
        buffer.putInt(packedInt);
    }

    private static void storeColour(ByteBuffer buffer, RGB colour) {
        byte[] colourBytes = colour.getAsBytes();
        buffer.put(colourBytes);
    }

    private static int pack(float x, float y, float z, float w) {
        int val = 0;
        val = val | (quantizeNormalized(w, TWO_BITS_MAX, false) << 30);
        val = val | (quantizeNormalized(z, TEN_BITS_MAX, true) << 20);
        val = val | (quantizeNormalized(y, TEN_BITS_MAX, true) << 10);
        val = val | quantizeNormalized(x, TEN_BITS_MAX, true);
        return val;
    }

    private static int quantizeNormalized(float original, int highestLevel, boolean signed) {
        if (signed) {
            original = original * 0.5f + 0.5f;
        }
        return Math.round(original * highestLevel);

    }

}
