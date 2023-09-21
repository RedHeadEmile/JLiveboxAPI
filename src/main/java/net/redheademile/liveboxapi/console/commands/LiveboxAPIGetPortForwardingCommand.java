package net.redheademile.liveboxapi.console.commands;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.responses.PortForwardingLiveboxResponse;
import net.redheademile.liveboxapi.utils.TableUtils;
import net.redheademile.liveboxapi.exceptions.LiveboxException;
import net.redheademile.liveboxapi.utils.ProtocolNumber;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class LiveboxAPIGetPortForwardingCommand implements ILiveboxAPICommand {
    private final String[] simpleColumns = new String[] { "Id", "Enabled", "Internal Port", "External Port", "Protocol", "Dest IP Addr" };
    private final Function<PortForwardingLiveboxResponse.LiveboxPortForwardingResponseStatus, String[]> simpleMapper =
            rule -> new String[] {
                    rule.Id,
                    rule.Enable ? "Y" : "N",
                    rule.InternalPort,
                    rule.ExternalPort,
                    ProtocolNumber.fromIds(rule.Protocol).toNamesString(),
                    rule.DestinationIPAddress
            };

    private final String[] detailedColumns = new String[] { "Id", "Enabled", "Internal Port", "External Port", "Protocol", "Dest IP Addr", "Origin", "Source Prefix", "Source Interface" };
    private final Function<PortForwardingLiveboxResponse.LiveboxPortForwardingResponseStatus, String[]> detailedMapper =
            rule -> new String[] {
                    rule.Id,
                    rule.Enable ? "Y" : "N",
                    rule.InternalPort,
                    rule.ExternalPort,
                    ProtocolNumber.fromIds(rule.Protocol).toNamesString(),
                    rule.DestinationIPAddress,
                    rule.Origin,
                    rule.SourcePrefix,
                    rule.SourceInterface
            };

    @Override
    public String[] getNameAndAliases() {
        return new String[] { "get-port-forwarding", "get-pf", "gpf" };
    }

    @Override
    public void execute(LiveboxAPI api, Scanner in, String... args) throws LiveboxException {
        System.out.println();
        System.out.println();

        boolean detailed = args.length > 0 && args[0].equalsIgnoreCase("-a");
        String[] columnNames = detailed ? detailedColumns : simpleColumns;
        Function<PortForwardingLiveboxResponse.LiveboxPortForwardingResponseStatus, String[]> mapper = detailed ? detailedMapper : simpleMapper;

        List<String[]> rows = api.getPortForwarding().status.values().stream().map(mapper).toList();

        TableUtils.tableDisplay(columnNames, rows);
        System.out.println();
        System.out.println();
    }
}
