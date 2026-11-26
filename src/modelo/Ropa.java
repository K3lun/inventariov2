package modelo;

/**
 * Subclase de Producto. Hereda los atributos comunes y agrega talla y material.
 * Aplica IVA del 16% y no maneja descuentos.
 */
public class Ropa extends Producto {

    private static final double IVA = 0.16;

    private final String talla;
    private final String material;

    public Ropa(String codigo, String nombre, double precioBase, int cantidad,
                String talla, String material) {
        super(codigo, nombre, precioBase, cantidad);
        this.talla = talla;
        this.material = material;
    }

    @Override
    public double calcularPrecioFinal() {
        return precioBase * (1 + IVA);
    }

    @Override
    public String getCategoria() { return "Ropa"; }

    @Override
    public String obtenerDetalles() {
        return "Talla: " + talla + ", material: " + material;
    }
}
