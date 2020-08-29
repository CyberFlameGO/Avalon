package me.themgrf.avalon;

import me.themgrf.avalon.renderer.TexturePack;
import me.themgrf.avalon.renderer.models.Loader;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class Avalon {

    private static Avalon instance;

    private final Random random;
    private final Loader loader;
    private TexturePack texturePack;

    public Avalon() {
        instance = this;

        this.random = new Random();
        this.loader = new Loader();

        setTexturePack(new TexturePack(new HashMap<String, File>(){{
            put("", null);
        }}));
    }

    public static Avalon getInstance() {
        return instance;
    }

    public Random getRandom() {
        return random;
    }

    public Loader getLoader() {
        return loader;
    }

    public TexturePack getTexturePack() {
        return texturePack;
    }

    public void setTexturePack(TexturePack texturePack) {
        this.texturePack = texturePack;
    }
}
