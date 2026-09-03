package com.pshela.iostydy;
/*// public static int countFiles(String path) - принимает путь к папке,
// возвращает количество файлов в папке и всех подпапках по пути
public static int countFiles(String path) {
return 0;
}

// public static int countDirs(String path) - принимает путь к папке,
// возвращает количество папок в папке и всех подпапках по пути
public static int countDirs(String path) {
return 0;
}
}

public static void copy(String from, String to) - метод по копированию папок и файлов.
Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
public static void move(String from, String to) - метод по перемещению папок и файлов.
Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.*/

import java.io.*;
import java.nio.channels.FileChannel;



public class FileManager {
    //принимает путь к папке,
    public static int countFiles(String path) throws IOException {
        File dir=new File(path);
        //проверяем, что путь существует и это папка
        if(!dir.exists()||!dir.isDirectory()){
            return 0;
        }
        //рекурсивный обход папок.
        return countFilesRecursive(dir);
    }//countFiles(String path)


// возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) {
        File dir = new File(path);
        // проверяем, что путь существует и это папка
        if (!dir.exists() || !dir.isDirectory()) {
            return 0;
        }
        // рекурсия
        return countDirsRecursive(dir);
    }
    public static void copy(String from, String to)throws IOException{
        File source =new File(from);
        File pathFolder =new File(to);
        // если источник не существует — копировать нечего
        if (!source.exists()) {
            System.out.println("Источник не найден: " + from);
            return;
        }
        // если целевая папка не существует — создаём
        if (!pathFolder.exists()) {
            pathFolder.mkdirs();
        }

        // если копируем файл
        if (source.isFile()) {
            // создаём путь назначения
            File dest = new File(pathFolder, source.getName());
            // вызываем метод, который принимает строки
            copyFile(source.getAbsolutePath(), dest.getAbsolutePath());
        }

        // если копируем папку
        if (source.isDirectory()) {
            copyDirectory(source, pathFolder);
        }
    }//copy(String from, String to)


    // метод по перемещению папок и файлов
    public static void move(String from, String to) throws IOException {
        // создаём объект File для исходного пути
        File source = new File(from);
        // создаём объект File для папки назначения
        File destFolder = new File(to);
        // если исходный путь — файл
        if (source.isFile()) {
            // создаём файл назначения: папка to + имя файла
            File destFile = new File(destFolder, source.getName());
            // копируем файл
            copyFile(source.getAbsolutePath(), destFile.getAbsolutePath());
            // удаляем исходный файл
            source.delete();
            System.out.println("Файл перемещён: " + source.getAbsolutePath());
            return;
        }
        // если исходный путь — папка
        if (source.isDirectory()) {
            // создаём папку назначения
            File destDir = new File(destFolder, source.getName());
            destDir.mkdirs();
            // копируем папку рекурсивно
            copyDirectory(source, destDir);
            // удаляем исходную папку рекурсивно
            deleteDirectory(source);
            System.out.println("Папка перемещена: " + source.getAbsolutePath());
        }
    }//move(String from, String to)

    // удаление папки рекурсивно
    private static void deleteDirectory(File dir) {
        // получаем список
        File[] files = dir.listFiles();
        // если список не пустой — перебираем элементы
        if (files != null) {
            for (File f : files) {
                // если элемент — папка, вызываем метод снова (рекурсия)
                if (f.isDirectory()) {
                    deleteDirectory(f);
                } else {
                    // если элемент — файл, удаляем его
                    f.delete();
                }
            }
        }
        // после удаления всех файлов и подпапок — удаляем саму папку
        dir.delete();
    }//deleteDirectory(File dir)










    // Копирование папки
    private static void copyDirectory(File source, File destination) throws IOException {
        // если папки не существуетсоздаём её
        if (!destination.exists()) {
            destination.mkdirs();
        }
        // получаем список всех элементов файлы и папки
        String[] files = source.list();
        // если папка пуста или произошла ошибка — просто выходим
        if (files != null) {
            // перебираем каждый элемент внутри исходной папки
            for (String file : files) {
                // создаём объект File для исходного элемента
                File srcFile = new File(source, file);
                // создаём объект File для элемента назначения
                File destFile = new File(destination, file);
                // если элемент  папка, вызываем метод снова рекурсия
                if (srcFile.isDirectory()) {
                    copyDirectory(srcFile, destFile);
                } else {
                    // если элемент — файл, копируем его
                    copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                }
            }
        }
    }//copyDirectory(File source, File destination)

    // копирование файла
    private static void copyFile(String from, String to) throws IOException {
        // try-with-resources: каналы автоматически закроются после выхода из блока try
        try (
                // открываем для чтения исходного файла
                FileChannel in = new FileInputStream(from).getChannel();

                // открываем  для записи в файл
                // FileOutputStream(to).getChannel() создаёт файл, если его нет
                FileChannel out = new FileOutputStream(to).getChannel()
        )
        {
            // копируем данные из in в out начиная с позиции 0
            in.transferTo(0, in.size(), out);
        }
    }//copyFile(String from, String to)


    // рекурсивный метод
    private static int countFilesRecursive(File dir) throws IOException {
        // Счётчик файлов
        int count=0;
        // получаем список всех файлов и папок
        File[] files = dir.listFiles();
        if (files == null) return 0;
        // перебираем каждый элемент внутри папки.
        for (File f : files) {
            // является ли элемент папкой
            if (f.isDirectory()) {
                // если это папка вызываем метод снова
                count += countFilesRecursive(f);
            } else {
                // если это файл увеличиваем счётчик на 1.
                count++;
            }
        }
        // возвращаем количество файлов
        return count;
    }//countFilesRecursive(File dir)

    // рекурсивный метод
    private static int countDirsRecursive(File dir) {
        int count = 0;
        // получаем список всех файлов и папок
        File[] files = dir.listFiles();
        if (files == null) return 0;
        // перебираем каждый элемент
        for (File f : files) {

            // если это папка считаем её и заходим внутрь
            if (f.isDirectory()) {
                count++; // считаем саму папку
                count += countDirsRecursive(f); // считаем её подпапки
            }
        }
        return count;
    }//countDirsRecursive(File dir)







}//class FileManager
