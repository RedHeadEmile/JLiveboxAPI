package net.redheademile.liveboxapi.requests;

public class DeletePortForwardingLiveboxRequest extends LiveboxRequest {
    public DeletePortForwardingLiveboxRequest(String id, String destinationIPAddress, String origin) {
        super(
                "deletePortForwarding",
                "Firewall",
                "id", id,
                "destinationIPAddress", destinationIPAddress,
                "origin", origin
        );
    }
}
