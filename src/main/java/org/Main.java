package org;
import org.SimpleDictionary;
import org.SimpleDictionaryADT;
import org.*;
import java.util.Scanner;

public class Main { //======================================================================= VARIABLES ==========

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
            System.out.println("1. Ingreso y clasificacion de pedidos"); // Cola de prioridad
            System.out.println("2. Preparacion de platos en Cocina"); // cola
            System.out.println("3. Entrega de pedidos"); // grafos y LIFO
            System.out.println("4. Reportes y analisis");// Simple dictionary
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

// ====================== CLASE INGRESO PEDIDOS ======================

class IngresoPedidos { //   Cola de prioridad
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

        // === BLOQUE NUEVO: VARIOS PLATOS POR PEDIDO ===
        System.out.print("\n¿Cuántos platos desea agregar al pedido? ");
        int cantidadPlatos;
        if (Main.sc.hasNextInt()) {
            cantidadPlatos = Main.sc.nextInt();
            Main.sc.nextLine(); // limpiar buffer
        } else {
            System.out.println("Ingrese un número válido.");
            Main.sc.next();
            return;
        }

        if (cantidadPlatos <= 0) {
            System.out.println("Debe ingresar al menos un plato.");
            return;
        }

        String[] platosPedido = new String[cantidadPlatos];
        for (int i = 0; i < cantidadPlatos; i++) {
            System.out.print("\nIngrese número de comida #" + (i + 1) + ": ");
            int opcioncomida;
            if (Main.sc.hasNextInt()) {
                opcioncomida = Main.sc.nextInt();
                Main.sc.nextLine(); // limpiar buffer
            } else {
                System.out.println(" Ingrese un número válido (1-3).");
                Main.sc.next();
                i--; // repetir el mismo índice
                continue;
            }

            String nombrePlato = switch (opcioncomida) {
                case 1 -> "Pizza";
                case 2 -> "Empanadas";
                case 3 -> "Hamburguesa";
                default -> {
                    System.out.println("Opción no válida.");
                    yield "";
                }
            };

            if (!nombrePlato.isEmpty()) platosPedido[i] = nombrePlato;
            else i--; // si fue inválido, repetir
        }

        // === Prioridad ===
        System.out.println("\nIngrese el nivel de prioridad (1-10): ");
        int prioridad = Main.sc.nextInt();

        Pedido nuevoPedido = new Pedido(platosPedido, prioridad);
        nuevoPedido.setEstado(estadoPedido.PENDIENTE);

        Main.registros[Main.contadorPedidos] = nuevoPedido;
        Main.pedidos.add(Main.contadorPedidos, prioridad); // Uso del TDA: PriorityQueueADT

        Main.contadorPedidos++;

        // === Confirmación ===
        System.out.println("\n=== PEDIDO AGREGADO ===");
        System.out.println("Cliente: " + nombreCliente);
        System.out.print("Platos: ");
        for (int i = 0; i < platosPedido.length; i++) {
            System.out.print(platosPedido[i]);
            if (i < platosPedido.length - 1) System.out.print(", ");
        }
        System.out.println("\nPRIORIDAD: " + prioridad);
    }
}



// ====================== CLASE COCINA ======================                       COLA===================

class Cocina { // COLA
    public static void prepararPlatos() {
        System.out.println("=== PREPARACIÓN DE PLATOS ===");

        if (Main.pedidos.isEmpty()) {
            System.out.println("No hay pedidos para preparar.");
            return;
        }

        PriorityQueueADT copiaPedidos = Utilidades.copiarColaPrioridad(Main.pedidos);

        while (!Main.platos.isEmpty()) Main.platos.remove(); // limpiar cola

        while (!copiaPedidos.isEmpty()) {
            int pedido = (int) copiaPedidos.getElement();
            int prioridad = (int) copiaPedidos.getPriority();
            Pedido pedidoData = Main.registros[pedido];

            Main.platos.add(pedido);

            //  Mostrar todos los platos y el estado actual
            System.out.print("Pedido ");
            for (int i = 0; i < pedidoData.platos.length; i++) {
                System.out.print(pedidoData.platos[i]);
                if (i < pedidoData.platos.length - 1) System.out.print(", ");
            }

            //  Mostrar prioridad y estado en la misma línea
            System.out.println(" | Prioridad: " + prioridad + " | Estado: " + pedidoData.getEstado());

            copiaPedidos.remove();
        }

        System.out.println("=== Pedidos enviados a la cocina ===");

        QueueADT copiaPlatos = Utilidades.copiarCola(Main.platos);
        Utilidades.pasarColaAPila(copiaPlatos);

        Main.pedidos = Utilidades.limpiarColaFinalizados(Main.pedidos);
    }
}



// ====================== CLASE ENTREGAS ===================================================== GRAFO ==============

class Entregas { // GRAFO
    public static void entregas() {
        System.out.println("=== ENTREGA DE PEDIDOS ===");

        if (Main.Analisis.isEmpty()) {
            System.out.println("No hay pedidos en la pila de entregas.");
            return;
        }

        int id = (int) Main.Analisis.getElement();  // LIFO TOMA EL ULTIMO ELEMENTO
        Main.Analisis.remove(); // SACA EL ELEMENTO

        Pedido p = Main.registros[id];

        // --- GRAFO DE ENTREGAS ---
        Grafo grafo = new Grafo(4); // UBICACIONES
        grafo.agregarNodo(0, "Cocina Central");
        grafo.agregarNodo(1, "Grito del Corto 3028");
        grafo.agregarNodo(2, "Hipólito Yrigoyen 962");
        grafo.agregarNodo(3, "Plaza Principal");

        grafo.agregarConexion(0, 1, 7);
        grafo.agregarConexion(0, 2, 12);
        grafo.agregarConexion(0, 3, 6);
        grafo.agregarConexion(1, 3, 8);

        int destinoIndex = switch (id % 3) {
            case 0 -> 1;
            case 1 -> 2;
            default -> 3;
        };

        String origen = grafo.getNodo(0);
        String destino = grafo.getNodo(destinoIndex);
        int distancia = grafo.getDistancia(0, destinoIndex);

        System.out.println("Ruta: " + origen + " → " + destino + " (" + distancia + " min)");
        p.setEstado(estadoPedido.FINALIZADO);
        System.out.print(" Pedido entregado: ");


        for (int i = 0; i < p.platos.length; i++) { // RECORRE LA LISTA
            System.out.print(p.platos[i]);
            if (i < p.platos.length - 1) System.out.print(", ");
        }
        System.out.println(" | Estado: " + p.getEstado());


        Main.pedidos = Utilidades.limpiarColaFinalizados(Main.pedidos);
    }
}


// ====================== CLASE REPORTES Y ANALISIS ===============================SIMPLE DICTIONARY==================

class ReportesYAnalisis { // SIMPLE DICTIONARY
    public static void reportesYAnalisis() {
        System.out.println("=== REPORTES Y ANÁLISIS ===");

        SimpleDictionaryADT dicEstados = new SimpleDictionary();
        SimpleDictionaryADT dicComidas = new SimpleDictionary();

        //  Inicializar claves para evitar null
        dicEstados.add(0, 0); // Pendientes
        dicEstados.add(1, 0); // Finalizados
        dicComidas.add(1, 0); // Pizza
        dicComidas.add(2, 0); // Empanadas
        dicComidas.add(3, 0); // Hamburguesa

        for (int i = 0; i < Main.contadorPedidos; i++) {
            Pedido p = Main.registros[i];
            if (p == null) continue;

            //  Estado del pedido
            int keyEstado = (p.getEstado() == estadoPedido.FINALIZADO) ? 1 : 0;
            int valorEstado = dicEstados.get(keyEstado);
            dicEstados.add(keyEstado, valorEstado + 1);

            //  Conteo de comidas (ahora recorre todos los platos)
            for (int j = 0; j < p.platos.length; j++) {
                int keyComida = switch (p.platos[j]) {
                    case "Pizza" -> 1;
                    case "Empanadas" -> 2;
                    default -> 3;
                };

                int valorComida = dicComidas.get(keyComida);
                dicComidas.add(keyComida, valorComida + 1);
            }
        }

        //  Mostrar resultados
        System.out.println("\nESTADO DE LOS PEDIDOS:");
        System.out.println("Pendientes: " + dicEstados.get(0));
        System.out.println("Finalizados: " + dicEstados.get(1));

        System.out.println("\nCOMIDAS MÁS PEDIDAS:");
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

        System.out.println("\nTotal de pedidos registrados: " + Main.contadorPedidos);
    }
}



// ====================== CLASE UTILIDADES ======================

class Utilidades {
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


// ====================== CLASE GRAFO ======================

class Grafo {
    private final String[] nodos;
    private final int[][] matriz;
    private final int cantidad;

    public Grafo(int cantidad) {
        this.cantidad = cantidad;
        nodos = new String[cantidad];
        matriz = new int[cantidad][cantidad];
    }

    public void agregarNodo(int index, String nombre) {
        nodos[index] = nombre;
    }

    public void agregarConexion(int origen, int destino, int distancia) {
        matriz[origen][destino] = distancia;
        matriz[destino][origen] = distancia;
    }

    public String getNodo(int index) {
        return nodos[index];
    }

    public int getDistancia(int origen, int destino) {
        return matriz[origen][destino];
    }

    public void mostrar() {
        System.out.println("\n=== MATRIZ DE ADYACENCIA ===");
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < cantidad; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
