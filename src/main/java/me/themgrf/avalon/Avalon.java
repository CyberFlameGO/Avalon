package me.themgrf.avalon;

import me.themgrf.avalon.renderer.TexturePack;

import java.io.File;
import java.util.HashMap;

public class Avalon {

    private static Avalon instance;

    private TexturePack texturePack;

    public void init() {
        instance = this;

        setTexturePack(new TexturePack(new HashMap<String, File>(){{
            put("", null);
        }}));
    }

    public static Avalon getInstance() {
        return instance;
    }

    public TexturePack getTexturePack() {
        return texturePack;
    }

    public void setTexturePack(TexturePack texturePack) {
        this.texturePack = texturePack;
    }
}
