package net.mostlyoriginal.game.events;

import net.mostlyoriginal.api.event.common.Event;

// Recogida de elementos de evento
public class EventItemPickup implements Event {
	public int id;

	public EventItemPickup(int id) {
		this.id = id;
	}
}
