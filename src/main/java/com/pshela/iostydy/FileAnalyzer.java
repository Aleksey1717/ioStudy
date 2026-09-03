package com.pshela.iostydy;
/*Используем классы FileInputStream, FileOutputStream, File
Практика:
1: Написать программу FileAnalyzer, которая в консоли принимает 2 параметра:
1) путь к файлу
2) слово
Usage:
java FileAnalyzer C:/test/story.txt duck

Выводит:
1) Кол-во вхождений искомого слова в файле
2) Все предложения содержащие искомое слово(предложение заканчивается символами ".", "?", "!").
Каждое предложение выводится с новой строки.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {

    public void analyze (String path, String word) throws IOException{
        //создаем объект файла
        File file= new File(path);
        //проверяем наличие файла
        if(!file.exists()){
            System.out.println("Файл не найден: " + path);
           return;
        }
        // открываем для чтения
        InputStream fileStrim = new FileInputStream(file);
        // читаем весь файл
        byte [] data= fileStrim.readAllBytes();
        // закрываем поток
        fileStrim.close();
        //переводим байты в строку
        String text =new String(data);

        //1) Кол-во вхождений искомого слова в файле
        int count =countWord (text, word);
        System.out.println("Общее количество слова \"" + word + "\": " + count);
        //2) Все предложения содержащие искомое слово(предложение заканчивается символами ".", "?", "!").
        //Каждое предложение выводится с новой строки
        List<String> sentences = findSentences(text, word);

        System.out.println("\nПредложения содержащие слово \""+word+ "\": ");
        for (String sentenc : sentences) {
            //вывод в консоль
            System.out.println(sentenc);
        }


    }//void analyze

    // Подсчёт количества вхождений слова
    private int countWord(String text, String word){
        int count = 0;
        // pазбиваем текст на слова по пробелам и знакам
        String[] parts = text.split("[\\s.,!?;:()\"]+");

        for (String part : parts) {
            if (part.equals(word)) {
                count++;
            }
        }
        return count;
    }//countWord

    // Найти предложений, содержащих слово
    private List<String> findSentences(String text, String word){
        // ANSI-коды для цвета
        String RED = "\u001B[31m";   // красный цвет
        String RESET = "\u001B[0m";  // сброс цвета
        //список, куда будем складывать найденные предложения
        List<String> result =new ArrayList<>();
        // pазбиваем текст на предложения.выражение (?<=[.!?])
        String[] parts = text.split("(?<=[.!?])");
        // перебираем каждое предложение
        for (String sentence : parts) {

            // проверяем содержит ли предложение искомое слово
            // если да  добавляем его в результат
            if (sentence.contains(word)) {
                // выделяем слово цветом
                String coloredSentence = sentence.replace(
                        word, RED + word + RESET);
                // // trim() убирает пробелы в начале и конце предложения
                // "\n" добавляет перенос строки, чтобы вывод был с новой строки
                result.add(coloredSentence.trim());
            }
        }
        return result;
    }//findSentences


}//class FileAnalyzer
