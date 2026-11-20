package presenter.core;

import model.helpers.IModel;
import presenter.helpers.IPresenter;
import view.helpers.IView;

/** Connects the simulation model to the window. */
public class MyPresenter implements IPresenter {
    private final IModel model;
    private IView view;

    public MyPresenter(IModel model, IView view) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null.");
        }
        this.model = model;
        
        view.setRenderCanvas(model.getRender());
        view.showWindow();
    }

    @Override
    public void play() {
        runModelAction(model::play);
    }

    @Override
    public void stop() {
        runModelAction(model::stop);
    }

    @Override
    public void showError(String message) {
        if (view == null) {
            throw new IllegalStateException(message);
        }
        view.showError(message);
    }

    private void runModelAction(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            showError(exception.getMessage());
        }
    }
}
