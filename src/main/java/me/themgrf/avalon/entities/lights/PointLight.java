package me.themgrf.avalon.entities.lights;

import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.util.vector.Vector3f;

public class PointLight extends Light {

    private Vector3f attenuation;

    public PointLight(Vector3f position, RGB colour, Vector3f attenuation) {
        super(position, colour);
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }
}
