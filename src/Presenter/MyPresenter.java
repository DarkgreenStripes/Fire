package Presenter;

import Model.IModel;
import View.IView;

/** Connects the simulation model to the window. */
public class MyPresenter implements IPresenter {
    private final IModel model;
    private IView view;

    public MyPresenter(IModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null.");
        }
        this.model = model;
    }

    public void setView(IView view) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null.");
        }
        this.view = view;
        getFrame();
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
    public void getFrame() {
        if (view != null) {
            view.showFrame();
        }
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
