package net.redheademile.liveboxapi.requests;

public class SetPortForwardingLiveboxRequest extends LiveboxRequest {
    public SetPortForwardingLiveboxRequest(
            String id,
            boolean enable,
            String internalPort,
            String externalPort,
            String sourcePrefix,
            String destinationIPAddress,
            String protocol,
            boolean persistent,
            String description,
            String sourceInterface,
            String origin
    ) {
        super(
                "setPortForwarding",
                "Firewall",
                "id", id,
                "enable", enable,
                "internalPort", internalPort,
                "externalPort", externalPort,
                "sourcePrefix", sourcePrefix,
                "destinationIPAddress", destinationIPAddress,
                "protocol", protocol,
                "persistent", persistent,
                "description", description,
                "sourceInterface", sourceInterface,
                "origin", origin
        );
    }

    public void setEnable(boolean enable) {
        super.parameters.put("enable", enable);
    }

    public void setInternalPort(String internalPort) {
        super.parameters.put("internalPort", internalPort);
    }

    public void setExternalPort(String externalPort) {
        super.parameters.put("externalPort", externalPort);
    }

    public void setDestinationIPAddress(String destinationIPAddress) {
        super.parameters.put("destinationIPAddress", destinationIPAddress);
    }
}
