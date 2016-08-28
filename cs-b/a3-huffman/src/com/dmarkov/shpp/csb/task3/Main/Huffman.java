package com.dmarkov.shpp.csb.task3.Main;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Denis on 18.07.2016.
 * Read bytes from file set in FILE_PATH,
 * compress file with Huffman algorithm, write new compress file with '.compressed' association
 * read compress file, decoding information from it and write new file in same format, that original file have,
 * and add to filename prefix '.decompressed' before file association information
 */
public class Huffman {


    private static String getDecompressFileName(String path) throws Exception {
        if (!path.contains(".compressed")) {
            throw new Exception("Input file has incorrect type (not compressed)");
        }
        StringBuilder sb = new StringBuilder();
        path = path.substring(0, path.lastIndexOf('.'));
        String title = path.substring(0, path.lastIndexOf('.'));
        sb.append(title);
        sb.append(".decompressed");
        String format = path.substring(path.lastIndexOf('.'));
        sb.append(format);
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            int argsLength = args.length;
            String fileInPath;
            String fileOutPath;
            String action;
            switch (argsLength) {
                case 0:
                    Scanner in = new Scanner(System.in);
                    System.out.print("Input path to file like \"E:\\Huffman.jar\" or \"E:\\Huffman.jar.compressed\": ");
                    fileInPath = in.nextLine();
                    System.out.print("Input path to save new file like \"E:\\\": ");
                    fileOutPath = in.nextLine();
                    System.out.print("Input action (\"compress\" or \"decompress\"): ");
                    action = in.nextLine();
                    break;
                case 3:
                    fileInPath = args[0];
                    fileOutPath = args[1];
                    action = args[2];
                    break;
                default:
                    throw new Exception("Incorrect argument count, you must input path to file, path to save file and action");
            }
            File f = new File(fileInPath);
            if (!f.exists()) {
                throw new Exception("Not valid path to file");
            }
            f = new File(fileOutPath);
            if (!f.isDirectory()) {
                throw new Exception("Not valid path to save file");
            }
            if (!(action.equals("compress") || action.equals("decompress"))) {
                throw new Exception("Not valid action");
            }
            if (action.equals("compress")) {
                fileOutPath += fileInPath.substring(fileInPath.lastIndexOf('\\')) + ".compressed";
                Compress.run(fileInPath, fileOutPath);
            }
            if (action.equals("decompress")) {
                fileOutPath = getDecompressFileName(fileInPath);
                Decompress.expand(fileInPath, fileOutPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

}
