package net.mostlyoriginal.game.component;

import com.artemis.Component;

// Tamaño de animacion
public class AnimSize extends Component {
	public float width;
	public float height;

	public void set(float width, float height) {
		this.width = width;
		this.height = height;
	}
}
