package me.themgrf.avalon;

import me.themgrf.avalon.renderer.TexturePack;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class Avalon {

    private static Avalon instance;

    private Random random;
    private TexturePack texturePack;

    public void init() {
        instance = this;
        random = new Random();

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

    public TexturePack getTexturePack() {
        return texturePack;
    }

    public void setTexturePack(TexturePack texturePack) {
        this.texturePack = texturePack;
    }
}
