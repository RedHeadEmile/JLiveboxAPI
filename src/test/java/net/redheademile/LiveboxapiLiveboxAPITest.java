package net.redheademile;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.exceptions.LiveboxException;
import net.redheademile.liveboxapi.utils.ProtocolNumber;

import java.net.URISyntaxException;
import java.util.List;

public class LiveboxapiLiveboxAPITest {

    private static String fillWithCharacter(String text, int length, String filler) {
        if (text.length() > length)
            throw new IllegalArgumentException("Text must be shorter then length");

        return text + filler.repeat(length - text.length());
    }

    private static void tableDisplay(String[] columnNames, List<String[]> rows) {
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

    public static void main(String[] args) throws URISyntaxException, LiveboxException {
        if (args.length < 1) {
            System.out.println("Please enter the Livebox password as first argument");
            return;
        }

        LiveboxAPI liveboxAPI = new LiveboxAPI(args[0]);

        System.out.println("List of forwarded port:");

        String[] columnNames = new String[] { "Id", "Enabled", "Internal Port", "External Port", "Protocol", "Dest IP Addr" };
        List<String[]> rows = liveboxAPI.getPortForwarding().status.values().stream().map(rule ->
                new String[] {
                        rule.Id,
                        rule.Enable ? "Y" : "N",
                        rule.InternalPort,
                        rule.ExternalPort,
                        ProtocolNumber.formatRawNumbers(rule.Protocol),
                        rule.DestinationIPAddress
                }).toList();

        tableDisplay(columnNames, rows);
    }
}
