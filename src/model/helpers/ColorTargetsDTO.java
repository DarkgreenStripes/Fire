package model.helpers;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds palette key points: temperatures in the range 0-255 and their colors.
 *
 * <p>Custom targets are copied into a regular map, so later changes to the
 * input map do not change this object.</p>
 */
public class ColorTargetsDTO {
    private static final int MIN_TEMPERATURE = 0;
    private static final int MAX_TEMPERATURE = 255;

    private final Map<Integer, Color> targets;

    // Construction and access
    public ColorTargetsDTO() {
        this(createDefaultTargets());
    }

    public ColorTargetsDTO(Map<Integer, Color> targets) {
        if (targets == null || targets.isEmpty()) {
            throw new IllegalArgumentException("Color targets cannot be empty.");
        }

        Map<Integer, Color> copiedTargets = new HashMap<>(targets);
        for (Map.Entry<Integer, Color> target : copiedTargets.entrySet()) {
            validateTarget(target.getKey(), target.getValue());
        }
        this.targets = copiedTargets;
    }

    public Map<Integer, Color> getTargets() {
        return new HashMap<>(targets);
    }

    // Default palette and target validation
    private static Map<Integer, Color> createDefaultTargets() {
        Map<Integer, Color> defaultTargets = new HashMap<>();
        defaultTargets.put(MIN_TEMPERATURE, new Color(0, 0, 0, 0));
        defaultTargets.put(25, new Color(139, 69, 19));
        defaultTargets.put(50, Color.RED);
        defaultTargets.put(100, new Color(255, 165, 0));
        defaultTargets.put(200, Color.YELLOW);
        defaultTargets.put(MAX_TEMPERATURE, Color.WHITE);
        return defaultTargets;
    }

    private static void validateTarget(Integer temperature, Color color) {
        if (temperature == null
                || temperature < MIN_TEMPERATURE
                || temperature > MAX_TEMPERATURE
                || color == null) {
            throw new IllegalArgumentException(
                    "Targets require temperatures from 0 to 255 and non-null colors.");
        }
    }
}
