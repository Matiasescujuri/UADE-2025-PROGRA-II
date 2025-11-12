package org;
import org.SimpleDictionary;
import org.SimpleDictionaryADT;
import org.*;
import java.util.Scanner;

public class Main { //======================================================================= MAIN ==========

    // === VARIABLES GLOBALES ===
    static Scanner sc = new Scanner(System.in);
    static Pedido[] registros = new Pedido[50];
    static int contadorPedidos = 0;

    static PriorityQueueADT pedidos = new ColaPrioridad(10);
    static QueueADT platos = new Cola(10);
    static QueueADT entregasCola = new Cola(10);
    static StackADT Analisis = new Pila();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== GESTION DE PEDIDOS ===");
            System.out.println("1. Ingreso y clasificacion de pedidos");
            System.out.println("2. Preparacion de platos en Cocina");
            System.out.println("3. Entrega de pedidos");
            System.out.println("4. Reportes y analisis");
            System.out.println("5. Salir");
            System.out.print("SELECCIONE UNA OPCION: ");
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                System.out.println(" Ingrese solo números (1-5).");
                sc.next();
                opcion = 0;
            }

            switch (opcion) {
                case 1 -> IngresoPedidos.ingresoPedidos();
                case 2 -> Cocina.prepararPlatos();
                case 3 -> Entregas.entregas();
                case 4 -> ReportesYAnalisis.reportesYAnalisis();
                case 5 -> System.out.println("SALIENDO DEL SISTEMA....");
                default -> System.out.println("Opcion no valida");
            }
        } while (opcion != 5);
    }
}


// ====================== CLASE INGRESO PEDIDOS ======================

class IngresoPedidos {// ========================================================= PEDIDOS ================

    public static void ingresoPedidos() {
        Main.sc.nextLine();

        String[] COMIDAS = {"1-Pizza", "2-Empanadas", "3-Hamburguesa"};
        System.out.println("=== INGRESO Y CLASIFICACION DE PEDIDOS ===");
        for (String comida : COMIDAS) System.out.print(comida + " ");

        System.out.print("\nIngrese el nombre del cliente: ");
        String nombreCliente = Main.sc.nextLine();

        if (nombreCliente.trim().isEmpty()) {
            System.out.println("️El nombre no puede estar vacío.");
            return;
        }

        if (nombreCliente.matches("\\d+")) {
            System.out.println("El nombre no puede ser un número.");
            return;
        }

        System.out.print("\nIngrese número de comida: ");
        int opcioncomida;
        if (Main.sc.hasNextInt()) {
            opcioncomida = Main.sc.nextInt();
        } else {
            System.out.println(" Ingrese un número válido (1-3).");
            Main.sc.next();
            return;
        }

        String nombrepedido = "";
        switch (opcioncomida) {
            case 1 -> nombrepedido = "Pizza";
            case 2 -> nombrepedido = "Empanadas";
            case 3 -> nombrepedido = "Hamburguesa";
            default -> {
                System.out.println("Opción no válida");
                return;
            }
        }

        System.out.println("=== Ingrese el nivel de prioridad (1-10) ===");
        int prioridad = Main.sc.nextInt();

        Pedido nuevoPedido = new Pedido(new String[]{nombrepedido, nombreCliente}, prioridad);
        nuevoPedido.setEstado(estadoPedido.PENDIENTE);

        Main.registros[Main.contadorPedidos] = nuevoPedido;
        Main.pedidos.add(Main.contadorPedidos, prioridad);
        Main.contadorPedidos++;

        System.out.println("=== PEDIDO AGREGADO ===");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Pedido: " + nombrepedido + " // PRIORIDAD: " + prioridad);
    }
}


// ====================== CLASE COCINA ======================

class Cocina {//=================================================================== COCINA

    public static void prepararPlatos() {
        System.out.println("=== PREPARACIÓN DE PLATOS ===");

        if (Main.pedidos.isEmpty()) {
            System.out.println("No hay pedidos para preparar.");
            return;
        }

        PriorityQueueADT copiaPedidos = Utilidades.copiarColaPrioridad(Main.pedidos);

        while (!Main.platos.isEmpty()) Main.platos.remove();

        while (!copiaPedidos.isEmpty()) {
            int pedido = (int) copiaPedidos.getElement();
            int prioridad = (int) copiaPedidos.getPriority();
            Pedido pedidoData = Main.registros[pedido];

            Main.platos.add(pedido);
            System.out.println("Pedido " + pedidoData.platos[0] +
                    " enviado a cocina (prioridad " + prioridad +
                    ", estado: " + pedidoData.estado + ")");
            copiaPedidos.remove();
        }

        System.out.println("Pedidos enviados a la cocina.");
        QueueADT copiaPlatos = Utilidades.copiarCola(Main.platos);
        Utilidades.pasarColaAPila(copiaPlatos);

        Main.pedidos = Utilidades.limpiarColaFinalizados(Main.pedidos);
    }
}


// ====================== CLASE ENTREGAS ======================

class Entregas { //=============================================================== ENTREGAS

    public static void entregas() {
        System.out.println("=== ENTREGA DE PEDIDOS ===");

        if (Main.Analisis.isEmpty()) {
            System.out.println("No hay pedidos en la pila de entregas.");
            return;
        }

        int id = (int) Main.Analisis.getElement();
        Main.Analisis.remove();

        Pedido p = Main.registros[id];

        String origen = "Cocina Central";
        String destino = switch (id % 3) {
            case 0 -> "Grito del Corto 3028";
            case 1 -> "Hipólito Yrigoyen 962";
            default -> "Plaza Principal";
        };

        int distancia = switch (id % 3) {
            case 0 -> 7;
            case 1 -> 12;
            default -> 6;
        };

        System.out.println("Ruta: " + origen + " → " + destino + " (" + distancia + " min)");
        p.setEstado(estadoPedido.FINALIZADO);
        System.out.println(" Pedido entregado: " + p.platos[0] + " | Estado: " + p.getEstado());

        Main.pedidos = Utilidades.limpiarColaFinalizados(Main.pedidos);
    }
}


// ====================== CLASE REPORTES Y ANALISIS ======================

class ReportesYAnalisis { //================================================ REPORTES Y ANALISIS =============

    public static void reportesYAnalisis() {
        System.out.println("=== REPORTES Y ANÁLISIS ===");

        SimpleDictionaryADT dicEstados = new SimpleDictionary();
        SimpleDictionaryADT dicComidas = new SimpleDictionary();

        for (int i = 0; i < Main.contadorPedidos; i++) {
            Pedido p = Main.registros[i];
            if (p == null) continue;

            // ESTADOS
            int keyEstado = (p.getEstado() == estadoPedido.FINALIZADO) ? 1 : 0;
            int valorEstado = dicEstados.get(keyEstado);
            dicEstados.add(keyEstado, valorEstado + 1);



            int valorComida = 0;
            SetADT clavesC = dicComidas.getKeys();

            int keyComida = switch (p.platos[0]) {
                case "Pizza" -> 1;
                case "Empanadas" -> 2;
                default -> 3;
            };

            while (!clavesC.isEmpty()) {
                int clave = clavesC.choose();
                if (clave == keyComida) {
                    valorComida = dicComidas.get(clave);
                }
                clavesC.remove(clave);
            }

            dicComidas.add(keyComida, valorComida + 1);


        }

        System.out.println("\n ESTADO DE LOS PEDIDOS:");
        System.out.println("Pendientes: " + dicEstados.get(0));
        System.out.println("Finalizados: " + dicEstados.get(1));

        System.out.println("\n COMIDAS MÁS PEDIDAS:");
        SetADT clavesComida = dicComidas.getKeys();
        while (!clavesComida.isEmpty()) {
            int clave = clavesComida.choose();
            String nombre = switch (clave) {
                case 1 -> "Pizza";
                case 2 -> "Empanadas";
                default -> "Hamburguesa";
            };
            System.out.println(nombre + ": " + dicComidas.get(clave));
            clavesComida.remove(clave);
        }

        System.out.println("\n Total de pedidos registrados: " + Main.contadorPedidos);
    }
}


// ====================== CLASE UTILIDADES ======================

class Utilidades { // ======================================================================== utilidades ====

    public static PriorityQueueADT copiarColaPrioridad(PriorityQueueADT original) {
        PriorityQueueADT copia = new ColaPrioridad(10);
        PriorityQueueADT aux = new ColaPrioridad(10);

        while (!original.isEmpty()) {
            int elem = (int) original.getElement();
            int prio = (int) original.getPriority();
            original.remove();
            copia.add(elem, prio);
            aux.add(elem, prio);
        }

        while (!aux.isEmpty()) {
            int elem = (int) aux.getElement();
            int prio = (int) aux.getPriority();
            aux.remove();
            original.add(elem, prio);
        }
        return copia;
    }

    public static QueueADT copiarCola(QueueADT original) {
        QueueADT copia = new Cola(10);
        QueueADT aux = new Cola(10);

        while (!original.isEmpty()) {
            int elem = (int) original.getElement();
            original.remove();
            copia.add(elem);
            aux.add(elem);
        }

        while (!aux.isEmpty()) {
            int elem = (int) aux.getElement();
            aux.remove();
            original.add(elem);
        }
        return copia;
    }

    public static void pasarColaAPila(QueueADT copiaPlatos) {
        while (!Main.Analisis.isEmpty()) Main.Analisis.remove();

        while (!copiaPlatos.isEmpty()) {
            int id = (int) copiaPlatos.getElement();
            copiaPlatos.remove();
            Main.Analisis.add(id);
            System.out.println("→ Pedido " + Main.registros[id].platos[0]);
        }
    }

    public static PriorityQueueADT limpiarColaFinalizados(PriorityQueueADT pedidos) {
        PriorityQueueADT nuevaCola = new ColaPrioridad(10);
        PriorityQueueADT aux = copiarColaPrioridad(pedidos);

        while (!aux.isEmpty()) {
            int id = (int) aux.getElement();
            int prioridad = (int) aux.getPriority();
            Pedido p = Main.registros[id];
            if (p.getEstado() != estadoPedido.FINALIZADO) nuevaCola.add(id, prioridad);
            aux.remove();
        }

        return nuevaCola;
    }
}
