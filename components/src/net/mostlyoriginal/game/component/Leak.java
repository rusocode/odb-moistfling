package net.mostlyoriginal.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

// Leak
public class Leak extends Component {
	public static final int MAXLEAKS = 30; // wtf?
	public float age = 0;
	public float speed = 1f;
	public int leaks = 0;
	public float lastLeakAge = 999f;
}
