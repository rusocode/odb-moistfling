package net.mostlyoriginal.api.util;

/**
 * Esta es una interfaz funcional y, por lo tanto, se puede utilizar como destino de asignacion para una
 * expresion lambda o referencia de metodo.
 */
@FunctionalInterface public interface FloatSupplier {
	float get();
}
