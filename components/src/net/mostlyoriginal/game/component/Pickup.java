package net.mostlyoriginal.game.component;

import com.artemis.Component;

// Recoger
public class Pickup extends Component {
	public Type type;

	// Objetos para recoger:
	public enum Type {
		OXYGEN, EXIT, CLICKABLE, TUTORIAL, BLINKER
	}
}
