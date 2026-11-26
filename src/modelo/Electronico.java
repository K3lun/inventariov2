package modelo;

import interfaces.Descontable;

/**
 * Subclase de Producto. Hereda codigo, nombre, precio y cantidad.
 * Agrega marca y garantia, y aplica IVA del 16%.
 * Implementa Descontable: 10% de descuento si el producto es de promocion.
 */
public class Electronico extends Producto implements Descontable {

    private static final double IVA = 0.16;

    private final String marca;
    private final int garantiaMeses;
    private boolean enPromocion;

    public Electronico(String codigo, String nombre, double precioBase, int cantidad,
                       String marca, int garantiaMeses, boolean enPromocion) {
        super(codigo, nombre, precioBase, cantidad); // llama al constructor del padre
        this.marca = marca;
        this.garantiaMeses = garantiaMeses;
        this.enPromocion = enPromocion;
    }

    @Override
    public double getPorcentajeDescuento() {
        return enPromocion ? 0.10 : 0.0;
    }

    /** Implementacion propia: precio - descuento + IVA. */
    @Override
    public double calcularPrecioFinal() {
        double conDescuento = precioBase - calcularDescuento(precioBase);
        return conDescuento * (1 + IVA);
    }

    @Override
    public String getCategoria() { return "Electronico"; }

    @Override
    public String obtenerDetalles() {
        return "Marca: " + marca + ", garantia: " + garantiaMeses + " meses"
                + (enPromocion ? ", EN PROMOCION" : "");
    }
}
