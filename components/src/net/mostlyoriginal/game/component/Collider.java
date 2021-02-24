package net.mostlyoriginal.game.component;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;

/**
 * Colisionador
 * Los componentes son clases de datos puros con, opcionalmente, algunos metodos auxiliares.
 */
public class Collider extends Component {

	public long layer; // Donde estamos?
	public long mask; // Con que chocamos?

	// @todo Es esta la mejor solucion? Se puede hacer mejor!
	@EntityId public IntBag colliding = new IntBag(4);

	public void set(long layer, long mask) {
		this.layer = layer;
		this.mask = mask;
	}

	public void set(long layer) {
		this.layer = layer;
		this.mask = 0;
	}

	// Choco?
	public boolean isColliding() {
		// Si la bolsa no esta vacia
		return !colliding.isEmpty();
	}

	// Choca con...
	public boolean collidesWith(Collider other) {
		// Calcula si la capa de otro colisionador y la mascara de esta clase son distintos a 0
		return (other.layer & this.mask) != 0;
	}
}
