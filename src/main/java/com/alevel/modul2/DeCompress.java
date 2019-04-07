package com.alevel.modul2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DeCompress {
    private String path;
    private String nameFile;
    //    private Map<Character, Integer> tableSymbol = new HashMap<>();
    private Map<String, Character> tableBinaryCode = new HashMap<>();

    public void path(String path, String nameFile) {
        if (nameFile.contains(".hf")) {
            this.path = path;
            this.nameFile = nameFile.substring(0, nameFile.length() - 3);
            try {
                readFileHT();
                writeDeCompressFile();
            } catch (Exception ex) {
                System.out.println("Error: not file found!");
            }
        } else System.out.println("Error: this file not huffmanFile (.hf) ");
    }

    private void readFileHT() {
        File fileHT = new File(getPath() + getNameFile() + ".ht");
        try (FileInputStream fin = new FileInputStream(fileHT)) {
//            System.out.printf("File size: %d bytes \n", fin.available());

            int[] readBuff = new int[2];
            readBuff[0]=-1;
            readBuff[1]=-1;

            String mapKey = "";
            Character mapVal = 0;
            String buff = "";
            while (true) {
                readBuff[0] = fin.read();
                if (readBuff[0] == -1) {
                    break;
                } else {
                    if ((char) readBuff[0] != ' ') {
                        buff += "" + (char) readBuff[0];
                    } else {
                        readBuff[1]=fin.read();
                        if ((char) readBuff[1]==' '){
                            buff+=""+(char)readBuff[0];
                        }
                        mapKey = buff.substring(0, buff.length() - 1);
                        mapVal = buff.charAt(buff.length() - 1);
                        tableBinaryCode.put(mapKey, mapVal);
                        buff = "";
                        if (readBuff[1]!=' '){
                            buff+=(char)readBuff[1];
                        }
                    }
                }
            }
//            convertMapCharacterIntegerToMapIntegerCharacter(tableSymbol);

            System.out.println(tableBinaryCode);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void writeDeCompressFile() {
        File fileDeCompress = new File(getPath() + "_" + getNameFile());
        File fileHF = new File(getPath() + getNameFile() + ".hf");
        FileUtils fileUtils = new FileUtils();
        fileUtils.write(fileDeCompress.getAbsolutePath(), "");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileHF), "UTF8"))) {
            int byteCodeHuff = 0;
            String stepTree = "";

            while ((byteCodeHuff = in.read()) != -1) {
//                System.out.println((char) byteCodeHuff);
                for (int i = 0; i < 8; i++) {
                    stepTree += byteCodeFixZero(8, byteCodeHuff).charAt(i);
                    if (tableBinaryCode.containsKey(stepTree)) {
                        fileUtils.update(fileDeCompress.getAbsolutePath(), tableBinaryCode.get(stepTree).toString());
                        stepTree = "";
                    }
                }
            }
//            convertMapCharacterIntegerToMapIntegerCharacter(tableSymbol);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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

    public String getPath() {
        return path;
    }

    public String getNameFile() {
        return nameFile;
    }
}
