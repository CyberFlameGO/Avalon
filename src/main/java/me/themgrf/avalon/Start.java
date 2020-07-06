package me.themgrf.avalon;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.models.Loader;
import me.themgrf.avalon.renderer.models.ModelLoader;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.models.Renderer;
import me.themgrf.avalon.renderer.shaders.StaticShader;
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

        Camera camera = new Camera();

        Entity entity = new Entity(rawModel, new Vector3f(0, -2, -25), new Rotation(0, 0, 0), 1);

        while (!Display.isCloseRequested()) {
            //entity.increasePosition(0, 0, -0.002f);
            entity.increaseRotation(new Rotation(0, 1, 0));
            camera.move();
            //entity.increaseRotation(new Rotation(1, 0, 0));

            renderer.prepare();
            shader.start();
            //renderer.render(rawModel);
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
