package me.themgrf.avalon.utils.colour;

public class RGBA extends RGB {

    private final float alpha;

    public RGBA(float red, float green, float blue, float alpha) {
        super(red, green, blue);
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public RGBA makeGLCompatible() {
        return new RGBA(getRed()/255, getGreen()/255, getBlue()/255, alpha);
    }
}
