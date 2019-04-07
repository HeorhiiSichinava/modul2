package com.alevel.modul2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) {

//            File readFile = new File("C:\\Users\\HEORHII\\Documents\\Text.txt");
        System.out.println();
        try {
            Compress treeBuild = new Compress();
            DeCompress deCompress = new DeCompress();
            treeBuild.path("C:\\Users\\HEORHII\\IdeaProjects\\modul2\\", "Text.txt");
            deCompress.path("C:\\Users\\HEORHII\\IdeaProjects\\modul2\\", "Text.txt.hf");
        }catch (NullPointerException nullEx){
            System.out.println("Error main class: "+nullEx);
        }
    }
}
