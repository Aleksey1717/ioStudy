package com.pshela.iostydy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFileAnalyzer {
    public static void main() throws IOException {
        //для проверки создадим файл с текстом
        String path = "C:/test/story.txt";
        File file = new File(path);
        //  если его нет
        if (!file.exists()) {
            // создаём папку C:/test
            file.getParentFile().mkdirs();
            // создаём файл story.txt
            file.createNewFile();
        }
        String text =
                "The duck is walking near the lake.\n" +
                            "I saw a yellow duck yesterday.\n" +
                            "The duck looked at another duck and started running!\n" +
                            "Sometimes birds can be very loud.\n" +
                            "I like animals that live near water.";

            // записываем текст в файл`
            FileOutputStream fileOutput = new FileOutputStream(file);
            fileOutput.write(text.getBytes());
            // закрываем поток
            fileOutput.close();

        // тестируем
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        fileAnalyzer.analyze(path, "duck");

    }//void main
}//class Test
