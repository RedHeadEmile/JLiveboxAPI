package net.redheademile.liveboxapi.console;

import java.util.List;

public class TableUtils {
    private static String fillWithCharacter(String text, int length, String filler) {
        if (text.length() > length)
            throw new IllegalArgumentException("Text must be shorter then length");

        return text + filler.repeat(length - text.length());
    }

    public static void tableDisplay(String[] columnNames, List<String[]> rows) {
        int columnAmount = columnNames.length;
        int[] maxColumnLength = new int[columnAmount];

        for (int i = 0; i < columnAmount; i++)
            maxColumnLength[i] = columnNames[i].length();

        for (String[] row : rows) {
            if (row.length != columnAmount)
                throw new IllegalArgumentException("A row does not contains the right amount of columns.");

            for (int i = 0; i < columnAmount; i++) {
                if (row[i].length() > maxColumnLength[i])
                    maxColumnLength[i] = row[i].length();
            }
        }

        String firstLine = "";
        String spacer = "";
        for (int i = 0; i < columnAmount; i++) {
            firstLine += "| " + fillWithCharacter(columnNames[i], maxColumnLength[i], " ") + " ";
            spacer += "--" + fillWithCharacter("", maxColumnLength[i], "-") + "-";
        }
        spacer += "--";

        System.out.println(spacer);
        System.out.println(firstLine + " |");
        System.out.println(spacer);

        for (String[] row : rows) {
            for (int i = 0; i < columnAmount; i++)
                System.out.print("| " + fillWithCharacter(row[i], maxColumnLength[i], " ") + " ");
            System.out.println(" |");
        }

        System.out.println(spacer);
    }
}
