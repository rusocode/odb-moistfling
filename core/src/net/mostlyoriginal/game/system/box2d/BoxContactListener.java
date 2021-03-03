package net.mostlyoriginal.game.system.box2d;

import com.artemis.E;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Joint;

// Oyente de contacto
public interface BoxContactListener {

	/**
	 * Llamado para ambas direcciones, a,b y b,a.
	 * <p>
	 * Advertencia: llamado desde el {@link BoxPhysicsSystem} ciclo de vida del proceso, retrasar cualquier cosa que rompa ese ciclo de
	 * vida (eliminacion/creacion de entidades).
	 */

	// Comenzar contacto
	void beginContact(E a, E b);

	// Finalizar contacto
	void endContact(E a, E b);
}
