package me.themgrf.avalon.entities;

import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

    private RawModel model;
    private Vector3f position;
    private Rotation rotation;
    private int scale;

    public Entity(RawModel model, Vector3f position, Rotation rotation, int scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void increasePosition(float x, float y, float z) {
        setPosition(new Vector3f(position.getX() + x, position.getY() + y, position.getZ() + z));
    }

    public void increaseRotation(Rotation rotation) {
        setRotation(new Rotation(
                this.rotation.getX() + rotation.getX(),
                this.rotation.getY() + rotation.getY(),
                this.rotation.getZ() + rotation.getZ()
        ));
    }

    public RawModel getModel() {
        return model;
    }

    public void setModel(RawModel model) {
        this.model = model;
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

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
