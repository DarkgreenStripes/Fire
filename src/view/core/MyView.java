package view.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import view.helpers.IView;

/** Window that displays the rendered fire. */
public class MyView extends JFrame implements IView {
    private static final int VIEW_WIDTH = 600;
    private static final int VIEW_HEIGHT = 400;
    private Canvas renderCanvas;

    public MyView() {
        setTitle("Simulación de fuego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
        setLocationByPlatform(true);
    }

    @Override 
    public void showWindow() {
        if (renderCanvas == null)
            throw new IllegalStateException("Canvas no asignado. Llama setRenderCanvas antes.");
        setVisible(true);
    }

    @Override 
    public void setRenderCanvas (Canvas renderCanvas) {
        if (renderCanvas == null) {
            throw new IllegalArgumentException("Render canvas not found.");
        }
        this.renderCanvas = renderCanvas;
        add(new ViewerPanel(renderCanvas, VIEW_WIDTH, VIEW_HEIGHT), BorderLayout.CENTER);
        pack();
    }

    @Override
    public void showError(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                this, message, "Error", JOptionPane.ERROR_MESSAGE));
    }

    @Override
    public void showFrame() {
        repaint();
    }
}
