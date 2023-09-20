package net.redheademile.liveboxapi.console.commands;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.console.TableUtils;
import net.redheademile.liveboxapi.exceptions.LiveboxException;
import net.redheademile.liveboxapi.utils.ProtocolNumber;

import java.util.List;
import java.util.Scanner;

public class LiveboxAPIGetPortForwardingCommand implements ILiveboxAPICommand {
    @Override
    public String[] getNameAndAliases() {
        return new String[] { "get-port-forwarding", "get-pf", "gpf" };
    }

    @Override
    public void execute(LiveboxAPI api, Scanner in, Object... options) throws LiveboxException {
        System.out.println();
        System.out.println();
        String[] columnNames = new String[] { "Id", "Enabled", "Internal Port", "External Port", "Protocol", "Dest IP Addr" };
        List<String[]> rows = api.getPortForwarding().status.values().stream().map(rule ->
                new String[] {
                        rule.Id,
                        rule.Enable ? "Y" : "N",
                        rule.InternalPort,
                        rule.ExternalPort,
                        ProtocolNumber.formatRawNumbers(rule.Protocol),
                        rule.DestinationIPAddress
                }).toList();

        TableUtils.tableDisplay(columnNames, rows);
        System.out.println();
        System.out.println();
    }
}
