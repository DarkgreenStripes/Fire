package model.helpers;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/** Operations the presenter needs from the simulation model. */
public interface IModel {
    void setDimension(Dimension dimension);

    BufferedImage getImage();

    void setPalette(ColorTargetsDTO colorTargets);

    void play();

    void stop();
}
