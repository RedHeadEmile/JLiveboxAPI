package net.redheademile.liveboxapi.responses;

import net.redheademile.liveboxapi.requests.DeletePortForwardingLiveboxRequest;
import net.redheademile.liveboxapi.requests.SetPortForwardingLiveboxRequest;

import java.util.HashMap;
import java.util.Map;

public class PortForwardingLiveboxResponse {
    public Map<String, LiveboxPortForwardingResponseStatus> status = new HashMap<>();

    public static class LiveboxPortForwardingResponseStatus {
        public String Id;
        public String Origin;
        public String Description;
        public String Status;
        public String SourceInterface;
        public String Protocol;
        public String ExternalPort;
        public String InternalPort;
        public String SourcePrefix;
        public String DestinationIPAddress;
        public String DestinationMACAddress;
        public int LeaseDuration;
        public boolean HairpinNAT;
        public boolean SymmetricSNAT;
        public boolean UPnPV1Compat;
        public boolean Enable;

        public SetPortForwardingLiveboxRequest toSetPortForwardingLiveboxRequest() {
            return new SetPortForwardingLiveboxRequest(
                    Id,
                    Enable,
                    InternalPort,
                    ExternalPort,
                    SourcePrefix,
                    DestinationIPAddress,
                    Protocol,
                    true,
                    Description,
                    SourceInterface,
                    Origin
            );
        }

        public DeletePortForwardingLiveboxRequest toDeletePortForwardingLiveboxRequest() {
            return new DeletePortForwardingLiveboxRequest(Id, DestinationIPAddress, Origin);
        }
    }
}
