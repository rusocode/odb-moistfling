package net.mostlyoriginal.api.util;

/**
 * Rastrea un cooldown que necesita dispararse a un cierto intervalo.
 * <p>
 * {@code Cooldown c = c.withInterval(seconds(5));}
 * Call {@code if(c.ready(delta)) { doAction(); } }
 * <p>
 * Por defecto, se activa siempre que ha pasado el intervalo dado y se restablece a otro intervalo completo.
 * <p>
 * Use {@link #subtractOverflowFromNextCooldown(boolean)} para configurar como lidiar con los desbordamientos.
 * Digamos que un cooldown de 10ms se activa con un delta de 16ms. El desbordamiento
 * de 6ms puede contar para el siguiente intervalo.
 * <p>
 * Use {@link #autoReset(boolean)} cuando desee controlar el reinicio manualmente ejecutando {@link #restart()}
 */
public class Cooldown {

	private FloatSupplier intervalSupplier; // Proveedor de intervalo
	private float cooldown;
	private boolean autoReset = true;
	private boolean subtractOverflowFromNextCooldown = false;

	private Cooldown(float intervalSupplier) {
		this.intervalSupplier = () -> intervalSupplier;
		this.cooldown = intervalSupplier;
	}

	private Cooldown(FloatSupplier intervalSupplier) {
		this.intervalSupplier = intervalSupplier;
		this.cooldown = this.intervalSupplier.get();
	}

	// Cooldown con un intervalo especificado de tipo float
	public static Cooldown withInterval(float interval) {
		return new Cooldown(interval);
	}

	// Cooldown con un intervalo especificado de tipo FloatSupplier
	public static Cooldown withInterval(FloatSupplier interval) {
		return new Cooldown(interval);
	}

	/**
	 * Disminuye el cooldown en segundos.
	 * No activa el restablecimiento hasta que se llama a {@link #ready(float)}.
	 *
	 * @param delta para disminuir el enfriamiento
	 */
	public Cooldown decreaseBy(float delta) {
		cooldown -= delta;
		return this;
	}

	/**
	 * Restablece el cooldown al intervalo.
	 *
	 * @return cooldown para encadenar
	 */
	public Cooldown restart() {

		final float interval = intervalSupplier.get();

		// Si se resto el desbordamiento del siguiente cooldown, entonces...
		if (subtractOverflowFromNextCooldown) {

			// Lo suficiente para activar de inmediato el tiempo de reutilizacion
			if (cooldown < -interval) cooldown = 0;
			else cooldown += interval;

		} else cooldown = interval;

		return this;
	}

	/**
	 * Establece un nuevo intervalo. No afecta el enfriamiento.
	 *
	 * @param interval
	 * @return cooldown para encadenar
	 */
	public Cooldown newInterval(float interval) {
		this.intervalSupplier = () -> interval;
		return this;
	}

	/**
	 * Establece un nuevo intervalo. No afecta el enfriamiento.
	 *
	 * @param intervalSupplier
	 * @return cooldown para encadenar
	 */
	public Cooldown newInterval(FloatSupplier intervalSupplier) {
		this.intervalSupplier = intervalSupplier;
		return this;
	}

	/**
	 * Establece el tiempo de reutilizacion en segundos determinados. No afecta el intervalo posterior.
	 *
	 * @param cooldown en segundos
	 * @return cooldown para encadenar
	 */
	public Cooldown set(float cooldown) {
		this.cooldown = cooldown;
		return this;
	}

	/**
	 * Progresa el cooldown por segundos delta y prueba si se ha agotado.
	 * Cuando autoReset este habilitado, este metodo llamara a {@link #restart()} automaticamente.
	 *
	 * @return {@code true} cuando se agota el cooldown
	 */
	public boolean ready(float delta) {
		if (cooldown > 0) decreaseBy(delta);
		return ready();
	}

	/**
	 * Prueba si el cooldown se ha agotado.
	 * Cuando autoReset este habilitado, este metodo llamara a {@link #restart()} automaticamente.
	 *
	 * @return {@code true} cuando se agota el cooldown
	 */
	public boolean ready() {
		boolean isReady = cooldown <= 0;
		if (isReady && autoReset) restart();
		return isReady;
	}

	/**
	 * @return {@code true} si el cooldown se restablece al intervalo cuando llega a cero o menos
	 * {@code false} si el usuario llamara manualmente {@link #restart()}
	 */
	public boolean isAutoReset() {
		return autoReset;
	}

	/**
	 * @param value {@code true} si el cooldown se restablece al intervalo cuando llega a cero o menos
	 *              {@code false} si el usuario llama manualmente {@link #restart()}
	 * @return cooldown para encadenar
	 */
	public Cooldown autoReset(boolean value) {
		this.autoReset = value;
		return this;
	}

	/**
	 * Cuando {@code true} se resta cualquier desbordamiento del siguiente cooldown. (Por lo tanto, si tiene 10ms cooldown, y el
	 * cuadro tomo 16ms, el siguiente tiempo de reutilizacion tomara 4ms). Util cuando el tiempo por cuadro es
	 * significativo para el cooldown.
	 *
	 * @return {@code true} cuando se resta el desbordamiento
	 * {@code false} si se usa el intervalo completo para restablecimientos
	 */
	public boolean isSubtractOverflowFromNextCooldown() {
		return subtractOverflowFromNextCooldown;
	}

	/**
	 * Cuando {@code true} se resta cualquier desbordamiento del siguiente cooldown. (Por lo tanto, si tiene 10ms cooldown, y el
	 * cuadro tomo 16ms, el siguiente tiempo de reutilizacion tomara 4ms). Util cuando el tiempo por cuadro es
	 * significativo para el cooldown.
	 *
	 * @param value
	 * @return cooldown para encadenar
	 */
	public Cooldown subtractOverflowFromNextCooldown(boolean value) {
		this.subtractOverflowFromNextCooldown = value;
		return this;
	}

	/**
	 * @return el cooldown actual en segundos
	 */
	public float get() {
		return cooldown;
	}
}
