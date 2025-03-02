package org.example.week_3.grant_pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // Путь к pdf файлу где есть список резултата ЕНТ 2022 года
        String filePath = "/home/bekarys/IdeaProjects/WorkWord/src/main/java/org/example/week_3/grant_pdf/grant_list_2022.pdf";

        // Создание объекта File
        File file = new File(filePath);

        // try-catch-resources для открытия и чтения файла pdf
        try (PDDocument pdf = PDDocument.load(file)){
            PDFTextStripper stripper = new PDFTextStripper();

            // Чтение pdf как текст полностью
            String text = stripper.getText(pdf);

            // Запись текст в файл с именем green.txt
            try (FileWriter f = new FileWriter(new File("/home/bekarys/IdeaProjects/WorkWord/src/main/java/org/example/week_3/grant_pdf/green.txt"))){
                // Запись
                f.write(text);
            }
            System.out.println("All Done!");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
