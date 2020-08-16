package me.themgrf.avalon.entities;

import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 50, 0);
    private Rotation rotation = new Rotation(40, 0, 0);

    private int lastloc;

    public void move() {
        int multiplier = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 4 : 1;

        float increment = 0.3f;
        float total = increment * multiplier;

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if (position.y <= 128) {
                position.y += total;

                /*if (rotation.getX() < 60) {
                    rotation.setX(rotation.getX() + 0.1f);
                }*/
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            if (position.y > 30) {
                position.y -= total;

                /*if (rotation.getX() > 10) {
                    rotation.setX(rotation.getX() - 0.1f);
                }*/
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            position.z -= total;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            position.z += total;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            position.x += total;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            position.x -= total;
        }

        // Scroll wheel control (ZOOM)
        int dWheel = Mouse.getDWheel();
        increment = 4f;
        total = increment * multiplier;

        if (dWheel < 0) { // zoom in
            if (position.y <= 128) {
                position.z += total;
                position.y += total;
            }
        } else if (dWheel > 0) { // zoom out
            if (position.y >= 30) {
                position.z -= total;
                position.y -= total;
            }
        }

        if (Mouse.getButtonCount() >= 1) {
            if (Mouse.isButtonDown(0)) {
                if (lastloc != Mouse.getX()) {
                    float mouseMove = 20 - (position.y / 10); // scale mouse movements to zoom level
                    position.setX(position.x - Mouse.getDX() / mouseMove);
                    position.setZ(position.z + Mouse.getDY() / mouseMove);

                    lastloc = Mouse.getX();
                }
            } else if (Mouse.isButtonDown(1)) {
                // TODO: Rotation?
            }
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
