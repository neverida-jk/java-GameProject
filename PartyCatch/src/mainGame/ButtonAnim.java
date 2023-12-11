/*
 * 	Class that handles the animation of buttons.
 */

package mainGame;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class ButtonAnim extends ButtonSkin {

	// Constructor for the ButtonAnim class
    public ButtonAnim(Button control) {
		super(control);

		// Create fade-in transition for the button
        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(control);
        fadeIn.setToValue(1);
        // Play fade-in transition on mouse enter
        control.setOnMouseEntered(e -> fadeIn.playFromStart());

        // Create fade-out transition for the button
        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.5);
        // Play fade-out transition on mouse exit
        control.setOnMouseExited(e -> fadeOut.playFromStart());

        // Set initial opacity for the button
        control.setOpacity(.5);
    }

}
