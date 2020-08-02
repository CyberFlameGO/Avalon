package me.themgrf.avalon.renderer.renderers;

import me.themgrf.avalon.renderer.EntityTransformation;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.models.TexturedModel;
import me.themgrf.avalon.renderer.shaders.TerrainShader;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.terrain.Terrain;
import me.themgrf.avalon.utils.Maths;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainRenderer extends Renderer {

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        super(shader);

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    @Override
    public void prepare(Object object) {
        if (object instanceof Terrain) {
            Terrain terrain = (Terrain) object;
            RawModel rawModel = terrain.getRawModel();
            GL30.glBindVertexArray(rawModel.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);
            ModelTexture texture = terrain.getTexture();
            getShader().loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        } else {
            throw new InvalidRenderPreparationException("Terrain preparation object must be instanceof Terrain");
        }
    }

    @Override
    public void loadModelMatrix(Object object) {
        if (object instanceof Terrain) {
            Terrain terrain = (Terrain) object;
            Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                    new EntityTransformation(
                            new Vector3f(terrain.getX(), 0, terrain.getY()), new Rotation(0, 0, 0), 1
                    )
            );
            getShader().loadTransformationMatrix(transformationMatrix);
        } else {
            throw new InvalidModelException("Terrain models must be instanceof Terrain");
        }
    }

    public void render(List<Terrain> terrains) {
        for (Terrain terrain : terrains) {
            prepare(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getRawModel().getVortexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbind();
        }
    }
}
