package model.core;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** Converts a temperature grid to an image and paints it on a Swing canvas. */
public class Render extends Canvas {
    private BufferedImage image;

    // Image generation
    /** Rebuilds the image using the palette and temperature grid for the current frame. */
    public synchronized BufferedImage getImage(Palette palette, int[][] temperatures) {
        if (palette == null || temperatures == null || temperatures.length == 0
                || temperatures[0] == null || temperatures[0].length == 0) {
            throw new IllegalArgumentException("A palette and non-empty temperatures are required.");
        }

        int width = temperatures[0].length;
        BufferedImage renderedImage = new BufferedImage(
                width, temperatures.length, BufferedImage.TYPE_INT_ARGB);
        for (int row = 0; row < temperatures.length; row++) {
            if (temperatures[row] == null || temperatures[row].length != width) {
                throw new IllegalArgumentException("Temperature rows must have equal lengths.");
            }
            for (int col = 0; col < width; col++) {
                renderedImage.setRGB(col, row, palette.getColor(temperatures[row][col]).getRGB());
            }
        }

        image = renderedImage;
        return image;
    }

    // Image access and canvas painting
    public synchronized BufferedImage getImage() {
        return image;
    }

    public void render() {
        repaint();
    }

    @Override
    public synchronized void paint(Graphics graphics) {
        if (image != null) {
            // Scale the simulation image to the canvas size.
            graphics.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public synchronized java.awt.Dimension getPreferredSize() {
        if (image == null) {
            return super.getPreferredSize();
        }
        return new java.awt.Dimension(image.getWidth(), image.getHeight());
    }
}
