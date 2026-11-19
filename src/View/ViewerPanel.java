package View;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/** Hosts the model's canvas and requests repaints when a new frame is ready. */
public class ViewerPanel extends JPanel {
    private final Canvas canvas;

    // Panel setup
    public ViewerPanel(int width, int height) {
        this(new Canvas(), width, height);
    }

    /**
     * Uses the supplied canvas directly so a model-owned renderer can be shown
     * without copying its image into a second drawing component.
     */
    public ViewerPanel(Canvas canvas, int width, int height) {
        if (canvas == null || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Canvas and positive dimensions are required.");
        }

        this.canvas = canvas;
        setLayout(new BorderLayout());
        setBackground(new Color(242, 243, 245));
        canvas.setBackground(Color.WHITE);
        canvas.setPreferredSize(new Dimension(width, height));
        add(canvas, BorderLayout.CENTER);
    }

    // Frame display
    public void showFrame() {
        canvas.repaint();
    }
}
