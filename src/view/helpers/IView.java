package view.helpers;
import java.awt.Canvas; 

/** Notifications the presenter can send to the window. */
public interface IView {
    void showError(String message);

    void showFrame();

    void setRenderCanvas (Canvas renderCanvas);

    void showWindow();
}
