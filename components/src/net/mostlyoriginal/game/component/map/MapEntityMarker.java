package net.mostlyoriginal.game.component.map;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.maps.MapProperties;

/**
 * Marca una entidad como proporcionada en el mapa.
 */
@Transient public class MapEntityMarker extends Component {
	public int mapX;
	public int mapY;
	public MapProperties properties;
}
