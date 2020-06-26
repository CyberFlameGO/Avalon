package me.themgrf.avalon;

import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.models.Loader;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.models.Renderer;
import me.themgrf.avalon.renderer.shaders.StaticShader;
import org.lwjgl.opengl.Display;

public class Start {

    public static void main(String[] args) {
        Avalon avalon = new Avalon();
        avalon.init();

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        RawModel rawModel = loader.loadToVAO(vertices, indices);

        while (!Display.isCloseRequested()) {
            renderer.prepare();
            shader.start();
            renderer.render(rawModel);
            shader.stop();

            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
