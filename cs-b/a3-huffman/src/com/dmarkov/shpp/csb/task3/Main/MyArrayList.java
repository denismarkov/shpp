package com.dmarkov.shpp.csb.task3.Main;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Denis on 26.07.2016.
 * Implements ArrayList, use Object[] array
 */
public class MyArrayList<E> implements Iterable<E> {
    /* Default size of new ArrayLis */
    private static final int DEFAULT_SIZE = 10;
    /* Object array, witch use as ArrayList */
    private E[] storage;
    /* Size of ArrayList */
    private int size;

    /* Constructor, create new ArrayList */
    public MyArrayList() {
        storage = (E[]) new Object[DEFAULT_SIZE];
        size = 0;
    }

    /* Get element at index
    @param int index - index of element
    @return E (user type of Object) element
     */
    public E get(int index) {
        if (index >= 0 && index < size) {
            return (E) storage[index];
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", size " + index);
        }
    }

    /* Add element to end of ArrayList
    @param E object
     */
    public void add(E object) {
        if (storage.length == size) {
            increaseStorageSize();
        }
        storage[size++] = object;
    }

    /* Add element to set location in ArrayList
    @param int index - index in ArrayList to add element
    @param E object
     */
    public void add(int index, E object) {
        if (storage.length == size) {
            increaseStorageSize();
        }
        for (int i = size; i > index; i--) {
            storage[i] = storage[i - 1];
        }
        size++;
        storage[index] = object;
    }

    /* Increase storage size */
    private void increaseStorageSize() {
        int newStorageSize = (int) (storage.length * 1.5);
        storage = Arrays.copyOf(storage, newStorageSize);
    }

    /* Remove element from ArrayList at index
    * @param index
    * */
    public E remove(int index) {
        if (index >= 0 && index < size) {
            E removedElement = (E) storage[index];
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
            return removedElement;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", size " + index);
        }
    }

    /* Replace element in ArrayList at index
    @param index
    @param E object
     */
    public void replace(int index, E object) {
        storage[index] = object;
    }

    /* Return size of ArrayList */
    public int size() {
        return size;
    }

    /* Return true if ArrayList is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    public class MyArrayListIterator implements Iterator<E> {
        /* Index of HashTable row */
        int currentIndex = 0;

        /* Current element of HashMap */
        /* Return next element of HashMap
        @return Entry element
         */
        public E next() {
            E current = storage[currentIndex];
            if (current != null) {
                currentIndex++;
                return current;
            }
            throw new NoSuchElementException();
        }

        /* Return true if HashMap has next element */
        public boolean hasNext() {
            if (currentIndex < size) {
                return true;
            }
            return false;
        }
    }
}
