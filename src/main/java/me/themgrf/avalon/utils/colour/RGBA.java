package me.themgrf.avalon.utils.colour;

import org.lwjgl.util.vector.Vector4f;

public class RGBA extends RGB {

    private final float alpha;

    public RGBA(float red, float green, float blue, float alpha) {
        super(red, green, blue);
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public Vector4f asVector4f() {
        return new Vector4f(getRed()/255, getGreen()/255, getBlue()/255, alpha);
    }
}
