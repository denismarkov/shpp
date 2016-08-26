import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Denis on 26.07.2016.
 * Implements of HashMap with use HashTable
 */
public class MyHashMap<K, V> implements Iterable<MyHashMap.Entry> {
    /* Array of entry, use as HashTable */
    private Entry<K, V>[] table;
    /* Table rows count */
    private int size = 16;
    private double loadFactor = 0.75;
    private int threshold;
    /* Number of elements in HashMa */
    private int elementsNumber;

    /* Construct new HashMap */
    public MyHashMap() {
        table = new Entry[size];
        elementsNumber = 0;
        threshold = (int)(size * loadFactor);
    }

    /* Put new element to HashMap if it has not null key, that not contains in HashMap, or change it value, if it is
    already in HashMap
    @param K newKey - element key
    @param V data - new value
     */
    public void put(K newKey, V data) {
        Entry<K, V> newEntry = new Entry<>(newKey, data, null);
        if(size + 1 >= threshold) {
            resize(2 * table.length);
        }
        if (newKey == null) {
            newEntry.next = table[0];
            table[0] = newEntry;
            elementsNumber++;
            return;
        }
        int hash = hash(newKey);
        if (table[hash] == null) {
            table[hash] = newEntry;
            elementsNumber++;
        } else {
            Entry<K, V> previous = null;
            Entry<K, V> current = table[hash];
            while (current != null) {
                if (current.key != null && current.key.equals(newKey)) {
                    if (previous == null) {
                        newEntry.next = current.next;
                        table[hash] = newEntry;

                        return;
                    } else {
                        newEntry.next = current.next;
                        previous.next = newEntry;
                        return;
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
            elementsNumber++;
        }
    }
    /* Resize HashMap table, if more cells in table contains date, then (loadFactor * table size) */
    void resize(int newSize) {
        MyHashMap<K,V> temp = this;
        table = new Entry[newSize];
        threshold = (int)(newSize * loadFactor);
        for(MyHashMap.Entry entry : temp) {
            put((K)entry.getKey(), (V)entry.getValue());
        }
    }

    /* Calculate hash of key
    @param K key
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % size;
    }

    /* Return true if key is already contains in HashMap
    @param K key
    @return boolean
     */
    public boolean containsKey(K key) {
        if (key == null) {
            if (table[0].key == null) {
                return true;
            } else {
                return false;
            }
        }

        int hash = hash(key);
        Entry<K, V> temp = table[hash];
        while (temp != null) {
            if (temp.key != null && temp.key.equals(key))
                return true;
            temp = temp.next;
        }
        return false;
    }

    /* Return element value, if its key contains in HashMap or null if it doesn't
    @param K key
    @return V value
     */
    public V get(K key) {
        if (key == null && table[0].key == null) {
            return table[0].value;
        }
        int hash = hash(key);
        Entry<K, V> temp = table[hash];
        while (temp != null) {
            if (temp.key != null && temp.key.equals(key))
                return temp.value;
            temp = temp.next;
        }
        return null;
    }

    /* If key is in HashMap remove it and return true, or return false, if it isn't
    @param K deleteKey
    @return boolean result of operation
     */
    public boolean remove(K deleteKey) {
        if (deleteKey == null && table[0] != null) {
            if (table[0].key == null) {
                Entry<K, V> current = table[0];
                table[0] = current.next;
                elementsNumber--;
                return true;
            } else {
                return false;
            }
        }
        int hash = hash(deleteKey);
        Entry<K, V> current = table[hash];
        Entry<K, V> previous = null;
        while (current != null) {
            if (current.key.equals(deleteKey)) {
                if (previous == null) {
                    table[hash] = table[hash].next;
                    elementsNumber--;
                    return true;
                } else {
                    previous.next = current.next;
                    elementsNumber--;
                    return true;
                }
            }
            previous = current;
            current = current.next;
        }
        return false;

    }

    /* Return HashMap size */
    public int size() {
        return elementsNumber;
    }

    public Iterator<Entry> iterator() {
        return new MyHashMapIterator();
    }

    /* Container to table element */
    public static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }
    }

    /* Implements Iterator for HashMap */
    public class MyHashMapIterator implements Iterator<Entry> {
        /* Index of HashTable row */
        int mainTableIndex = 0;
        /* Current element of HashMap */
        Entry<K, V> first = getFirst();
        Entry<K, V> current = new Entry<>(null, null, first);

        Entry<K, V> getFirst() {
            Entry<K, V> first = null;
            while (first == null && mainTableIndex < size - 1) {
                first = table[mainTableIndex];
                mainTableIndex++;
            }
            mainTableIndex--;
            return first;
        }

        public Entry next() {
            if (this.hasNext()) {
                if (current.next != null) {
                    current = current.next;
                    return current;
                } else {
                    Entry temp = null;
                    while (temp == null && mainTableIndex < size - 1) {
                        mainTableIndex++;
                        temp = table[mainTableIndex];
                    }
                    current = temp;
                    return temp;
                }
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            if (current != null && current.next != null) {
                return true;
            } else {
                Entry temp = null;
                int i = mainTableIndex;
                while (temp == null && i < size - 1) {
                    temp = table[++i];
                }
                if (temp != null) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
