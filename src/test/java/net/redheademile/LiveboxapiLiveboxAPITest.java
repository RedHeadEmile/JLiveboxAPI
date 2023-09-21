package net.redheademile;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.utils.TableUtils;
import net.redheademile.liveboxapi.exceptions.LiveboxException;
import net.redheademile.liveboxapi.utils.ProtocolNumber;

import java.net.URISyntaxException;
import java.util.List;

public class LiveboxapiLiveboxAPITest {

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
                        ProtocolNumber.fromIds(rule.Protocol).toNamesString(),
                        rule.DestinationIPAddress
                }).toList();

        TableUtils.tableDisplay(columnNames, rows);
    }
}
