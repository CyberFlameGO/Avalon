package me.themgrf.avalon.entities;

import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private Rotation rotation = new Rotation(0, 0, 0);

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.y += 0.02f;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.y -= 0.02f;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02f;
        }

        // TODO: Figure out how on earth mouse works
        /*int dWheel = Mouse.getDWheel();
        if (dWheel != 0) {
            // up
            position.z -= 0.02f;
        } else {
            // down
            position.z += 0.02f;
        }*/
    }

    public Vector3f getPosition() {
        return position;
    }

    public Rotation getRotation() {
        return rotation;
    }
}
