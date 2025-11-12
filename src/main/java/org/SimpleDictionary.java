package org;

public class SimpleDictionary implements SimpleDictionaryADT {
    private int[] keys;
    private int[] values;
    private int count;

    public SimpleDictionary() {
        keys = new int[50];
        values = new int[50];
        count = 0;
    }

    @Override
    public SetADT getKeys() {
        SetADT set = new SetArray();
        for (int i = 0; i < count; i++) {
            set.add(keys[i]);
        }
        return set;
    }

    @Override
    public int get(int key) {
        for (int i = 0; i < count; i++) {
            if (keys[i] == key)
                return values[i];
        }
        return 0; // valor por defecto si no existe
    }

    @Override
    public void add(int key, int value) {
        for (int i = 0; i < count; i++) {
            if (keys[i] == key) {
                values[i] = value;
                return;
            }
        }
        if (count == keys.length)
            expandir();

        keys[count] = key;
        values[count] = value;
        count++;
    }

    private void expandir() {
        int[] newKeys = new int[keys.length * 2];
        int[] newValues = new int[values.length * 2];
        for (int i = 0; i < keys.length; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys;
        values = newValues;
    }

    @Override
    public void remove(int key) {
        for (int i = 0; i < count; i++) {
            if (keys[i] == key) {
                keys[i] = keys[count - 1];
                values[i] = values[count - 1];
                count--;
                return;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
