package net.mostlyoriginal.game.component.future;

import com.artemis.Component;

/**
 * Los componentes son clases de datos puros con, opcionalmente, algunos metodos auxiliares.
 */
public class ParticleEffect extends Component {
	public String type;

	public void set(String type) {
		this.type = type;
	}
}
