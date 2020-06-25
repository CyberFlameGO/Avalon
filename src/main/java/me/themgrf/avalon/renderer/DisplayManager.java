package me.themgrf.avalon.renderer;

import me.themgrf.avalon.utils.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final String NAME = "Avalon";
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 144;

    public static void createDisplay() {
        AvalonDisplay.createDisplay(WIDTH, HEIGHT, NAME, true);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();

        int x = Mouse.getX();
        int y = Mouse.getY();

        //Logger.info(x + " " + y);

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_F11:
                        AvalonDisplay.createDisplay(WIDTH, HEIGHT, NAME, Display.isFullscreen());
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

}
