package me.themgrf.avalon.renderer;

import me.themgrf.avalon.utils.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class TexturePack {

    private final HashMap<String, File> assets;

    public TexturePack(HashMap<String, File> assets) {
        this.assets = assets;
    }

    public InputStream getResourceStream(ResourceLocation resourceLocation) {
        String path = "/assets/" + resourceLocation.getDomain() + "/" + resourceLocation.getPath();
        Logger.info("Fetching " + path);
        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            Logger.error("Failed to load texture: " + path);
        }
        return inputStream;
    }

    public ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint) {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

    public boolean resourceExists(ResourceLocation resourceLocation) {
        return getResourceStream(resourceLocation) != null || assets.containsKey(resourceLocation.toString());
    }

}
