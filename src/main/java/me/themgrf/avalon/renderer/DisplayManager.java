package me.themgrf.avalon.renderer;

import me.themgrf.avalon.utils.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final String NAME = "Avalon";
    private static final Resolution DEFAULT_RES = new Resolution(1280, 720);
    private static final Resolution FULLSCREEN_RES = new Resolution(1920, 1080);
    private static final int FPS_CAP = 144;

    public static void createDisplay() {
        AvalonDisplay.createDisplay(DEFAULT_RES.getWidth(), DEFAULT_RES.getHeight(), NAME, false);
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
                        AvalonDisplay.setFullscreen(!Display.isFullscreen(), DEFAULT_RES.getWidth(), DEFAULT_RES.getHeight());
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
