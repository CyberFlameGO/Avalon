package me.themgrf.avalon.renderer;

public class ResourceLocation {

    private final String path;

    public ResourceLocation(String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
