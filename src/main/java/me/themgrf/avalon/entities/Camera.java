package me.themgrf.avalon.entities;

import me.themgrf.avalon.utils.Logger;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private final Vector3f position = new Vector3f(0, 0, 0);
    private final Rotation rotation = new Rotation(0, 0, 0);

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.y += 0.02f;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.y -= 0.02f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02f;
        }

        int dWheel = Mouse.getDWheel();
        int multiplier = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 4 : 1;
        float increment = 0.05f;
        float total = increment * multiplier;

        if (dWheel < 0) { // zoom in
            position.z += total;
        } else if (dWheel > 0) { // zoom out
            position.z -= total;
        }

    }

    public Vector3f getPosition() {
        return position;
    }

    public Rotation getRotation() {
        return rotation;
    }
}
