package modelo;

/**
 * Clase abstracta base de todos los productos del inventario.
 * Define los atributos comunes y los metodos que cada subclase DEBE implementar.
 * Aqui se apoya el polimorfismo: el inventario trabaja con "Producto" sin
 * importar si el objeto real es Electronico, Alimento o Ropa.
 */
public abstract class Producto {

    // Atributos comunes (protected para que las subclases puedan usarlos)
    protected final String codigo;
    protected String nombre;
    protected double precioBase;
    protected int cantidad;

    public Producto(String codigo, String nombre, double precioBase, int cantidad) {
        if (precioBase < 0 || cantidad < 0) {
            throw new IllegalArgumentException("Precio y cantidad no pueden ser negativos");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.cantidad = cantidad;
    }

    // ----- Metodos abstractos: cada subclase los implementa a su manera -----

    /** Precio final de una unidad (con impuestos y/o descuentos segun el tipo). */
    public abstract double calcularPrecioFinal();

    /** Nombre de la categoria del producto. */
    public abstract String getCategoria();

    /** Detalles propios de cada tipo de producto. */
    public abstract String obtenerDetalles();

    // ----- Metodos concretos: se heredan tal cual -----

    /** Valor total en bodega de este producto (precio final x existencias). */
    public double calcularValorInventario() {
        return calcularPrecioFinal() * cantidad;
    }

    /** Resta existencias; lanza error si no alcanza el stock. */
    public void vender(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores a 0");
        }
        if (unidades > cantidad) {
            throw new IllegalStateException("Stock insuficiente de " + nombre
                    + " (disponible: " + cantidad + ")");
        }
        cantidad -= unidades;
    }

    /** Suma existencias. */
    public void reabastecer(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores a 0");
        }
        cantidad += unidades;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioBase() { return precioBase; }
    public int getCantidad() { return cantidad; }

    @Override
    public String toString() {
        return String.format("[%s] %-22s | %-11s | Precio final: $%9.2f | Stock: %3d%n        %s",
                codigo, nombre, getCategoria(), calcularPrecioFinal(), cantidad, obtenerDetalles());
    }
}
