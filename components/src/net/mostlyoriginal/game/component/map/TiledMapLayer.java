package net.mostlyoriginal.game.component.map;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Capa de mosaico TiledMap (cuadros).
 */
@Transient public class TiledMapLayer extends Component {
	public TiledMapTileLayer layer;
}
