package me.themgrf.avalon.api;

import me.themgrf.avalon.entities.lights.Sun;
import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.util.vector.Vector3f;

public class World {

    private final Sun sun;

    private String name;

    public World(String name) {
        this.name = name;
        this.sun = new Sun(new Vector3f(-500, 100, 0), new RGB(255, 255, 255));
    }

    public World(String name, Sun sun) {
        this.name = name;
        this.sun = sun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sun getSun() {
        return sun;
    }
}
