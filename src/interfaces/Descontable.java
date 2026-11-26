package interfaces;

/**
 * Interfaz para los productos que pueden recibir un descuento.
 * Cada clase que la implemente decide su propia regla de descuento.
 */
public interface Descontable {

    /** Devuelve el porcentaje de descuento (0.10 = 10%). */
    double getPorcentajeDescuento();

    /** Metodo por defecto: calcula el monto a descontar sobre un precio dado. */
    default double calcularDescuento(double precio) {
        return precio * getPorcentajeDescuento();
    }
}
