import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Denis on 18.07.2016.
 * Compress file with Huffman algorithm, write new compressed in same directory
 * and same name with '.compressed' association
 */
class Compress {
    /* Get bytes array from binary file, build Huffman tree, compress table and write new file, that contains
        serialized tree and compressed bitSet in one object ObjectContainer
        @param filePath - String path to original file
        @param compressFile - String path to write compressed file
     */
    static void run(String filePath, String compressFile) throws Exception {
        byte[] bytes = readFile(filePath);
        if(bytes.length < 1) {
            throw new Exception("Attempt to compress empty file");
        }
        MyPriorityQue<Node> que = getFrequency(bytes);
        Node tree = buildTree(que);
        MyHashMap<Byte, String> compressTable = buildCompressTable(tree);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(compressFile));
        BitSet fileBitSet = compressFile(compressTable, bytes);
        // Put Huffman tree and compressed BitSet to one container
        ObjectContainer compressedFileAndTree = new ObjectContainer(tree, fileBitSet);
        // Write compressed file
        oos.writeObject(compressedFileAndTree);
    }

    /*
    Read bytes array from original file and return this byte array
    @param filePath - String path to original file
    @return byte[] data - bytes array of original file
     */
    private static byte[] readFile(String filePath) throws Exception{
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    /*
    Get frequency of bytes in file, create HashMap with bytes and they frequency,
    create Node for each unique byte that contains byte and his frequency,
    add all Nodes to priority Queue, compare their frequency
    @param bytes - byte array of binary file
    @return priority queue que with Nodes
     */
    private static MyPriorityQue<Node> getFrequency(byte[] bytes) {
        MyPriorityQue<Node> que = new MyPriorityQue<>();
        MyHashMap<Byte, Integer> frequency = new MyHashMap<>();
        for (byte b : bytes) {
            frequency.put(b, (frequency.containsKey(b) ? (frequency.get(b) + 1) : 1));
        }
        for (MyHashMap.Entry<Byte, Integer> entry : frequency) {
            que.add(new Node(entry.getValue(), entry.getKey(), null, null));
        }
        return que;
    }

    /*
    Build Huffman tree of Nodes: poll 2 Nodes with less frequency from que,
    put it to new Node and set new Node frequency as sum of frequency children Nodes, put new Node to priority que,
    do it while not get one Node, that contains all other
    @param priority que of Nodes
    @return Huffman tree
     */
    private static Node buildTree(MyPriorityQue<Node> que) {
        while (que.size() > 1) {
            Node child1 = que.poll();
            Node child2 = que.poll();
            Node root = new Node((child1.frequency + child2.frequency), child1, child2);
            que.add(root);
        }
        return que.poll();
    }

    /*
    Build HashMap of bytes from original file and their values in compressed file
    @param Node thee - Huffman tree
    @return - HashMap of bytes from original file and their values in compressed file
     */
    private static MyHashMap<Byte, String> buildCompressTable(Node tree) {
        MyHashMap<Byte, String> compressTable = new MyHashMap<>();
        addTreeElement(compressTable, tree, "");
        return compressTable;
    }

    /*
    Get bytes values in compressed file and put it to compressTable
    @param HashMap compressTable - container to bytes values in compressed file
    @param node - current process Node
    @param String s - string of 1 and 0, that represent byte values in compressed file
     */
    private static void addTreeElement(MyHashMap<Byte, String> compressTable, Node node, String s) {
        if (node.child1 == null && node.child2 == null) {
            compressTable.put(node.key, s);
            return;
        }
        addTreeElement(compressTable, node.child1, s + '0');
        addTreeElement(compressTable, node.child2, s + '1');
    }

    /*
    Create BitSet of compress file, consistently turning over original file bytes array and set compressed byte code to
    BitSet. Set one over bit true for get BitSet length during decompress file.
    @param compressTable - HashMap original bytes and their compress code
    @param bytes - bytes array of original binary file
    @return bitSet - bitSet of compressed file (without tree)
     */
    private static BitSet compressFile(MyHashMap<Byte, String> compressTable, byte[] bytes) {
        BitSet bitSet = new BitSet();
        int bitSetIndex = 0;
        for (int i = 0; i < bytes.length; i++) {
            String compressCod = compressTable.get(bytes[i]);
            for (int j = 0; j < compressCod.length(); j++) {
                if (compressCod.charAt(j) == '1') {
                    bitSet.set(bitSetIndex);
                }
                bitSetIndex++;
            }
        }
        bitSet.set(bitSetIndex);
        return bitSet;
    }

}
