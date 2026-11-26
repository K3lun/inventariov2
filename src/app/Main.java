package app;

import interfaces.Descontable;
import interfaces.Perecedero;
import modelo.*;
import java.time.LocalDate;

/** Programa principal: demuestra el polimorfismo del sistema de inventarios. */
public class Main {

    public static void main(String[] args) {
        LocalDate hoy = LocalDate.of(2026, 11, 25);
        Inventario inv = new Inventario();

        // Referencia de tipo Producto, objetos de distintas subclases
        inv.agregar(new Electronico("E-001", "Laptop 15 pulgadas", 12000, 5, "Lenovo", 12, true));
        inv.agregar(new Electronico("E-002", "Audifonos Bluetooth", 800, 20, "JBL", 6, false));
        inv.agregar(new Alimento("A-001", "Yogurt natural 1L", 35, 40, hoy.plusDays(5), hoy));
        inv.agregar(new Alimento("A-002", "Galletas de avena", 28, 60, hoy.plusDays(90), hoy));
        inv.agregar(new Alimento("A-003", "Leche entera 1L", 26, 10, hoy.minusDays(2), hoy));
        inv.agregar(new Ropa("R-001", "Playera de algodon", 250, 30, "M", "Algodon"));
        inv.agregar(new Ropa("R-002", "Chamarra de mezclilla", 900, 8, "L", "Mezclilla"));

        System.out.println("=== INVENTARIO (polimorfismo con toString) ===");
        inv.mostrarInventario();

        System.out.println("\n=== Mismo metodo, distinto resultado (calcularPrecioFinal) ===");
        for (Producto p : inv.getProductos()) {
            System.out.printf("%-22s -> $%9.2f  (%s)%n",
                    p.getNombre(), p.calcularPrecioFinal(), p.getClass().getSimpleName());
        }

        System.out.println("\n=== Uso de interfaces con instanceof ===");
        for (Producto p : inv.getProductos()) {
            if (p instanceof Descontable d && d.getPorcentajeDescuento() > 0) {
                System.out.printf("%s tiene descuento de %.0f%%%n",
                        p.getNombre(), d.getPorcentajeDescuento() * 100);
            }
            if (p instanceof Perecedero per && per.estaVencido(hoy)) {
                System.out.println("ALERTA: " + p.getNombre() + " esta vencido");
            }
        }

        System.out.println("\n=== Ventas ===");
        inv.vender("E-001", 2);
        inv.vender("R-001", 10);
        try {
            inv.vender("R-002", 50); // stock insuficiente
        } catch (IllegalStateException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        System.out.printf("%nValor total del inventario: $%,.2f%n", inv.calcularValorTotal());
    }
}
