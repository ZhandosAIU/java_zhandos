package org.example.week_2.L;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;

public class LabReportTemplate {
    public static void main(String[] args) {

        try {
            // Создаём новый Word-документ
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainPart = wordPackage.getMainDocumentPart();

            // Добавляем заголовок (по центру, кастомный шрифт)
            addStyledText(mainPart, "ЛАБОРАТОРНАЯ РАБОТА", "Title", "FF6600", true, 42, "Oswald", JcEnumeration.CENTER);

            // Добавляем подзаголовок (курсив, по центру)
            addStyledText(mainPart, "Введите свой текст здесь Введите свой текст здесь", "Subtitle", "666666", false, 16, "Droid Serif", JcEnumeration.CENTER, true);

            // Добавляем пустое пространство (для картинки)
            addImageToWord(mainPart, "/home/bekarys/IdeaProjects/WorkWord/src/main/java/org/example/test.jpg", 500, 500);

            // Добавляем подпись (по центру)
            addStyledText(mainPart, "Ваше имя", "Subtitle", "8B4513", true, 18, "Droid Serif", JcEnumeration.CENTER);
            addStyledText(mainPart, "04.09.20XX", "Subtitle", "8B4513", false, 16, "Droid Serif", JcEnumeration.CENTER);
            addStyledText(mainPart, "КЛАСС, ДИСЦИПЛИНА", "Subtitle", "8B4513", false, 16, "Droid Serif", JcEnumeration.CENTER);

            // Сохраняем файл
            String filePath = "Lab_Report_Template.docx";
            wordPackage.save(new File(filePath));

            System.out.println("📄 Документ успешно создан: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для добавления текста с настройками (Шрифт + Выравнивание)
    private static void addStyledText(MainDocumentPart mainPart, String text, String style, String color, boolean bold, int fontSize, String font, JcEnumeration alignment) {
        addStyledText(mainPart, text, style, color, bold, fontSize, font, alignment, false);
    }

    private static void addStyledText(MainDocumentPart mainPart, String text, String style, String color, boolean bold, int fontSize, String font, JcEnumeration alignment, boolean italic) {
        P paragraph = new P();
        R run = new R();
        Text t = new Text();
        t.setValue(text);

        RPr runProperties = new RPr();

        // Устанавливаем шрифт
        RFonts rFonts = new RFonts();
        rFonts.setAscii(font);
        rFonts.setHAnsi(font);
        runProperties.setRFonts(rFonts);

        // Устанавливаем размер шрифта
        HpsMeasure size = new HpsMeasure();
        size.setVal(BigInteger.valueOf(fontSize));
        runProperties.setSz(size);

        // Цвет текста
        Color textColor = new Color();
        textColor.setVal(color);
        runProperties.setColor(textColor);

        // Жирный текст
        if (bold) {
            runProperties.setB(new BooleanDefaultTrue());
        }

        // Курсивный текст
        if (italic) {
            runProperties.setI(new BooleanDefaultTrue());
        }

        run.setRPr(runProperties);
        run.getContent().add(t);
        paragraph.getContent().add(run);

        // Выравнивание текста
        PPr paragraphProperties = new PPr();
        Jc justification = new Jc();
        justification.setVal(alignment);
        paragraphProperties.setJc(justification);
        paragraph.setPPr(paragraphProperties);

        mainPart.getContent().add(paragraph);
    }

    private static void addImageToWord(MainDocumentPart mainPart, String imagePath, int width, int height) throws Exception {
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            System.out.println("⚠️ Файл изображения не найден: " + imagePath);
            return;
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart((WordprocessingMLPackage) mainPart.getPackage(), imageBytes);
        Inline inline = imagePart.createImageInline("Image", "Generated", 0, 1, width, height, false);

        P paragraph = new P();
        R run = new R();
        Drawing drawing = new Drawing();
        drawing.getAnchorOrInline().add(inline);
        run.getContent().add(drawing);
        paragraph.getContent().add(run);

        // Центрируем изображение
        PPr paragraphProperties = new PPr();
        Jc justification = new Jc();
        justification.setVal(JcEnumeration.CENTER);
        paragraphProperties.setJc(justification);
        paragraph.setPPr(paragraphProperties);

        mainPart.getContent().add(paragraph);
    }


}
