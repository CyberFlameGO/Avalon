package me.themgrf.avalon;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.entities.Player;
import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.RenderManager;
import me.themgrf.avalon.renderer.ResourceLocation;
import me.themgrf.avalon.renderer.guis.GUIRenderer;
import me.themgrf.avalon.renderer.guis.GUITexture;
import me.themgrf.avalon.renderer.models.*;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.terrain.Terrain;
import me.themgrf.avalon.utils.Location;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("C:/Users/Thomas/Documents/IntelliJ Coding/Avalon/libs/").getAbsolutePath());

        Avalon avalon = new Avalon();

        DisplayManager.createDisplay(DisplayManager.DEFAULT_RES);

        Loader loader = avalon.getLoader();
        RawModel rawModel = ModelLoader.loadModel("test/stall", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("test/stall"));

        texture.setShineDamper(5);
        texture.setReflectivity(0.2f);

        TexturedModel texturedModel = new TexturedModel(rawModel, texture);

        Player player = new Player(new Camera(), "Geoff");

        GUIRenderer guiRenderer = new GUIRenderer(loader);

        Entity entity = new Entity(texturedModel, new Location(0, 0, -50), 1);
        entity.setRotation(new Rotation(0, 180, 0));
        Light light = new Light(new Vector3f(0, 1000, 0), new Vector3f(1, 1, 1));

        ResourceLocation heightMap = new ResourceLocation("textures/test/heightmap.png");
        Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("test/grass")), heightMap);
        Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("test/grass")), heightMap);

        RenderManager renderManager = new RenderManager();
        while (!Display.isCloseRequested()) {
            //Location loc = entity.getLocation();
            //loc.setRotation(new Rotation(0, loc.getRotation().getY() + 0.2f, 0));

            player.move();

            renderManager.processTerrain(terrain);
            renderManager.processTerrain(terrain2);
            renderManager.processEntity(entity);
            renderManager.render(light, player.getCamera());

            guiRenderer.render();

            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderManager.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
