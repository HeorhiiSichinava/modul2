package com.alevel.modul2;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    //////////////////////////////////////////////////////////////////////////////////

/*    public void writeByte(String fileName, byte byteCode) {
        List<Integer> byteList = new ArrayList<>();
        try {
            File fDoc = new File(fileName);
            new FileOutputStream(fDoc).write(byteCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return;
    }

*/    public void writeByte(String fileName, byte[] byteCode) {
        List<Integer> byteList = new ArrayList<>();
        try {
            File fDoc = new File(fileName);
            FileOutputStream fWrite = new FileOutputStream(fDoc);
            fWrite.write(byteCode, 0, byteCode.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return;
    }
/*
    //////////////////////////////////////////////////////////////////////////////////
    public List<Integer> readFileByteToList(String fileName) throws FileNotFoundException {
        List<Integer> byteList = new ArrayList<>();
        try {
            File file = new File(fileName);
            ByteBuffer bf = null;
            
            try (InputStream readFile = new FileInputStream(fileName)) {
                int read;
                while ((read = readFile.read()) != -1) {
                    byteList.add(read);
//                    System.out.println(readFile.read());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteList;
    }

    public void update(String nameFile, byte newByte) throws FileNotFoundException {
        exists(nameFile);
        int size = readFileByteToList(nameFile).size();
        byte[] b = new byte[size];
        for (int i = 0; i <b.length-1 ; i++) {
            b[i]=readFileByteToList(nameFile).get(i).byteValue();
        }
        b[b.length]=newByte;
        writeByte(nameFile,b);
    }
*/
    //////////////////////////////////////////////////////////////////////////////////
    public void write(String fileName, String text) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Where my fuckin' file?");
                }
            }
            try (FileOutputStream out = new FileOutputStream(file.getAbsoluteFile())) {
                out.write(text.getBytes());
            }
        } catch (IOException ololo) {
            throw new RuntimeException(ololo);
        }
    }

    /*    public static void addWrite(PrintWriter out,String text) {
            out.print(text);
        }*/
    //////////////////////////////////////////////////////////////////////////////////
    public String read(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        exists(fileName);
        try {
            File file = new File(fileName);
            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    //////////////////////////////////////////////////////////////////////////////////
    public void update(String nameFile, String newText) throws FileNotFoundException {
        exists(nameFile);
        StringBuilder sb = new StringBuilder();
        String oldFile = read(nameFile);
        sb.append(oldFile);
        sb.append(newText);
        write(nameFile, sb.toString());
    }

    private void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) throw new FileNotFoundException(file.getName());
    }
}

