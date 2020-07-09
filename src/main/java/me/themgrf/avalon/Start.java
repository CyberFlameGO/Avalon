package me.themgrf.avalon;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.models.*;
import me.themgrf.avalon.renderer.shaders.StaticShader;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Start {

    public static void main(String[] args) {
        Avalon avalon = new Avalon();
        avalon.init();

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        //RawModel rawModel = loader.loadToVAO(vertices, textureCoords, indices); // original cube
        //RawModel cube = ModelLoader.cube(loader);
        RawModel rawModel = ModelLoader.loadModel("test/stall", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("test/stall"));
        texture.setShineDamper(5);
        texture.setReflectivity(0.2f);

        TexturedModel texturedModel = new TexturedModel(rawModel, texture);

        Camera camera = new Camera(); // Player

        Entity entity = new Entity(texturedModel, new Vector3f(0, -2, -25), new Rotation(0, 180, 0), 1);
        Light light = new Light(new Vector3f(0, 5, 0), new Vector3f(1, 1, 1));

        while (!Display.isCloseRequested()) {
            //entity.increasePosition(0, 0, -0.002f);
            entity.increaseRotation(new Rotation(0, 0.2f, 0));
            camera.move();
            light.setPosition(camera.getPosition());

            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();

            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
