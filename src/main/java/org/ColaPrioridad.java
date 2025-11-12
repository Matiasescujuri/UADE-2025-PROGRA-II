package org;

public class ColaPrioridad implements PriorityQueueADT {
    private int[] elementos;
    private int[] prioridades;
    private int tamaño;
    private int max;

    public ColaPrioridad(int capacidad) {
        this.max = capacidad;
        this.elementos = new int[capacidad];
        this.prioridades = new int[capacidad];
        this.tamaño = 0;
    }

    @Override
    public void add(int valor, int prioridad) {
        if (tamaño == max) {
            System.out.println(" Cola de prioridad llena");
            return;
        }

        //(mayor prioridad = más arriba)
        int i = tamaño - 1;
        while (i >= 0 && prioridades[i] < prioridad) {
            elementos[i + 1] = elementos[i];
            prioridades[i + 1] = prioridades[i];
            i--;
        }

        // Insertamos el nuevo elemento en la posición correcta
        elementos[i + 1] = valor;
        prioridades[i + 1] = prioridad;
        tamaño++;
    }


    public int getElement() {
        if (isEmpty()) {
            System.out.println("⚠️ Cola vacía");
            return -1;
        }
        return elementos[0]; // el de mayor prioridad siempre está primero
    }


    public int getPriority() {
        if (isEmpty()) {
            return -1;
        }
        return prioridades[0];
    }


    public void remove() {
        if (isEmpty()) {
            System.out.println("⚠️ Cola vacía");
            return;
        }
        for (int i = 0; i < tamaño - 1; i++) {
            elementos[i] = elementos[i + 1];
            prioridades[i] = prioridades[i + 1];
        }
        tamaño--;
    }


    public boolean isEmpty() {
        return tamaño == 0;
    }
}
