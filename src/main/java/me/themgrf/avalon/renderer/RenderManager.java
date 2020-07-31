package me.themgrf.avalon.renderer;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.renderer.models.Renderer;
import me.themgrf.avalon.renderer.models.TexturedModel;
import me.themgrf.avalon.renderer.shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RenderManager {

    private final StaticShader shader = new StaticShader();
    private final Renderer renderer = new Renderer(shader);

    private final HashMap<TexturedModel, List<Entity>> entities = new HashMap<>();

    public void render(Light sun, Camera camera) {
        renderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);

        renderer.render(entities);

        shader.stop();
        entities.clear();
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

    public void cleanUp() {
        shader.cleanUp();
    }

}
