package net.mostlyoriginal.game.component;

import java.io.Serializable;

// Datos del sprite
public class SpriteData implements Serializable {

	public String id;
	public String comment; // not used.

	public int x;
	public int y;
	public int width;
	public int height;
	public int countX = 1; // delta?
	public int countY = 1;
	public float milliseconds = 200;
	public boolean repeat = true;

	public SpriteData() {
	}
}
