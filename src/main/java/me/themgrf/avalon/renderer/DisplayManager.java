package me.themgrf.avalon.renderer;

import me.themgrf.avalon.utils.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import java.awt.*;

public class DisplayManager {

    public static final String NAME = "Avalon";
    public static final Resolution DEFAULT_RES = new Resolution(1280, 720);
    public static final Resolution FULLSCREEN_RES = new Resolution(1920, 1080);
    private static final int FPS_CAP = 144;

    private static double previousTime = 0;
    private static int frames = 0;

    public static void createDisplay(Resolution resolution) {
        AvalonDisplay.createDisplay(resolution.getWidth(), resolution.getHeight(), NAME, false);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();

        printFPS();

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_F11:
                        GL11.glViewport(0, 0, DEFAULT_RES.getWidth(), DEFAULT_RES.getHeight());
                        AvalonDisplay.setFullscreen(!Display.isFullscreen());
                        break;
                    case Keyboard.KEY_SPACE:
                        Logger.info("SPACE");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    protected static void printFPS() {
        frames++;

        double currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) - (previousTime / 1000) > 1) {
            Logger.info("FPS: " + frames);

            frames = 0;
            previousTime = currentTime;
        }
    }

}
