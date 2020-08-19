package me.themgrf.avalon.utils.colour;

import org.lwjgl.util.vector.Vector3f;

public class RGB {

    private final float red, green, blue;

    public RGB(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public RGB makeGLCompatible() {
        return new RGB(red / 255, green / 255, blue / 255);
    }

    public Vector3f asVector() {
        return new Vector3f(red, green, blue);
    }

    public byte[] getAsBytes(){
        int r = (int) (red * 255);
        int g = (int) (green * 255);
        int b = (int) (blue * 255);
        return new byte[]{(byte)r, (byte) g, (byte) b, 1};
    }

    public static RGB interpolateColours(RGB colour1, RGB colour2, float blend, RGB dest) {
        float colour1Weight = 1 - blend;

        float r = (colour1Weight * colour1.getRed()) + (blend * colour2.getRed());
        float g = (colour1Weight * colour1.getGreen()) + (blend * colour2.getGreen());
        float b = (colour1Weight * colour1.getBlue()) + (blend * colour2.getBlue());

        if (dest == null) {
            return new RGB(r, g, b);
        } else {
            return dest;
        }
    }
}
