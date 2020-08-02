package me.themgrf.avalon.renderer.renderers;

public class InvalidRenderPreparationException extends RuntimeException {

    public InvalidRenderPreparationException(String message) {
        super("Invalid preparation in render: " + message);
    }
}
