package org;

public class Pila implements StackADT {

        private int[] elementos; // Guarda los elementos de la pila
        private int tope;// Indica la posición del último elemento agregado



        public Pila(){
            elementos = new int [10];
            tope = -1;

        }
        public Pila(int capacidad){
            elementos = new int [capacidad];
            tope = -1;

        }
        public void add(int valor) {
            if (tope < elementos.length - 1) {
                elementos[++tope] = valor; // incremento el tope y lo guardo
            } else {
                System.out.println(" La pila está llena.");
            }
        }

        // Elimina el último elemento
        public void remove() {
            if (!isEmpty()) {
                tope--; // simplemente bajo el índice
            } else {
                System.out.println(" No hay elementos para eliminar.");
            }
        }

        // Devuelve el elemento de arriba sin eliminarlo

        public int getElement() {
            if (!isEmpty()) {
                return elementos[tope];
            } else {
                System.out.println(" La pila está vacía.");
                return -1;
            }
        }

        // Verifica si la pila está vacía

        public boolean isEmpty() {
            return tope == -1;
        }

        }










