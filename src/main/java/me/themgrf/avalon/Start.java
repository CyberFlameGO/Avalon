package me.themgrf.avalon;

import me.themgrf.avalon.api.World;
import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Entity;
import me.themgrf.avalon.entities.Player;
import me.themgrf.avalon.entities.lights.Light;
import me.themgrf.avalon.entities.lights.PointLight;
import me.themgrf.avalon.entities.lights.Sun;
import me.themgrf.avalon.renderer.DisplayManager;
import me.themgrf.avalon.renderer.RenderManager;
import me.themgrf.avalon.renderer.ResourceLocation;
import me.themgrf.avalon.renderer.guis.GUIRenderer;
import me.themgrf.avalon.renderer.models.Loader;
import me.themgrf.avalon.renderer.models.ModelLoader;
import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.models.TexturedModel;
import me.themgrf.avalon.renderer.textures.ModelTexture;
import me.themgrf.avalon.terrain.Terrain;
import me.themgrf.avalon.utils.Location;
import me.themgrf.avalon.utils.Rotation;
import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.util.Arrays;
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

        World world = new World("world");

        Player player = new Player(new Camera(), "Geoff");

        GUIRenderer guiRenderer = new GUIRenderer(loader);

        Entity entity = new Entity(texturedModel, new Location(0, 0, -50), 1);
        entity.setRotation(new Rotation(0, 180, 0));

        ResourceLocation heightMap = new ResourceLocation("textures/test/heightmap.png");
        Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("test/grass")), heightMap);
        Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("test/grass")), heightMap);

        List<Light> lights = Arrays.asList(
                world.getSun(),
                new PointLight(new Vector3f(0, 10, -50), new RGB(255, 153, 102), new Vector3f(1, 0.01f, 0.002f))
        );

        RenderManager renderManager = new RenderManager();
        while (!Display.isCloseRequested()) {
            //Location loc = entity.getLocation();
            //loc.setRotation(new Rotation(0, loc.getRotation().getY() + 0.2f, 0));

            player.move();

            renderManager.processTerrain(terrain);
            renderManager.processTerrain(terrain2);
            renderManager.processEntity(entity);

            for (Light light : lights) {
                if (light instanceof Sun) {
                    Vector3f pos = light.getPosition();
                    if (pos.x >= 500) {
                        RGB colour = light.getColour();
                        if (colour.getRed() > 50) {
                            light.setColour(new RGB(colour.getRed() - 1, colour.getGreen() - 1, colour.getBlue() - 1));
                        }
                        light.setPosition(new Vector3f(-600, pos.y + 1, 0));
                    } else if (pos.x <= -500) {
                        light.setColour(new RGB(255, 255, 255));
                        //light.setPosition(new Vector3f(500, pos.y + 1, 0));
                    }
                    if (pos.x > 0) {
                        light.setPosition(new Vector3f(pos.x + 0.1f, pos.y - 0.1f, 0));
                    } else {
                        light.setPosition(new Vector3f(pos.x + 0.1f, pos.y + 0.1f, 0));
                    }
                }
            }

            renderManager.render(lights, player.getCamera());

            guiRenderer.render();

            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderManager.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
