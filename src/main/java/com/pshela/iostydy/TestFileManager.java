package com.pshela.iostydy;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TestFileManager {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 - Посчитать файлы");
            System.out.println("2 - Посчитать папки");
            System.out.println("3 - Копировать");
            System.out.println("4 - Перемещение");
            System.out.println("0 - Выход");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> testCountFiles();
                case 2 -> testCountDirs();
                case 3 -> testCopy();
                case 4 -> testMove();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }//void main


    static void testCountFiles() throws IOException {
        String path = "C:\\Program Files\\JetBrains";
        // метод статический -static методы принадлежат классу, а не объекту.
        //Количество файлов в папке и всех подпапках по пути
        int totalFile=FileManager.countFiles(path);
        System.out.println("Количество файлов в папке :"+path+" и всех подпапках по пути :"+totalFile);
    }//testCountFiles()
    static void testCountDirs() throws IOException {
        String path = "C:\\Program Files\\JetBrains";
        // количество папок в папке и всех подпапках по пути
        int totalDir = FileManager.countDirs(path);
        System.out.println("Количество папок в папке :"+path+" и всех подпапках по пути :" + totalDir);
    }
    //метод по копированию папок и файлов.
    static void testCopy() throws IOException {
        //создаём тестовую папку C:\test ----------
        String root = "C:\\test2";
        File rootDir = new File(root);
        if (!rootDir.exists()) {
            rootDir.mkdirs(); // создаём папку C:\test2
        }

        // создаём два пустых файла
        File empty1 = new File(root + "\\empty1.txt");
        File empty2 = new File(root + "\\empty2.txt");

        if (!empty1.exists()) empty1.createNewFile();
        if (!empty2.exists()) empty2.createNewFile();

        // создаём подпапку ----------
        File subFolder = new File(root + "\\folder2");
        if (!subFolder.exists()) {
            subFolder.mkdirs(); // создаём C:\test\subfolder
        }

        System.out.println("Тестовая папка создана: " + root);
        // копируем
        String from = "C:\\test2";
        String to = "C:\\testCopy";

        FileManager.copy(from, to);
        System.out.println("Скопировано из папки: " + from + " в папку: " + to);
        // проверяем
        int totalFileCopy = FileManager.countFiles(to);
        System.out.println("Количество файлов в скопированной папке: " + totalFileCopy);

        int totalDirCopy = FileManager.countDirs(to);
        System.out.println("Количество папок в скопированной папке: " + totalDirCopy);
    }

    // метод по перемещению папок и файлов
    static void testMove() throws IOException {
        // создаём тестовую папку C:\testMove ----------
        String root = "C:\\testMove";
        File rootDir = new File(root);
        if (!rootDir.exists()) {
            rootDir.mkdirs(); // создаём папку C:\testMove
        }

        // создаём два файла
        File empty1 = new File(root + "\\empty1.txt");
        File empty2 = new File(root + "\\empty2.txt");

        if (!empty1.exists()) empty1.createNewFile();
        if (!empty2.exists()) empty2.createNewFile();

        // создаём подпапку ----------
        File subFolder = new File(root + "\\folderA");
        if (!subFolder.exists()) {
            subFolder.mkdirs(); // создаём C:\testMove\folderA
        }
        System.out.println("Тестовая папка для перемещения создана: " + root);
        // перемещаем
        String from = "C:\\testMove";
        String to = "C:\\testMoved";
        FileManager.move(from, to);
        System.out.println("Перемещено из папки: " + from + " в папку: " + to);
        // проверяем
        int totalFileMoved = FileManager.countFiles(to);
        System.out.println("Количество файлов в перемещённой папке: " + totalFileMoved);
        int totalDirMoved = FileManager.countDirs(to);
        System.out.println("Количество папок в перемещённой папке: " + totalDirMoved);
    }





}//class TestFileManager
