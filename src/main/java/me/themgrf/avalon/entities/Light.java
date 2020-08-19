package me.themgrf.avalon.entities;

import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Light {

    private Vector3f position;
    private final Vector3f direction;
    private final Vector2f lightBias;
    private RGB colour;

    public Light(Vector3f position, Vector3f direction, RGB colour, Vector2f lightBias) {
        this.direction = direction;
        this.direction.normalise();
        this.position = position;
        this.colour = colour;
        this.lightBias = lightBias;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public RGB getColour() {
        return colour;
    }

    public void setColour(RGB colour) {
        this.colour = colour;
    }

    public Vector2f getLightBias() {
        return lightBias;
    }
}
