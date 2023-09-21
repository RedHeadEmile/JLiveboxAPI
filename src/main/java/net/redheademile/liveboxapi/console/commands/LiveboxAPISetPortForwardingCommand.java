package net.redheademile.liveboxapi.console.commands;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.console.LiveboxAPIConsole;
import net.redheademile.liveboxapi.exceptions.LiveboxException;
import net.redheademile.liveboxapi.requests.SetPortForwardingLiveboxRequest;
import net.redheademile.liveboxapi.responses.PortForwardingLiveboxResponse;
import net.redheademile.liveboxapi.utils.ProtocolNumber;

import java.util.Scanner;

public class LiveboxAPISetPortForwardingCommand implements ILiveboxAPICommand {
    @Override
    public String[] getNameAndAliases() {
        return new String[] { "set-port-forwarding", "set-pf", "spf" };
    }

    private void handleExistingRules(LiveboxAPI api, Scanner in, PortForwardingLiveboxResponse.LiveboxPortForwardingResponseStatus rule) throws LiveboxException {
        boolean edit;
        while (true) {
            String type = LiveboxAPIConsole.getInputFromConsole(in, "(E)dit / (D)elete", String.class, null);
            if (type.equalsIgnoreCase("e")) {
                edit = true;
                break;
            }
            else if (type.equalsIgnoreCase("d")) {
                edit = false;
                break;
            }
        }

        if (!edit) {
            if (LiveboxAPIConsole.getInputFromConsole(in, "Sure?", Boolean.class, null)) {
                api.deletePortForwarding(rule.Id, rule.DestinationIPAddress, rule.Origin);
                System.out.println("Deleted!");
            }
        }
        else {
            SetPortForwardingLiveboxRequest request = rule.toSetPortForwardingLiveboxRequest();
            request.setExternalPort(LiveboxAPIConsole.getInputFromConsole(in, "External Port", String.class, rule.ExternalPort));
            request.setInternalPort(LiveboxAPIConsole.getInputFromConsole(in, "Internal Port", String.class, rule.InternalPort));
            request.setEnable(LiveboxAPIConsole.getInputFromConsole(in, "Enable", Boolean.class, rule.Enable));
            request.setDestinationIPAddress(LiveboxAPIConsole.getInputFromConsole(in, "Destination IP Address", String.class, rule.DestinationIPAddress));

            api.setPortForwarding(request);
            System.out.println("Updated!");
        }
    }

    private void handleNewRule(LiveboxAPI api, Scanner in, String id) throws LiveboxException {
        SetPortForwardingLiveboxRequest request = new SetPortForwardingLiveboxRequest(
                id,
                LiveboxAPIConsole.getInputFromConsole(in, "Enable", Boolean.class, true),
                LiveboxAPIConsole.getInputFromConsole(in, "Internal Port", String.class, null),
                LiveboxAPIConsole.getInputFromConsole(in, "External Port", String.class, null),
                LiveboxAPIConsole.getInputFromConsole(in, "Source Prefix", String.class, ""),
                LiveboxAPIConsole.getInputFromConsole(in, "Destination IP Address", String.class, null),
                LiveboxAPIConsole.getInputFromConsole(in, "Protocol", ProtocolNumber.class, null).toIdsString(),
                LiveboxAPIConsole.getInputFromConsole(in, "Persistent", Boolean.class, true),
                LiveboxAPIConsole.getInputFromConsole(in, "Description", String.class, null),
                LiveboxAPIConsole.getInputFromConsole(in, "Source Interface", String.class, "data"),
                LiveboxAPIConsole.getInputFromConsole(in, "Origin", String.class, "webui")
        );

        api.setPortForwarding(request);
        System.out.println("Created!");
    }

    @Override
    public void execute(LiveboxAPI api, Scanner in, String... args) throws LiveboxException {
        System.out.println();
        System.out.println();
        String id = LiveboxAPIConsole.getInputFromConsole(in, "Id", String.class, null);

        PortForwardingLiveboxResponse.LiveboxPortForwardingResponseStatus existingRules = api.getPortForwarding().status.values().stream().filter(s -> s.Id.equalsIgnoreCase(id)).findFirst().orElse(null);
        if (existingRules != null) handleExistingRules(api, in, existingRules);
        else handleNewRule(api, in, id);

        System.out.println();
        System.out.println();
    }
}
