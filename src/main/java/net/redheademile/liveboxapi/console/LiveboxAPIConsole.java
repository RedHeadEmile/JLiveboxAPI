package net.redheademile.liveboxapi.console;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.console.commands.ILiveboxAPICommand;
import net.redheademile.liveboxapi.console.commands.LiveboxAPIDeletePortForwardingCommand;
import net.redheademile.liveboxapi.console.commands.LiveboxAPIGetPortForwardingCommand;
import net.redheademile.liveboxapi.console.commands.LiveboxAPISetPortForwardingCommand;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class LiveboxAPIConsole {
    public static <T> T getInputFromConsole(Scanner in, String name, Class<T> type, T defaultValue) {
        List<String> boolTrueValues = List.of("yes", "oui", "true", "y", "1");
        List<String> boolFalseValues = List.of("no", "non", "false", "n", "0");

        while (true) {
            System.out.print(name);
            if (defaultValue != null)
                System.out.print(" (" + defaultValue + ")");
            System.out.print(": ");
            String input = in.nextLine();

            if (input == null) throw new RuntimeException();
            if (input.isBlank() && defaultValue != null) return defaultValue;
            if (input.isBlank()) continue;

            if (type == String.class) return (T) input;

            else if (type == boolean.class || type == Boolean.class) {
                input = input.toLowerCase();
                if (boolTrueValues.contains(input)) return (T) Boolean.TRUE;
                if (boolFalseValues.contains(input)) return (T) Boolean.FALSE;
            }

            else if (type == int.class || type == Integer.class) {
                try {
                    Integer i = Integer.parseInt(input);
                    return (T) i;
                }
                catch (NumberFormatException e) {}
            }

            else throw new UnsupportedOperationException("Unsupported type");
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        Scanner in = new Scanner(System.in);

        String password = args.length > 0 ? args[0] : null;
        if (password == null) {
            System.out.print("Enter the Livebox password: ");
            password = in.nextLine();
        }

        LiveboxAPI api = new LiveboxAPI(password);

        ILiveboxAPICommand[] commands = new ILiveboxAPICommand[] {
                new LiveboxAPIDeletePortForwardingCommand(),
                new LiveboxAPIGetPortForwardingCommand(),
                new LiveboxAPISetPortForwardingCommand(),
        };

        List<String> allCommandAndAliases = new ArrayList<>();
        for (ILiveboxAPICommand command : commands)
            for (String nameOrAliases : command.getNameAndAliases()) {
                if (allCommandAndAliases.contains(nameOrAliases.toLowerCase()))
                    throw new IllegalStateException("Two commands with same name or alias.");
                allCommandAndAliases.add(nameOrAliases.toLowerCase());
            }

        while (true) {
            System.out.print("Type de command ('?' for help): ");
            String inputCommand = in.nextLine();

            if (inputCommand == null) break;
            if (inputCommand.isBlank()) continue;

            for (ILiveboxAPICommand command : commands)
                for (String nameOrAliases : command.getNameAndAliases())
                    if (nameOrAliases.equalsIgnoreCase(inputCommand)) {
                        try {
                            command.execute(api);
                        }
                        catch (Exception e) { e.printStackTrace(); }
                        break;
                    }

            System.out.print("Unknown command, commands: ");
            System.out.println(String.join(", ", Stream.of(commands).map(c -> c.getNameAndAliases()[0]).toList()));
        }
    }
}
