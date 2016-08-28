package com.dmarkov.shpp.csb.task3.Main;

import java.io.Serializable;
import java.util.BitSet;

/**
 * Created by Denis on 18.07.2016.
 * Object container to right serialization and easy access to Huffman tree and compressed BitSet
 * in compressed file
 */
class ObjectContainer implements Serializable {
    public Node temp;
    Node tree;
    BitSet bitSet;

    ObjectContainer(Node tree, BitSet bitSet) {
        this.tree = tree;
        this.bitSet = bitSet;
    }
}
