package me.themgrf.avalon.entities.lights;

import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.util.vector.Vector3f;

public class Light {

    private Vector3f position;
    private RGB colour;

    public Light(Vector3f position, RGB colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public RGB getColour() {
        return colour;
    }

    public void setColour(RGB colour) {
        this.colour = colour;
    }
}
