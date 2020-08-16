package me.themgrf.avalon.entities;

import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 3, 0);
    private Rotation rotation = new Rotation(0, 0, 0);

    public void move() {
        int multiplier = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 4 : 1;

        float increment = 0.02f;
        float total = increment * multiplier;

        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            if (position.y <= 256) {
                position.y += total;
                if (rotation.getX() < 60) {
                    rotation.setX(rotation.getX() + 0.05f);
                }
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            if (position.y > 2) {
                position.y -= total;
                if (rotation.getX() > 10) {
                    rotation.setX(rotation.getX() - 0.05f);
                }
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            position.x += total;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            position.x -= total;
        }

        int dWheel = Mouse.getDWheel();
        increment = 0.35f;
        total = increment * multiplier;

        if (dWheel < 0) { // zoom in
            position.z += total;
        } else if (dWheel > 0) { // zoom out
            position.z -= total;
        }

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
