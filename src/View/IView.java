package View;

/** Notifications the presenter can send to the window. */
public interface IView {
    void showError(String message);

    void showFrame();
}
