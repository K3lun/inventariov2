package modelo;

import interfaces.Descontable;
import interfaces.Perecedero;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Subclase de Producto. Los alimentos no llevan IVA (tasa 0%) en este ejemplo.
 * Implementa DOS interfaces: Perecedero (fecha de caducidad) y Descontable
 * (descuento del 30% cuando faltan 7 dias o menos para caducar).
 */
public class Alimento extends Producto implements Perecedero, Descontable {

    private final LocalDate fechaCaducidad;
    private final LocalDate fechaReferencia; // "hoy" inyectado para poder probar

    public Alimento(String codigo, String nombre, double precioBase, int cantidad,
                    LocalDate fechaCaducidad, LocalDate fechaReferencia) {
        super(codigo, nombre, precioBase, cantidad);
        this.fechaCaducidad = fechaCaducidad;
        this.fechaReferencia = fechaReferencia;
    }

    @Override
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }

    @Override
    public boolean estaVencido(LocalDate hoy) {
        return hoy.isAfter(fechaCaducidad);
    }

    @Override
    public double getPorcentajeDescuento() {
        long dias = ChronoUnit.DAYS.between(fechaReferencia, fechaCaducidad);
        return (dias >= 0 && dias <= 7) ? 0.30 : 0.0;
    }

    @Override
    public double calcularPrecioFinal() {
        if (estaVencido(fechaReferencia)) {
            return 0.0; // un producto vencido ya no se puede vender
        }
        return precioBase - calcularDescuento(precioBase);
    }

    @Override
    public String getCategoria() { return "Alimento"; }

    @Override
    public String obtenerDetalles() {
        return "Caduca: " + fechaCaducidad
                + (estaVencido(fechaReferencia) ? " (VENCIDO)" : "");
    }
}
