package net.mostlyoriginal.game.component;

import com.artemis.Component;

// Oxigeno
public class Oxygen extends Component {

	public float percentage;

	public void increase() {
		// Aumenta el porcentaje en un 75
		percentage += 75;
		if (percentage > 100f) percentage = 150f; // ?
	}
}
