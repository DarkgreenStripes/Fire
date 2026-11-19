package model.core;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import model.helpers.ColorTargetsDTO;
import model.helpers.IModel;

/** Coordinates the fire simulation, its palette, and the rendered image. */
public class MyModel implements IModel {
    private static final int FRAME_DELAY_MILLIS = 50;

    private Fire fire;
    private Palette palette = new Palette();
    private final Render render = new Render();
    private final Timer timer;

    public MyModel(Dimension dimension) {
        timer = new Timer(FRAME_DELAY_MILLIS, event -> updateFrame());
        timer.setCoalesce(true);
        setDimension(dimension);
    }

    @Override
    public void setDimension(Dimension dimension) {
        if (dimension == null || dimension.width <= 0 || dimension.height <= 0) {
            throw new IllegalArgumentException("Fire dimensions must be positive.");
        }
        fire = new Fire(dimension.width, dimension.height);
        refreshImage();
    }

    @Override
    public BufferedImage getImage() {
        return render.getImage();
    }

    @Override
    public void setPalette(ColorTargetsDTO colorTargets) {
        palette = new Palette(colorTargets);
        refreshImage();
        render.render();
    }

    @Override
    public void play() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    @Override
    public void stop() {
        timer.stop();
    }

    public Render getRender() {
        return render;
    }

    private void updateFrame() {
        fire.evolveTemperatures();
        refreshImage();
        render.render();
    }

    private void refreshImage() {
        render.getImage(palette, fire.getTemperatureMatrix());
    }
}
