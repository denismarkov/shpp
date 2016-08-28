package com.dmarkov.shpp.csb.task3.Main;

/**
 * Created by Denis on 26.07.2016.
 * Implements java Deque
 */

public class MyDeque<E> {
    /* Peek of deque */
    DequeNode<E> first;
    /* End of deque */
    DequeNode<E> last;
    /* Size of deque */
    int size;

    /*Add element to end of deque
    * @param E object
    * */
    public void add(E value) {
        if (value == null) {
            throw new IllegalStateException();
        }
        DequeNode<E> current = new DequeNode<>(value);
        if (this.isEmpty()) {
            first = last = current;
            size++;
            return;
        }
        last.right = current;
        current.left = last;
        last = current;
        size++;
    }

    /*Add element to begin of deque
     * @param E object
     */
    public void addFirst(E value) {
        if (value == null) {
            throw new IllegalStateException();
        }
        DequeNode<E> current = new DequeNode<>(value);
        if (this.isEmpty()) {
            first = last = current;
            size++;
            return;
        }
        first.left = current;
        current.right = first;
        first = current;
        size++;
    }

    /* Return the first element of deque with remove it
     * @return E object*/
    public E poll() {
        if (this.isEmpty()) {
            return null;
        }
        DequeNode<E> current = first;
        if (first.right != null) {
            first = first.right;
            first.left = null;
        }
        size--;
        return current.value;
    }

    /* Return true if deque size is 0 */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Return the last element of deque with remove it
    * @return E object*/
    public E pollLast() {
        if (this.isEmpty()) {
            return null;
        }
        DequeNode<E> current = last;
        if (last.left != null) {
            last = last.left;
            last.right = null;
        }
        size--;
        return current.value;
    }

    /* Return the first element of deque without remove it
       @return E object
     */
    public E peek() {
        if (this.isEmpty()) {
            return null;
        }
        return first.value;
    }

    /* Return the last element of deque without remove it
     @return E object
     */
    public E peekLast() {
        if (this.isEmpty()) {
            return null;
        }
        return last.value;
    }

    /* Return deque size */
    public int size() {
        return size;
    }

    /* Container for element of deque, contains value, link to previous and next deque element */
    private class DequeNode<E> {
        E value;
        DequeNode<E> left;
        DequeNode<E> right;

        public DequeNode(E value) {
            this.value = value;
        }
    }
}
