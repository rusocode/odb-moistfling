package net.mostlyoriginal.game.component.future;

import com.artemis.Component;

/**
 * Entidad futura.
 */
public class FutureEntity extends Component {
	public int type; // Agregar a EntityType
	public String subType;
	public int count = 1;

	public void set(int entityType) {
		this.type = entityType;
	}
}
