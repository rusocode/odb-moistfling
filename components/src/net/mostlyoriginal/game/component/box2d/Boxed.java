package net.mostlyoriginal.game.component.box2d;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Un cuerpo rigido. Estos se crean a traves de World.CreateBody.
 */
@DelayedComponentRemoval public class Boxed extends Component {
	public Body body;
}
