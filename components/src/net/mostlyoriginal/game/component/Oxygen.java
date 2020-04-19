package net.mostlyoriginal.game.component;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class Oxygen extends Component {
    public float percentage;
    public void increase() {
        percentage += 75;
        if ( percentage > 100f) percentage=150f;
    }
}