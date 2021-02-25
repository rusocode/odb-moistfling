package net.mostlyoriginal.api.util;

/**
 * Rastrea un cooldown que necesita dispararse a un cierto intervalo.
 * <p>
 * {@code Cooldown c = c.withInterval(seconds(5));}
 * Call {@code if(c.ready(delta)) { doAction(); } }
 * <p>
 * De forma predeterminada, se activa siempre que ha pasado el intervalo dado y se restablece a otro intervalo completo.
 * <p>
 * Use {@link #subtractOverflowFromNextCooldown(boolean)} para configurar como lidiar con los desbordamientos.
 * Digamos que un tiempo de reutilizacion de 10 ms se activa con un delta de 16 ms. El desbordamiento
 * de 6 ms puede contar para el siguiente intervalo.
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

	public static Cooldown withInterval(float interval) {
		return new Cooldown(interval);
	}

	public static Cooldown withInterval(FloatSupplier interval) {
		return new Cooldown(interval);
	}

	/**
	 * Decrease cooldown by seconds.
	 * Does not trigger reset until {@link #ready(float)} is called.
	 *
	 * @param delta seconds to decrease cooldown with.
	 */
	public Cooldown decreaseBy(float delta) {
		cooldown -= delta;
		return this;
	}

	/**
	 * Reset cooldown to interval.
	 *
	 * @return Cooldown for chaining.
	 */
	public Cooldown restart() {
		final float interval = intervalSupplier.get();
		if (subtractOverflowFromNextCooldown) {
			// enough to immediately trigger the cooldown again.
			if (cooldown < -interval) {
				cooldown = 0;
			} else cooldown += interval;
		} else {
			cooldown = interval;
		}
		return this;
	}

	/**
	 * Set new interval. Does not affect cooldown.
	 *
	 * @param interval new interval.
	 * @return Cooldown for chaining.
	 */
	public Cooldown newInterval(float interval) {
		this.intervalSupplier = () -> interval;
		return this;
	}

	/**
	 * Set new interval. Does not affect cooldown.
	 *
	 * @param intervalSupplier supplies interval.
	 * @return Cooldown for chaining.
	 */
	public Cooldown newInterval(FloatSupplier intervalSupplier) {
		this.intervalSupplier = intervalSupplier;
		return this;
	}

	/**
	 * Set cooldown to given seconds. Does not affect subsequent interval.
	 *
	 * @param cooldown cooldown in seconds.
	 * @return Cooldown for chaining.
	 */
	public Cooldown set(float cooldown) {
		this.cooldown = cooldown;
		return this;
	}

	/**
	 * Progress cooldown by delta seconds, and test if the cooldown has run out.
	 * When autoReset is enabled, calling this method will call {@link #restart()} automatically.
	 *
	 * @return {@code true} when the cooldown has run out.
	 */
	public boolean ready(float delta) {
		if (cooldown > 0) {
			decreaseBy(delta);
		}
		return ready();
	}

	/**
	 * Test if the cooldown has run out.
	 * When autoReset is enabled, calling this method will call {@link #restart()} automatically.
	 *
	 * @return {@code true} when the cooldown has run out.
	 */
	public boolean ready() {
		boolean isReady = cooldown <= 0;
		if (isReady && autoReset) {
			restart();
		}
		return isReady;
	}

	/**
	 * @return {@code true} if the cooldown resets to interval whenever it reaches zero or below. {@code false} if the user will manually call {@link #restart()}.
	 */
	public boolean isAutoReset() {
		return autoReset;
	}

	/**
	 * @param value {@code true} if the cooldown resets to interval whenever it reaches zero or below.
	 *              {@code false} if the user will manually call {@link #restart()}.
	 * @return Cooldown for chaining.
	 */
	public Cooldown autoReset(boolean value) {
		this.autoReset = value;
		return this;
	}

	/**
	 * When {@code true} any overflow is subtracted from the next cooldown. (So if you have 10ms cooldoswn, and the
	 * frame took 16ms, the next cooldown will take 4ms). Useful when the time per frame is significant for the
	 * cooldown.
	 *
	 * @return {@code true} when overflow is subtracted, {@code false} if full interval is used for resets.
	 */
	public boolean isSubtractOverflowFromNextCooldown() {
		return subtractOverflowFromNextCooldown;
	}

	/**
	 * When {@code true} any overflow is subtracted from the next cooldown. (So if you have 10ms cooldoswn, and the
	 * frame took 16ms, the next cooldown will take 4ms). Useful when the time per frame is significant for the
	 * cooldown.
	 *
	 * @param value
	 * @return Cooldown for chaining.
	 */
	public Cooldown subtractOverflowFromNextCooldown(boolean value) {
		this.subtractOverflowFromNextCooldown = value;
		return this;
	}

	/**
	 * @return get current cooldown in seconds.
	 */
	public float get() {
		return cooldown;
	}
}
