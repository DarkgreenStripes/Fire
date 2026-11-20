package main;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import model.core.MyModel;
import presenter.core.MyPresenter;
import view.core.MyView;

/** Starts the fire simulation window. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyPresenter presenter = new MyPresenter(new MyModel(new Dimension(120, 80)), new MyView());
            presenter.play();
        });
    }
}
