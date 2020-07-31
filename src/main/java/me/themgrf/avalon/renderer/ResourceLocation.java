package me.themgrf.avalon.renderer;

public class ResourceLocation {

    private final String domain, path;

    public ResourceLocation(String path) {
        this.domain = "avalon";
        this.path = path;
    }

    public ResourceLocation(String domain, String path) {
        this.domain = domain;
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
