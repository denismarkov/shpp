package com.dmarkov.shpp.csb.task1.Main;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by Denis on 26.07.2016.
 * Implements of Priority Que, use binary heap
 */
public class MyPriorityQue<E extends Comparable<E>> {
    /* Array, which use as storage */
    private E[] storage;
    private int size;

    /* Constructor, create new PriorityQue whith default capacity 10 */
    public MyPriorityQue() {
        storage = (E[]) new Comparable[10];
        size = 0;
    }

    /*Add element to end of deque and sort it with bubbleUp()
     * @param E object
     */
    public void add(E object) {
        if (size + 1 >= storage.length) {
            storage = Arrays.copyOf(storage, storage.length * 2);
        }
        storage[size + 1] = object;
        bubbleUp();
        size++;
    }

    /* If new element has priority, that don't complies with it priority, change it position */
    private void bubbleUp() {
        int index = size + 1;
        while (hasParent(index) && (parent(index).compareTo(storage[index]) > 0)) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    /* Return true, if element has parent
    @param int index of element
     */
    private boolean hasParent(int index) {
        return index > 1;
    }

    /* Return parent of element
    @param int index
    @return E parent
     */
    private E parent(int index) {
        return storage[parentIndex(index)];
    }

    /* Return parent index
    @param index - element index
    @return parent index
     */
    private int parentIndex(int index) {
        return index / 2;
    }

    /* Swap elements
    @param int index1
    @param int index2
     */
    private void swap(int index1, int index2) {
        E tmp = storage[index1];
        storage[index1] = storage[index2];
        storage[index2] = tmp;
    }

    /* Return true, if que is empty
    @return boolean isEmpty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Return peek of que, whithout remove it
    * @return E element
    * */
    public E peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return (E) storage[1];
    }

    /* Return the first element of que and remove it
    @return E element
     */
    public E poll() {
        E result = peek();
        storage[1] = storage[size];
        size--;
        bubbleDown();
        return result;

    }

    /* Move element on tree to the end of que while element don't take it's place */
    private void bubbleDown() {
        int index = 1;
        while (hasLeftChild(index)) {
            int smallerChild = leftIndex(index);
            if (hasRightChild(index) && storage[leftIndex(index)].compareTo(storage[rightIndex(index)]) > 0) {
                smallerChild = rightIndex(index);
            }
            if (storage[index].compareTo(storage[smallerChild]) > 0) {
                swap(index, smallerChild);
            } else {
                break;
            }
            index = smallerChild;
        }
    }

    /* Return left child index
    @param int parent index
    @return int left child index
     */
    private int leftIndex(int i) {
        return i * 2;
    }

    /* Return right child index
    @param int parent index
    @return int right child index
    */
    private int rightIndex(int i) {
        return i * 2 + 1;
    }

    /* Return true, if element has left child
    @param int element index
    @return boolean
     */
    private boolean hasLeftChild(int i) {
        return leftIndex(i) <= size;
    }

    /* Return true, if element has right child
    @param int element index
    @return boolean
    */
    private boolean hasRightChild(int i) {
        return rightIndex(i) <= size;
    }

    public int size() {
        return size;
    }

}



