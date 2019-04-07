package com.alevel.modul2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Compress {
    private String path;
    private String nameFile;
    private Map<Character, String> tableSymbol = new HashMap<>();

    private HuffmanNode treeNode(int count, int step, HuffmanNode huffmanNode) {
//        System.out.println(count + ":" + step + ":" + huffmanNode);
        FileUtils fileUtils = new FileUtils();
        if (huffmanNode.getSymbol() != null) {
            try {
                tableSymbol.put(huffmanNode.getSymbol(), byteCodeFixZero(count, step));
//                System.out.println(tableSymbol);
                fileUtils.update(getPath() + getNameFile() + ".ht", byteCodeFixZero(count, step) + huffmanNode.getSymbol()+" ");
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        } else {
            count++;
            step <<= 1;
            treeNode(count, step + 0b1, huffmanNode.lLeaf);
            treeNode(count, step + 0b0, huffmanNode.rLeaf);
        }
//        fileUtils.write(getPath()+getNameFile()+".ht",tableSymbol.entrySet().toString());
        return huffmanNode;
    }

    public static String byteCodeFixZero(int length, int byteCode) {
        String strByte = "";
        if (Integer.toBinaryString(byteCode).length() < length) {
            for (int i = 0; i < length - Integer.toBinaryString(byteCode).length(); i++) {
                strByte += "0";
            }
        }
        return strByte + Integer.toBinaryString(byteCode);
    }

    public void path(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;

        FileUtils fileUtils = new FileUtils();
        fileUtils.write(path + nameFile + ".ht", "");
        fileUtils.write(path + nameFile + ".hf", "");
        String str = "";
        try (FileInputStream fin = new FileInputStream(getPath() + getNameFile())) {
            System.out.printf("File size: %d bytes \n", fin.available());

            int i = -1;
            while ((i = fin.read()) != -1) {
                str += (char) i;
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        readFile(str);
        writeCompressFile();
    }

    private void writeCompressFile() {
        File fileCompress = new File(getPath() + getNameFile() + ".hf");
        FileUtils fileUtils = new FileUtils();

        try (FileInputStream fin = new FileInputStream(getPath() + getNameFile())) {
//            System.out.printf("File size: %d bytes \n", fin.available());
            Character byteCodeCompress = 0;
            int i = -1;
            String buffByteCod = "";
            char addByte = 0;
            char buffByte = 0;
            while (true) {
                i = fin.read();
                if (i != -1) {
                    buffByteCod += tableSymbol.get((char) i);
                    if (buffByteCod.length() >= 8) {
                        addByte = byteToSymbol(buffByteCod.substring(0, 8));
                        fileUtils.update(fileCompress.getAbsolutePath(), String.valueOf(addByte));
                        if (buffByteCod.length() - 8 > 0)
                            buffByte = byteToSymbol(buffByteCod.substring(9));
                        else
                            buffByte = 0;
                        buffByteCod = buffByteCod.substring(8);
                    }
                } else {
                    if (buffByte > 0) {
                        fileUtils.update(fileCompress.getAbsolutePath(), buffByteCod);
                    }
                    break;
                }
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    private char byteToSymbol(String str) {
        short byteCode = 0b0;
        for (Character ch : str.toCharArray()) {
            byteCode <<= 1;
            byteCode += (short) Integer.parseInt(ch.toString());
        }
        return (char) byteCode;
    }

    private void readFile(String s) {
//        System.out.println(s);
        treeNode(0, 0b0000_0000, nodePQueueSymbol(counterUniqueSymbol(s)));
    }

    private HuffmanNode nodePQueueSymbol(Map<Character, Integer> mapSymbol) {
        PriorityQueue<HuffmanNode> prioritySymbol = new PriorityQueue();
        HuffmanNode hN = null;
        for (Map.Entry getSymbolAndWeight : mapSymbol.entrySet()) {
            Object ch = getSymbolAndWeight.getKey();
            Object in = getSymbolAndWeight.getValue();
            /*System.out.println("(" + ch + ":" + in + "), ");*/
            hN = new HuffmanNode((Character) ch, (Integer) in);
            prioritySymbol.offer(hN);
        }
//        System.out.println(prioritySymbol);

        HuffmanNode lHuffNode, rHuffNode;
        while (prioritySymbol.size() > 1) {
            lHuffNode = prioritySymbol.poll();
            rHuffNode = prioritySymbol.poll();
            prioritySymbol.offer(new HuffmanNode(lHuffNode, rHuffNode));
        }
//        System.out.println(prioritySymbol.peek());

        return prioritySymbol.poll();
    }

    private Map<Character, Integer> counterUniqueSymbol(String str) {
        Map<Character, Integer> mapSymbol = new HashMap<Character, Integer>();
        for (Character addSymbol : str.toCharArray()) {
            if (mapSymbol.containsKey(addSymbol)) {
/*                if (addSymbol.toString().contains("\n")) {
                    mapSymbol.put('#', mapSymbol.get(addSymbol) + 1);
                } else*/
                mapSymbol.put(addSymbol, mapSymbol.get(addSymbol) + 1);
            } else {
/*                if (addSymbol.toString().contains("\n")) {
                    mapSymbol.put('#', 1);
                } else*/
                mapSymbol.put(addSymbol, 1);
            }
        }
//        System.out.println(mapSymbol);
        return mapSymbol;
    }

    public String getPath() {
        return path;
    }

    public String getNameFile() {
        return nameFile;
    }
}