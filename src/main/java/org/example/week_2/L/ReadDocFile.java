package org.example.week_2.L;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.util.Collection;
import java.util.List;

// Готовый файл Ережеп Бекарыс
public class ReadDocFile {
    public static void main(String[] args) throws Docx4JException {

        String path1 = "/home/bekarys/IdeaProjects/WorkWord/src/main/java/org/example/word_java.docx";
        String file1 = "word_java.docx";

        String path2 = "/home/bekarys/Загрузки/test_java_word_file.docx";
        String file2 = "test_java_word_file.docx";

        File file = new File(path1);
        WordprocessingMLPackage word = WordprocessingMLPackage.load(file);
        MainDocumentPart main = word.getMainDocumentPart();
        List<Object> con = main.getContent();
        int count_P = 0;
        int count_Tbl = 0;
        int count_T = 0;
        for (Object obj: con){
            Object unwrappedObj = org.docx4j.XmlUtils.unwrap(obj); // Распаковка объекта

            if (unwrappedObj instanceof P) {
                count_P++;
                P paragraph = (P) unwrappedObj;

                // Извлекаем текст из параграфа
                String paragraphText = getTextFromParagraph(paragraph);

                count_T += paragraphText.split("[.]").length;

                if (!paragraphText.isEmpty()){
                // System.out.println("✅ Paragraph-" + count_P + ": " + paragraphText);
                }
            } else if (unwrappedObj instanceof Tbl) {
                count_Tbl++;
                // System.out.println("✅ Table-" + count_Tbl);
            }
        }

        // System.out.println("Количество предложении: " + count_T);

        Collection<Part> parts = word.getParts().getParts().values();
        int count_image = 0;
        for (Part part: parts){
            if (part instanceof BinaryPartAbstractImage){
                // BinaryPartAbstractImage image = (BinaryPartAbstractImage) part;
                // byte[] bytes = image.getBytes();
                // System.out.println("✅ Изображение");
                count_image += 1;
            }
        }

        System.out.println("Информация о Word файле: " + file1);
        System.out.println("✅ Количество предложении: " + count_T);
        System.out.println("✅ Количество параграфы: " + count_P);
        System.out.println("✅ Количество таблицы: " + count_Tbl);
        System.out.println("✅ Количество изобрежении: " + count_image);
    }

    private static String getTextFromParagraph(P paragraph){
        StringBuilder sb = new StringBuilder();

        for (Object obj: paragraph.getContent()){
            Object unwrappedObj = XmlUtils.unwrap(obj);
            if (unwrappedObj instanceof R) { // R = Run (фрагмент текста)
                R run = (R) unwrappedObj;
                for (Object runObj : run.getContent()) {
                    Object runUnwrapped = org.docx4j.XmlUtils.unwrap(runObj);
                    if (runUnwrapped instanceof Text) { // Text = сам текст
                        sb.append(((Text) runUnwrapped).getValue());
                    }
                }
            }
        }
        return sb.toString();
    }
}
