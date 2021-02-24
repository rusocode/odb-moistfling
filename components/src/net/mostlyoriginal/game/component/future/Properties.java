package net.mostlyoriginal.game.component.future;

import com.artemis.Component;
import com.artemis.annotations.FluidMethod;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Contenedor generico de propiedades dinamicas.
 * Es mejor utilizar componentes concretos en la mayoria de los casos.
 */
public class Properties extends Component {

	public ObjectMap<String, Object> properties = new ObjectMap<>();

	public void set(String key, boolean value) {
		properties.put(key, value);
	}

	public void set(String key, int value) {
		properties.put(key, value);
	}

	public void set(String key, String value) {
		properties.put(key, value);
	}

	public void set(String key, Object value) {
		properties.put(key, value);
	}

	// Devuelve el valor (que puede ser nulo) para la clave especificada, o nulo si la clave no esta en el mapa
	public int getInt(String key) {
		return (int) properties.get(key);
	}

	@SuppressWarnings("unchecked") @FluidMethod(exclude = true)
	// No se llama 'get' porque el generador de entidades fluidas no puede manejar
	public <T extends Enum> T getEnum(String key, Class<T> enumClazz) {
		return (T) properties.get(key);
	}

	public String getString(String key) {
		return (String) properties.get(key);
	}

	public Boolean getBoolean(String key) {
		return (Boolean) properties.get(key);
	}
}
