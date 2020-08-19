package me.themgrf.avalon.renderer;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.renderer.models.TexturedModel;
import me.themgrf.avalon.renderer.renderers.EntityRenderer;
import me.themgrf.avalon.renderer.renderers.TerrainRenderer;
import me.themgrf.avalon.renderer.shaders.StaticShader;
import me.themgrf.avalon.renderer.shaders.TerrainShader;
import me.themgrf.avalon.terrain.LowPolyTerrain;
import me.themgrf.avalon.terrain.TexturedTerrain;
import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RenderManager {

    public static final float FOV = 70;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000;

    private static final RGB SKY_COLOUR = new RGB(127, 184, 184).makeGLCompatible();

    private final StaticShader shader = new StaticShader();
    private final EntityRenderer entityRenderer;

    private final TerrainRenderer terrainRenderer;
    private final TerrainShader terrainShader = new TerrainShader();

    private final HashMap<TexturedModel, List<Entity>> entities = new HashMap<>();
    private final List<TexturedTerrain> terrains = new ArrayList<>();

    private Matrix4f projectionMatrix;

    public RenderManager() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        createProjectionMatrix();

        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public void render(Light sun, Camera camera) {
        prepare();
        shader.start();
        shader.loadSkyColour(SKY_COLOUR);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);

        entityRenderer.render(entities);

        shader.stop();

        terrainShader.start();
        terrainShader.loadSkyColour(SKY_COLOUR);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();

        entities.clear();
        terrains.clear();
    }

    public void render(LowPolyTerrain terrain, Camera camera, Light light) {
        prepare();
        terrain.render(camera, light);
    }

    public void processEntity(Entity entity) {
        TexturedModel texturedModel = entity.getTexturedModel();
        List<Entity> batch = entities.get(texturedModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(texturedModel, newBatch);
        }
    }

    public void processTerrain(TexturedTerrain terrain) {
        terrains.add(terrain);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(SKY_COLOUR.getRed(), SKY_COLOUR.getGreen(), SKY_COLOUR.getBlue(), 1);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustrum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustrum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustrum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }

}
