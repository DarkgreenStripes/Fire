import Model.MyModel;
import Presenter.MyPresenter;
import View.MyView;

import java.awt.Dimension;
import javax.swing.SwingUtilities;

/** Starts the fire simulation window. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyModel model = new MyModel(new Dimension(120, 80));
            MyPresenter presenter = new MyPresenter(model);
            MyView view = new MyView(model, presenter);
            view.showWindow();
            presenter.play();
        });
    }
}
