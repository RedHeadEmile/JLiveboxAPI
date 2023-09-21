package net.redheademile.liveboxapi.console.commands;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.exceptions.LiveboxException;

import java.util.Scanner;

public class LiveboxAPIDeletePortForwardingCommand implements ILiveboxAPICommand {
    @Override
    public String[] getNameAndAliases() {
        return new String[] { "delete-port-forwarding", "delete-pf", "dpf" };
    }

    @Override
    public void execute(LiveboxAPI api, Scanner in, String... args) throws LiveboxException {
        System.out.println();
        System.out.println();

        System.out.print("Id: ");
        String id = in.nextLine();

        System.out.println("Destination: ");
        String destination = in.nextLine();

        System.out.println("Origin: ");
        String origin = in.nextLine();

        if (id == null || id.isBlank() || destination == null || destination.isBlank() || origin == null || origin.isBlank()) {
            System.out.println("Aborting the command");
            System.out.println();
            System.out.println();
            return;
        }

        api.deletePortForwarding(id, destination, origin);
        System.out.println("Deleted!");
        System.out.println();
        System.out.println();
    }
}
