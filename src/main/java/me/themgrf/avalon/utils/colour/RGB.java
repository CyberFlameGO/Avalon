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

    public Vector3f asVector3f() {
        return new Vector3f(red/255, green/255, blue/255);
    }
}
