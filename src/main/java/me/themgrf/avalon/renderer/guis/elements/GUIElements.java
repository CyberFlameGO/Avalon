package me.themgrf.avalon.renderer.guis.elements;

import me.themgrf.avalon.Avalon;
import me.themgrf.avalon.renderer.guis.GUITexture;
import me.themgrf.avalon.renderer.models.Loader;
import org.lwjgl.util.vector.Vector2f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GUIElements {

    private static final Loader LOADER = Avalon.getInstance().getLoader();
    private static final HashMap<String, GUIElement> GUIS = new HashMap<>();

    public static final GUIElement LOGO = element(
            new GUIElement(
                    "logo",
                    new GUITexture(
                            LOADER.loadTexture("test/banner"),
                            new Vector2f(-0.85f, 0.9f),
                            new Vector2f(0.15f, 0.1f)
                    )
            )
    );

    public static HashMap<String, GUIElement> getElements() {
        return GUIS;
    }

    private static GUIElement element(GUIElement element) {
        String id = element.getId();
        if (GUIS.containsKey(id)) return element;
        GUIS.put(id, element);

        return element;
    }

}
