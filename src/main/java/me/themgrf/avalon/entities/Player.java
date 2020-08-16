package me.themgrf.avalon.entities;

import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.utils.Location;

public class Player extends Entity {

    private Camera camera;
    private String name;

    public Player(Camera camera, String name) {
        super(new RawModel(0, 0), new Location(camera.getPosition(), camera.getRotation()), 1);
        this.camera = camera;
        this.name = name;

        this.camera.getRotation().setX(10);
    }

    public void move() {
        camera.move();
    }

    //region Getters & Setters

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //endregion
}
