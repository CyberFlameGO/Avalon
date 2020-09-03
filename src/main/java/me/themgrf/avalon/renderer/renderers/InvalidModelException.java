package me.themgrf.avalon.renderer.renderers;

/**
 * An exception that provides information on models loaded in renderers
 */
public class InvalidModelException extends RuntimeException {

    /**
     * Constructor for creating an InvalidModelException
     *
     * @param message The reason given for the error
     */
    public InvalidModelException(String message) {
        super("Invalid object specified: " + message);
    }

}
