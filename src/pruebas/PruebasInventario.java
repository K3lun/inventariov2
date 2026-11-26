package pruebas;

import modelo.*;
import java.time.LocalDate;

/**
 * Pruebas sencillas sin librerias externas (se ejecutan con java directamente).
 * Cada prueba verifica un comportamiento polimorfico o una regla de negocio.
 */
public class PruebasInventario {

    private static int pasadas = 0, falladas = 0;

    public static void main(String[] args) {
        LocalDate hoy = LocalDate.of(2026, 11, 25);

        // Precio final distinto segun el tipo
        verificar("Electronico con promo: 1000 -> 1044",
                cerca(new Electronico("E", "TV", 1000, 1, "X", 12, true).calcularPrecioFinal(), 1044.0));
        verificar("Electronico sin promo: 1000 -> 1160",
                cerca(new Electronico("E", "TV", 1000, 1, "X", 12, false).calcularPrecioFinal(), 1160.0));
        verificar("Ropa lleva IVA: 100 -> 116",
                cerca(new Ropa("R", "Playera", 100, 1, "M", "Algodon").calcularPrecioFinal(), 116.0));
        verificar("Alimento por caducar: 100 -> 70",
                cerca(new Alimento("A", "Pan", 100, 1, hoy.plusDays(3), hoy).calcularPrecioFinal(), 70.0));
        verificar("Alimento vencido vale 0",
                cerca(new Alimento("A", "Leche", 100, 1, hoy.minusDays(1), hoy).calcularPrecioFinal(), 0.0));

        // Polimorfismo: un arreglo de Producto, cada uno responde a su manera
        Producto[] lista = {
            new Ropa("R", "Playera", 100, 1, "M", "Algodon"),
            new Electronico("E", "TV", 1000, 1, "X", 12, false)
        };
        verificar("Categorias distintas via Producto",
                lista[0].getCategoria().equals("Ropa") && lista[1].getCategoria().equals("Electronico"));

        // Reglas de stock
        Producto p = new Ropa("R", "Playera", 100, 5, "M", "Algodon");
        p.vender(3);
        verificar("Vender descuenta stock", p.getCantidad() == 2);
        verificar("Vender de mas lanza error", lanza(() -> p.vender(10)));
        verificar("Reabastecer suma stock", sumaStock(p));

        // Inventario
        Inventario inv = new Inventario();
        inv.agregar(new Ropa("R-1", "Playera", 100, 2, "M", "Algodon"));
        verificar("Codigo duplicado lanza error",
                lanza(() -> inv.agregar(new Ropa("R-1", "Otra", 50, 1, "S", "Lino"))));
        verificar("Valor total = 116 x 2", cerca(inv.calcularValorTotal(), 232.0));

        System.out.println("\nResultado: " + pasadas + " pasadas, " + falladas + " falladas");
        if (falladas > 0) System.exit(1);
    }

    private static boolean sumaStock(Producto p) { p.reabastecer(4); return p.getCantidad() == 6; }
    private static boolean cerca(double a, double b) { return Math.abs(a - b) < 0.01; }

    private static boolean lanza(Runnable r) {
        try { r.run(); return false; } catch (RuntimeException e) { return true; }
    }

    private static void verificar(String nombre, boolean ok) {
        System.out.println((ok ? "[OK]    " : "[FALLA] ") + nombre);
        if (ok) pasadas++; else falladas++;
    }
}
