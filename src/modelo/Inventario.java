package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Administra la lista de productos. Usa SOLO el tipo Producto, por eso
 * funciona igual con cualquier subclase presente o futura (polimorfismo).
 */
public class Inventario {

    private final List<Producto> productos = new ArrayList<>();

    public void agregar(Producto p) {
        if (buscarPorCodigo(p.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe el codigo " + p.getCodigo());
        }
        productos.add(p);
    }

    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productos.stream().filter(p -> p.getCodigo().equals(codigo)).findFirst();
    }

    public void vender(String codigo, int unidades) {
        Producto p = buscarPorCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el codigo " + codigo));
        p.vender(unidades);
    }

    /** Suma el valor de todos los productos; cada uno calcula su precio a su modo. */
    public double calcularValorTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.calcularValorInventario(); // llamada polimorfica
        }
        return total;
    }

    public void mostrarInventario() {
        for (Producto p : productos) {
            System.out.println(p); // toString() usa metodos abstractos implementados por cada tipo
        }
    }

    public List<Producto> getProductos() { return productos; }
}
