import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by Denis on 18.07.2016.
 * Get container object from compressed file,
 * from container get Huffman tree and compressed BitSet,
 * decode BitSet whit Huffman tree, recover original file byte array
 * and write it to a new file with '.decompressed' prefix in name
 */
public class Decompress {
    public static void expand(String compressedFilePath, String decompressFile) throws Exception {
        ObjectContainer compressedFileAndTree = readFile(compressedFilePath);
        Node tree = compressedFileAndTree.tree;
        BitSet fileStream = compressedFileAndTree.bitSet;
        byte[] bytes = convertFileStreamToByteArray(tree, fileStream);
        FileOutputStream fos = new FileOutputStream(decompressFile);
        fos.write(bytes);
        fos.close();
    }

    /*
    Read Container Object from compressed file
    @param compressedFilePath - path to compressed file
    @return container object with Huffman tree and compressed BitSet
     */
    private static ObjectContainer readFile(String compressedFilePath) throws Exception {
        ObjectContainer file;
        FileInputStream fileIn = new FileInputStream(compressedFilePath);
        /*if(fileIn.available() < 1) {
            throw new Exception("Attempt to decompress empty file");
        }*/
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(fileIn);
        } catch (Exception e){
            throw new Exception("Incorrect input compressed file");
        }
        Object fileObject = ois.readObject();
        if(fileObject instanceof ObjectContainer) {
            file = (ObjectContainer) fileObject;
        } else {
            throw new Exception("Attempt to decompress not correct compressed file");
        }

        return file;
    }

    /*
    Decoding compressed BitSet with Huffman tree and get bytes array of decompressed file
    @param tree - Node Huffman tree
    @param fileStream - compressed BitSet
    @return byte[] of decompressed file
     */
    private static byte[] convertFileStreamToByteArray(Node tree, BitSet fileStream) {
        MyArrayList<Byte> decompressByteArrayList = new MyArrayList<>();
        for (int i = 0; i < fileStream.length() - 1; ) {
            Node temp = tree;
            while (temp.child1 != null && temp.child2 != null) {
                if (fileStream.get(i)) {
                    temp = temp.child2;
                } else {
                    temp = temp.child1;
                }
                i++;
            }
            decompressByteArrayList.add(temp.key);
        }
        return byteArrayListToByteArray(decompressByteArrayList);
    }

    /*
    Convert ArrayList<Byte> to byte[]
    @param decompressByteArrayList - ArrayList<Byte>
    @return byte[] - bytes array
     */
    private static byte[] byteArrayListToByteArray(MyArrayList<Byte> decompressByteArrayList) {
        int length = decompressByteArrayList.size();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = decompressByteArrayList.get(i);
        }
        return bytes;
    }

}
