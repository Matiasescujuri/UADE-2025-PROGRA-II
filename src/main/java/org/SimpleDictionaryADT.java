package org;

public interface SimpleDictionaryADT {
    SetADT getKeys();
    int get(int key);
    void add(int key, int value);
    void remove(int key);
    boolean isEmpty();
}
