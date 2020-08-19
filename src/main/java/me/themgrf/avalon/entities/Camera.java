package me.themgrf.avalon.entities;

import me.themgrf.avalon.renderer.RenderManager;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private static final Matrix4f VIEW_MATRIX = new Matrix4f();

    private Vector3f position = new Vector3f(0, 50, 0);
    private Rotation rotation = new Rotation(40, 0, 0);

    private final Matrix4f projectionMatrix;
    private int lastloc;

    public Camera() {
        this.projectionMatrix = createProjectionMatrix();
    }

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

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getProjectionViewMatrix() {
        return Matrix4f.mul(projectionMatrix, VIEW_MATRIX, null);
    }

    private static Matrix4f createProjectionMatrix() {
        Matrix4f projectionMatrix = new Matrix4f();
        float far = RenderManager.FAR_PLANE;
        float near = RenderManager.NEAR_PLANE;;

        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(RenderManager.FOV / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far - near;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far + near) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near * far) / frustum_length);
        projectionMatrix.m33 = 0;
        return projectionMatrix;
    }
}
