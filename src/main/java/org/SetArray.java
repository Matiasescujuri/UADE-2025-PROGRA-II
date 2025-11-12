package org;

public class SetArray implements SetADT {
    private int[] elementos;
    private int count;

    public SetArray() {
        elementos = new int[50];
        count = 0;
    }

    @Override
    public void add(int element) {
        if (!contains(element)) {
            if (count == elementos.length) expandir();
            elementos[count++] = element;
        }
    }

    private void expandir() {
        int[] nuevo = new int[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            nuevo[i] = elementos[i];
        }
        elementos = nuevo;
    }

    @Override
    public void remove(int element) {
        for (int i = 0; i < count; i++) {
            if (elementos[i] == element) {
                elementos[i] = elementos[count - 1];
                count--;
                return;
            }
        }
    }

    @Override
    public boolean contains(int element) {
        for (int i = 0; i < count; i++) {
            if (elementos[i] == element) return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int choose() {
        if (isEmpty()) return -1;
        return elementos[(int) (Math.random() * count)];
    }
}
