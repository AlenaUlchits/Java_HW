package ru.geekbrains.lesson5;

import java.io.File;

public class Tree {

    public static void main(String[] args) {
        print(new File("."), "", true);
    }

    static void print(File file, String indent, boolean isLast){
        System.out.print(indent);
        if (isLast){
            System.out.print("└─");
            indent += "  ";
        }
        else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if (files == null)
            return;

        int subDirTotal = 0;
        int fileCounter = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                subDirTotal++;
            } else {
                fileCounter++;
            }
        }

        int subDirCounter = 0;
        int fileCounter2 = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                print(f, indent + (subDirTotal == ++subDirCounter && fileCounter == 0 ? "  " : "│ "), subDirTotal == subDirCounter && fileCounter == 0);
            } else {
                fileCounter2++;
                System.out.print(indent + (fileCounter2 == fileCounter ? "└─" : "├─") + f.getName());
                System.out.println();
            }
        }
    }
}
