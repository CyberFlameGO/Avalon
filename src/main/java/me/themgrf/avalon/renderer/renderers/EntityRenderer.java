package me.themgrf.avalon.renderer.renderers;

import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.renderer.EntityTransformation;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.models.TexturedModel;
import me.themgrf.avalon.renderer.shaders.StaticShader;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.utils.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.HashMap;
import java.util.List;

public class EntityRenderer extends Renderer {

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        super(shader);

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    @Override
    public void prepare(Object object) {
        if (object instanceof TexturedModel) {
            TexturedModel texturedModel = (TexturedModel) object;
            RawModel rawModel = texturedModel.getRawModel();
            GL30.glBindVertexArray(rawModel.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            ModelTexture modelTexture = texturedModel.getModelTexture();
            getShader().loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getTextureID());
        } else {
            throw new InvalidRenderPreparationException("Entity preparation object must be instanceof TexturedModel");
        }
    }

    @Override
    public void loadModelMatrix(Object object) {
        if (object instanceof Entity) {
            Entity entity = (Entity) object;
            Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                    new EntityTransformation(entity.getLocation().getPosition(), entity.getLocation().getRotation(), entity.getScale())
            );
            getShader().loadTransformationMatrix(transformationMatrix);
        } else {
            throw new InvalidModelException("Entity models must be instanceof Entity");
        }
    }

    public void render(HashMap<TexturedModel, List<Entity>> entities) {
        for (TexturedModel texturedModel : entities.keySet()) {
            prepare(texturedModel);
            List<Entity> batch = entities.get(texturedModel);
            for (Entity entity : batch) {
                loadModelMatrix(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, texturedModel.getRawModel().getVortexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbind();
        }
    }
}
