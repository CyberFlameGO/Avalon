package me.themgrf.avalon.utils;

/**
 * Utility logging class for printing to the console
 */
public class Logger {

    public static void info(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void success(String msg) {
        System.out.println("[SUCCESS] " + msg);
    }

    public static void warning(String msg) {
        System.out.println("[WARNING] " + msg);
    }

    public static void error(String msg) {
        System.err.println(msg);
    }

}
