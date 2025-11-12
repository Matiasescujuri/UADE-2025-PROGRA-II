package org;

public class Cola implements QueueADT {

    private int[] elementos;
    private int  indice;  // INDICE DEL PRIMER ELEMENTO
    private int  fin; // INDICE DEL ULTIMO ELEMENTO
    private int  cantidad; // cantidad de elementos de una lista


    public Cola (int capacidad) {
        elementos = new int[capacidad];
        indice = 0;
        fin = 0;
        cantidad = 0;


    }
    public void add(int valor){
        if (cantidad < elementos.length) {
            elementos[fin] = valor;
            fin = (fin + 1) % elementos.length;
            cantidad++;
        }else{
            System.out.println("la cola esta llena");

        }
    }


    public  void remove(){
        if(!isEmpty()) {
            indice = (indice + 1) % elementos.length;
            cantidad--;
        } else {
            System.out.println("cola esta vacia");
        }

    }
    public  int getElement(){
        if (!isEmpty()) {
            return elementos[indice];
        }else {
            System.out.println("cola esta vacia");
            return -1;
        }

    }

    @Override
    public  boolean isEmpty() {
        return cantidad == 0;
    }


}