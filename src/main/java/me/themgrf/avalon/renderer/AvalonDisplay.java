package me.themgrf.avalon.renderer;

import me.themgrf.avalon.Avalon;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class AvalonDisplay {

    private static ContextAttribs attribs;

    public static void createDisplay(int width, int height, String title, boolean fullscreen) {
        Display.destroy();

        if (attribs != null) {
            attribs = new ContextAttribs(3,2);
            attribs.withForwardCompatible(true);
            attribs.withProfileCore(true);
        }

        try {
            if (fullscreen) {
                Display.setFullscreen(false);
                Display.setDisplayMode(new DisplayMode(width, height));
                Display.setResizable(true);
            } else {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setFullscreen(true);
            }
            Display.setTitle(title);

            TexturePack pack = Avalon.getInstance().getTexturePack();
            InputStream icon16 = pack.getResourceStream(new ResourceLocation("icons/logo_16x16.png"));
            InputStream icon32 = pack.getResourceStream(new ResourceLocation("icons/logo_32x32.png"));
            if (icon16 != null && icon32 != null) {
                Display.setIcon(new ByteBuffer[]{
                        pack.readImageToBuffer(icon16),
                        pack.readImageToBuffer(icon32)
                });
            }

            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException | IOException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, width, height);
    }

}
