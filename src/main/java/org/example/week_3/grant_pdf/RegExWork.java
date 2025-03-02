package org.example.week_3.grant_pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExWork {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^[B6][B\\d]\\d{2} - [\\p{L}, ]+$");
        Pattern p = Pattern.compile("^[B6][B\\d]\\d{2}");

        Pattern stud = Pattern.compile("\\d{1,3} \\d{9} (\\p{L}+(?: \\p{L}+)*) \\d{1,3} \\d{3}");

        String text1 = "6B03 - Әлеуметтік ғылымдар, журналистика және ақпарат ";
        String text2 = "B003 - Бастауышта оқыту педагогикасы мен әдістемесі ";
        String text3 = "ksj n nfjk,nrfjkm,lfkd";

        String t = "89 Басым құқық: ТОтб Аттестаттың (дипломның) орташа баллы: 4,571 Бейіндік пәндер балдарының сомасы: 46 Ауыл квотасы конкурсы бойынша өту параметрлері Сертификаттың балл сомасы: 79 Басым құқық: - Аттестаттың (дипломның) орташа баллы: 4,143 Бейіндік пәндер балдарының сомасы: 40  № ТЖК Тегі, Аты, Әкесінің аты Балл жиынтығы ЖОО ЖАЛПЫ КОНКУРС 1 001175243 ХАЙРУЛЛИНА ДИЛЬНАЗ ЕРБОЛАТОВНА 137 013 2 001174974 САДУОВА КАМИЛА ҒАНИҚЫЗЫ 134 302 3 001031724 ЕРБОЛАТОВА АКБОТА ТАЛГАТОВНА 127 013";

        Matcher mt = stud.matcher(t);
        List<String> separators = new ArrayList<>();

        while (mt.find()) {
            separators.add(mt.group());
        }

        System.out.println("Разделители: " + separators);

//        Matcher m1 = pattern.matcher(text1);
//        Matcher m2 = pattern.matcher(text2);
//        Matcher m3 = pattern.matcher(text3);
//
//        System.out.println(m1.matches()); // false (потому что `6B03` некорректно)
//        System.out.println(m2.matches()); // true
//        System.out.println(m3.matches()); // false

        //System.out.println(p.matcher(text1).find()); // true (text1 начинается с "6B03")
    }
}
