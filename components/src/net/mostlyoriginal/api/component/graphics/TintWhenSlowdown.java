package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;
import com.artemis.annotations.Fluid;
import com.badlogic.gdx.graphics.Color;

/**
 * Colorear para animaciones, etiquetas.
 * <p>
 * Opcional, la convencion es asumir blanco si no esta establecido.
 */
@Fluid(swallowGettersWithParameters = true) public class TintWhenSlowdown extends Component {
	public Color normal = new Color();
	public Color slow = new Color();
}
