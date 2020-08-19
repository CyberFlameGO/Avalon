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

    @Override
    public byte[] getAsBytes(){
        int r = (int) (getRed() * 255);
        int g = (int) (getGreen() * 255);
        int b = (int) (getBlue() * 255);
        int alpha = (int) (getAlpha() * 255);
        return new byte[]{(byte)r, (byte) g, (byte) b, (byte) alpha};
    }
}
