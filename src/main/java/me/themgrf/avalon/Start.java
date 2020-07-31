package me.themgrf.avalon;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.entities.Player;
import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.RenderManager;
import me.themgrf.avalon.renderer.models.*;
import me.themgrf.avalon.renderer.shaders.StaticShader;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.utils.Location;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class Start {

    public static void main(String[] args) {
        Avalon avalon = new Avalon();
        avalon.init();

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        RawModel rawModel = ModelLoader.loadModel("test/stall", loader);

        ModelTexture texture = null;
        try {
            texture = new ModelTexture(loader.loadTexture("test/stall"));

            texture.setShineDamper(5);
            texture.setReflectivity(0.2f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TexturedModel texturedModel = new TexturedModel(rawModel, texture);

        Player player = new Player(new Camera(), "Geoff");

        Entity entity = new Entity(texturedModel, new Location(0, -2, -25), 1);
        Light light = new Light(new Vector3f(0, 5, 0), new Vector3f(1, 1, 1));

        RenderManager renderManager = new RenderManager();
        while (!Display.isCloseRequested()) {
            Location loc = entity.getLocation();
            loc.setRotation(new Rotation(0, loc.getRotation().getY() + 0.2f, 0));

            player.move();

            renderManager.processEntity(entity);
            renderManager.render(light, player.getCamera());

            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
