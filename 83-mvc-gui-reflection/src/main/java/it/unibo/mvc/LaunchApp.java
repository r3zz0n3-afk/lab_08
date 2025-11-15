package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/* useless imports
 import it.unibo.mvc.api.DrawNumber;
 import it.unibo.mvc.view.DrawNumberStandardOutputView;
 import it.unibo.mvc.view.DrawNumberSwingView;
 */

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        /* 
         Implementation whitout Reflections
         app.addView(new DrawNumberStandardOutputView());
         app.addView(new DrawNumberSwingView());
         app.addView(new DrawNumberSwingView());
         */

        for (int i = 0; i < 3; i++) {
            // CHECKSTYLE:OFF
            for (final String viewType : List.of("it.unibo.mvc.view.DrawNumberStandardOutputView", "it.unibo.mvc.view.DrawNumberSwingView")) {
                try {
                   final Class<?> viewClass = Class.forName(viewType);
                   final var costrutor = viewClass.getConstructor().newInstance();
                   app.addView((DrawNumberView) costrutor);
                } catch (final ClassNotFoundException e) { // Chatch the all possible Exeptions
                    e.printStackTrace(); //NOPMD
                } catch (final NoSuchMethodException e) {
                    e.printStackTrace(); //NOPMD
                } catch (final InvocationTargetException e) {
                    e.printStackTrace(); //NOPMD
                } catch (final InstantiationException e) {
                    e.printStackTrace(); //NOPMD
                } catch (final IllegalAccessException e) {
                    e.printStackTrace(); //NOPMD
                } catch (final IllegalArgumentException e) {
                    e.printStackTrace(); //NOPMD
                }
            }
        }
    }
}
