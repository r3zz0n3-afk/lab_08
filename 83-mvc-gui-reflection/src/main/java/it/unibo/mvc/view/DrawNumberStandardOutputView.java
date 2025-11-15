package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * View std output implementation, and is only output.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    @Override
    public void setController(final DrawNumberController observer) {
        throw new UnsupportedOperationException("This view is out put only");
    }

    @Override
    public void start() {
    }

    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); //NOPMD
    }
}
