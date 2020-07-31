package me.themgrf.avalon.utils;

import org.lwjgl.util.vector.Vector3f;

public class Location {

    private Vector3f position;
    private Rotation rotation;

    public Location(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
        this.rotation = new Rotation(0, 0, 0);
    }

    public Location(float x, float y, float z, Rotation rotation) {
        this.position = new Vector3f(x, y, z);
        this.rotation = rotation;
    }

    public Location(Vector3f position) {
        this.position = position;
        this.rotation = new Rotation(0, 0, 0);
    }

    public Location(Vector3f position, Rotation rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }
}
