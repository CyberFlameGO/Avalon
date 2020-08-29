package me.themgrf.avalon.renderer.guis;

import me.themgrf.avalon.renderer.guis.elements.GUIElement;
import me.themgrf.avalon.renderer.guis.elements.GUIElements;
import me.themgrf.avalon.renderer.models.Loader;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.utils.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class GUIRenderer {

    private static final List<GUITexture> GUIS = new ArrayList<>();

    private final RawModel rawModel;
    private GUIShader guiShader;

    public GUIRenderer(Loader loader) {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        rawModel = loader.loadToVAO(positions);
        guiShader = new GUIShader();
    }

    public void render() {
        guiShader.start();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for (GUIElement guiElement : GUIElements.getElements().values()) {
            GUITexture texture = guiElement.getTexture();

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
            Matrix4f matrix = Maths.createTransformationMatrix(texture.getPosition(), texture.getScale());
            guiShader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, rawModel.getVortexCount());
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        guiShader.stop();
    }

    public void cleanUp() {
        guiShader.cleanUp();
    }

}
