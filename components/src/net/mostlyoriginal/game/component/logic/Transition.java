package net.mostlyoriginal.game.component.logic;

import com.artemis.Component;
import com.badlogic.gdx.Screen;

/**
 * Ayudante de transicion entre pantallas.
 */
public class Transition extends Component {

	/**
	 * Campo generico Class con tipo comodin (?), para que se puedan crear objetos que implementen la interfaz Screen.
	 */
	public Class<? extends Screen> screen;

	public Transition() {
	}

	public Transition(Class<? extends Screen> screen) {
		this.screen = screen;
	}
}
