package me.themgrf.avalon.utils;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.renderer.EntityTransformation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

    /**
     * Clamp a value OpenGL style
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static float clamp(float value, float min, float max){
        return Math.max(Math.min(value, max), min);
    }

    /**
     * Calculate a normal of a triangle made up of 3 vertices
     *
     * @param vertex0
     * @param vertex1
     * @param vertex2
     * @return
     */
    public static Vector3f calculateNormal(Vector3f vertex0, Vector3f vertex1, Vector3f vertex2) {
        Vector3f tangentA = Vector3f.sub(vertex1, vertex0, null);
        Vector3f tangentB = Vector3f.sub(vertex2, vertex0, null);
        Vector3f normal = Vector3f.cross(tangentA, tangentB, null);
        normal.normalise();
        return normal;
    }

    /**
     * Return a 4x4 Matrix based on an entity transformation
     *
     * @param transformation The entity rotation to utilise
     * @return A 4x4 Matrix based on an entity transformation
     */
    public static Matrix4f createTransformationMatrix(EntityTransformation transformation) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        Matrix4f.translate(transformation.getTranslation(), matrix, matrix);

        Rotation rotation = transformation.getRotation();
        Matrix4f.rotate((float) Math.toRadians(rotation.getX()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.getY()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.getZ()), new Vector3f(0, 0, 1), matrix, matrix);

        float scale = transformation.getScale();
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().getX()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().getY()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().getZ()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);

        return viewMatrix;
    }

}
