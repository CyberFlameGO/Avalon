package me.themgrf.avalon.renderer.renderers;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.renderer.EntityTransformation;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.shaders.ShaderProgram;
import me.themgrf.avalon.renderer.shaders.TerrainShader;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.terrain.LowPolyTerrain;
import me.themgrf.avalon.terrain.TexturedTerrain;
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

    private final boolean hasIndices;

    public TerrainRenderer(ShaderProgram shader, boolean hasIndices) {
        super(shader);
        this.hasIndices = hasIndices;
    }

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        super(shader);

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();

        this.hasIndices = false;
    }

    @Override
    @Deprecated
    public void prepare(Object object) {
        if (object instanceof TexturedTerrain) {
            TexturedTerrain terrain = (TexturedTerrain) object;
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

    /**
     * Starts the shader program and loads up any necessary uniform variables.
     *
     * @param terrain
     *            - The terrain to be rendered.
     * @param camera
     *            - The camera being used to render the scene.
     * @param light
     *            - The light in the scene.
     */
    private void prepare(LowPolyTerrain terrain, Camera camera, Light light) {
        terrain.getVao().bind();
        getShader().start();
        TerrainShader terrainShader = (TerrainShader) getShader();
        terrainShader.lightBias.loadVector2(light.getLightBias());
        terrainShader.lightDirection.loadVector3(light.getDirection());
        terrainShader.lightColour.loadVector3(light.getColour().asVector());
        terrainShader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
    }

    @Override
    public void loadModelMatrix(Object object) {
        if (object instanceof TexturedTerrain) {
            TexturedTerrain terrain = (TexturedTerrain) object;
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

    @Deprecated
    public void render(List<TexturedTerrain> terrains) {
        for (TexturedTerrain terrain : terrains) {
            prepare(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getRawModel().getVortexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbind();
        }
    }

    /**
     * Renders a terrain to the screen. If the terrain has an index buffer the
     * glDrawElements is used. Otherwise glDrawArrays is used.
     *
     * @param terrain
     *            - The terrain to be rendered.
     * @param camera
     *            - The camera being used for rendering the terrain.
     * @param light
     *            - The light being used to iluminate the terrain.
     */
    public void render(LowPolyTerrain terrain, Camera camera, Light light) {
        prepare(terrain, camera, light);
        if (hasIndices) {
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        } else {
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, terrain.getVertexCount());
        }
        finish(terrain);
    }

    /**
     * End the rendering process by unbinding the VAO and stopping the shader
     * program.
     *
     * @param terrain
     */
    private void finish(LowPolyTerrain terrain) {
        terrain.getVao().unbind();
        getShader().stop();
    }
}
