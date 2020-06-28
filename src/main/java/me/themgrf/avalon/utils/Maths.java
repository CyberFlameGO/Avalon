package me.themgrf.avalon.utils;

import me.themgrf.avalon.renderer.EntityTransformation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

    public static Matrix4f createTransformationMatrix(EntityTransformation transformation) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(transformation.getTranslation(), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(transformation.getRx()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(transformation.getRy()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(transformation.getRz()), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(transformation.getScale(), transformation.getScale(), transformation.getScale()), matrix, matrix);
        return matrix;
    }

}
