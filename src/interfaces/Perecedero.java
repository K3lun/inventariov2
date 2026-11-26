package interfaces;

import java.time.LocalDate;

/**
 * Interfaz para los productos que tienen fecha de caducidad.
 */
public interface Perecedero {

    LocalDate getFechaCaducidad();

    /** Indica si el producto ya vencio respecto a una fecha de referencia. */
    boolean estaVencido(LocalDate hoy);
}
