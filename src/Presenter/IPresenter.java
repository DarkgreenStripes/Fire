package Presenter;

/** Actions the view can request from the presenter. */
public interface IPresenter {
    void play();

    void stop();

    void getFrame();

    void showError(String message);
}
