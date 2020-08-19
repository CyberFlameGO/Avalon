package me.themgrf.avalon;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.entities.Player;
import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.RenderManager;
import me.themgrf.avalon.renderer.models.*;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.terrain.LowPolyTerrain;
import me.themgrf.avalon.terrain.TexturedTerrain;
import me.themgrf.avalon.terrain.generation.ColourGenerator;
import me.themgrf.avalon.terrain.generation.LowPolyTerrainGenerator;
import me.themgrf.avalon.terrain.generation.PerlinNoiseGenerator;
import me.themgrf.avalon.utils.Location;
import me.themgrf.avalon.utils.Rotation;
import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;

public class Start {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("C:/Users/Thomas/Documents/IntelliJ Coding/Avalon/libs/").getAbsolutePath());

        Avalon avalon = new Avalon();
        avalon.init();

        DisplayManager.createDisplay(DisplayManager.DEFAULT_RES);

        Loader loader = new Loader();
        RawModel rawModel = ModelLoader.loadModel("test/stall", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("test/stall"));

        texture.setShineDamper(5);
        texture.setReflectivity(0.2f);

        TexturedModel texturedModel = new TexturedModel(rawModel, texture);

        Player player = new Player(new Camera(), "Player");

        Entity entity = new Entity(texturedModel, new Location(0, 0, -50), 1);
        entity.setRotation(new Rotation(0, 180, 0));
        Light light = new Light(new Vector3f(50, 100, 50), new Vector3f(1, 1, 1), new RGB(0, 0, 0), new Vector2f(0, 0));

        //TexturedTerrain terrain = new TexturedTerrain(-0.5f, -0.5f, loader, new ModelTexture(loader.loadTexture("test/grass")), "heightmap");

        PerlinNoiseGenerator noise = new PerlinNoiseGenerator(3, 10, 0.35f);
        ColourGenerator colourGen = new ColourGenerator(
                new RGB[] { new RGB(201, 178, 99),
                        new RGB(135, 184, 82), new RGB(80, 171, 93), new RGB(120, 120, 120),
                        new RGB(200, 200, 210) },
                0.45f
        );
        LowPolyTerrain terrain = new LowPolyTerrainGenerator(noise, colourGen).generateTerrain(1600);

        RenderManager renderManager = avalon.getRenderManager();
        while (!Display.isCloseRequested()) {
            //Location loc = entity.getLocation();
            //loc.setRotation(new Rotation(0, loc.getRotation().getY() + 0.2f, 0));

            player.move();

            //renderManager.processTerrain(terrain);
            renderManager.processEntity(entity);
            //renderManager.render(light, player.getCamera());
            renderManager.render(terrain, player.getCamera(), light);

            DisplayManager.updateDisplay();
        }

        terrain.delete();
        renderManager.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
