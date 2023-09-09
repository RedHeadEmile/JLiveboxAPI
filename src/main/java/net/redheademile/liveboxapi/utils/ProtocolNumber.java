package net.redheademile.liveboxapi.utils;

public class ProtocolNumber {
    public static final String TCP = "6";
    public static final String UDP = "17";

    public static String buildNumbersString(String... protocolsNumber) {
        return String.join(",", protocolsNumber);
    }

    public static String formatRawNumbers(String rawProtocols) {
        String[] splittedRawProtocols = new String[] { rawProtocols };
        if (rawProtocols.contains(","))
            splittedRawProtocols = rawProtocols.split(",");

        String[] protocols = new String[splittedRawProtocols.length];
        for (int i = 0; i < splittedRawProtocols.length; i++) {
            String rawProtocol = splittedRawProtocols[i];
            switch (rawProtocol) {
                case TCP -> protocols[i] = "tcp";
                case UDP -> protocols[i] = "udp";
                default -> throw new IllegalArgumentException("Unkown raw protocol: " + rawProtocol);
            }
        }

        return String.join(",", protocols);
    }
}
