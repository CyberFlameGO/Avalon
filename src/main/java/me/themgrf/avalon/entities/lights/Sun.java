package me.themgrf.avalon.entities.lights;

import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.util.vector.Vector3f;

public class Sun extends Light {

    public Sun(Vector3f position, RGB colour) {
        super(position, colour);
    }
}
