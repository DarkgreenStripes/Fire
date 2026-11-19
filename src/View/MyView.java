package View;

import Model.MyModel;
import Presenter.MyPresenter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/** Window that displays the rendered fire. */
public class MyView extends JFrame implements IView {
    private static final int VIEW_WIDTH = 600;
    private static final int VIEW_HEIGHT = 400;

    public MyView(MyModel model, MyPresenter presenter) {
        if (model == null || presenter == null) {
            throw new IllegalArgumentException("Model and presenter cannot be null.");
        }

        setTitle("Simulación de fuego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new ViewerPanel(model.getRender(), VIEW_WIDTH, VIEW_HEIGHT), BorderLayout.CENTER);
        setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
        pack();
        setLocationByPlatform(true);
        presenter.setView(this);
    }

    public void showWindow() {
        if (SwingUtilities.isEventDispatchThread()) {
            setVisible(true);
        } else {
            SwingUtilities.invokeLater(() -> setVisible(true));
        }
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
