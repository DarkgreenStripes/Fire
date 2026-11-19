package model.core;

import java.awt.Color;
import java.util.Map;
import model.helpers.ColorTargetsDTO;

/**
 * Builds a lookup table that maps each supported temperature to a color.
 *
 * <p>Colors between configured targets are linearly interpolated, including
 * their alpha channel.</p>
 */
public class Palette {
    private static final int MIN_TEMPERATURE = 0;
    private static final int MAX_TEMPERATURE = 255;
    private Color[] colors;

    // Palette construction
    public Palette() {
        createPalette();
    }

    public Palette(ColorTargetsDTO targets) {
        createPalette(targets);
    }

    public Color[] createPalette() {
        return createPalette(new ColorTargetsDTO());
    }

    public Color[] createPalette(ColorTargetsDTO colorTargets) {
        if (colorTargets == null) {
            throw new IllegalArgumentException("Color targets cannot be null.");
        }

        Map<Integer, Color> targets = colorTargets.getTargets();
        if (!targets.containsKey(MIN_TEMPERATURE)
                || !targets.containsKey(MAX_TEMPERATURE)) {
            throw new IllegalArgumentException("Color targets must include temperatures 0 and 255.");
        }

        Color[] generatedColors = new Color[MAX_TEMPERATURE + 1];
        int previousTemperature = -1;
        Color previousColor = null;
        for (int temperature = MIN_TEMPERATURE; temperature <= MAX_TEMPERATURE; temperature++) {
            if (targets.containsKey(temperature)) {
                Color currentColor = targets.get(temperature);
                if (previousTemperature >= 0) {
                    int temperatureRange = temperature - previousTemperature;
                    for (int i = previousTemperature; i <= temperature; i++) {
                        double progress = (double) (i - previousTemperature) / temperatureRange;
                        int red = interpolate(previousColor.getRed(), currentColor.getRed(), progress);
                        int green = interpolate(previousColor.getGreen(), currentColor.getGreen(), progress);
                        int blue = interpolate(previousColor.getBlue(), currentColor.getBlue(), progress);
                        int alpha = interpolate(previousColor.getAlpha(), currentColor.getAlpha(), progress);
                        generatedColors[i] = new Color(red, green, blue, alpha);
                    }
                }
                previousTemperature = temperature;
                previousColor = currentColor;
            }
        }

        colors = generatedColors;
        return colors.clone();
    }

    // Color lookup and access
    public Color getColor(int temperature) {
        if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException("Temperature must be between 0 and 255.");
        }
        return colors[temperature];
    }

    public Color[] getColors() {
        // Do not expose the mutable lookup table to callers.
        return colors.clone();
    }

    private int interpolate(int start, int end, double progress) {
        return (int) Math.round(start + progress * (end - start));
    }
}
