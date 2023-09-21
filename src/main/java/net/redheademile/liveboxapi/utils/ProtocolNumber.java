package net.redheademile.liveboxapi.utils;

import java.util.List;
import java.util.function.Function;

public class ProtocolNumber {

    private final List<EProtocolNumber> protocols;
    public ProtocolNumber(EProtocolNumber... protocols) {
        this.protocols = List.of(protocols);
    }

    public String toIdsString() {
        return String.join(",", protocols.stream().map(p -> p.id).toList());
    }

    public String toNamesString() {
        return String.join(",", protocols.stream().map(p -> p.name).toList());
    }

    private static ProtocolNumber fromRaw(String raw, Function<String, EProtocolNumber> converter) {
        raw = raw.trim();
        String[] splittedRaw = new String[] { raw };
        if (raw.contains(","))
            splittedRaw = raw.replace(" ", "").split(",");
        else if (raw.contains(";"))
            splittedRaw = raw.replace(" ", "").split(";");
        else if (raw.contains(" "))
            splittedRaw = raw.split(" ");

        EProtocolNumber[] protocols = new EProtocolNumber[splittedRaw.length];
        for (int i = 0; i < splittedRaw.length; i++) {
            String rawProtocol = splittedRaw[i];
            EProtocolNumber protocolNumber = converter.apply(rawProtocol);
            if (protocolNumber == null)
                throw new IllegalArgumentException("Unkown raw protocol: " + rawProtocol);

            protocols[i] = protocolNumber;
        }

        return new ProtocolNumber(protocols);
    }

    public static ProtocolNumber fromNames(String names) {
        return fromRaw(names, EProtocolNumber::fromName);
    }

    public static ProtocolNumber fromIds(String ids) {
        return fromRaw(ids, EProtocolNumber::fromId);
    }

    public enum EProtocolNumber {
        TCP("tcp", "6"),
        UDP("udp", "17");

        private final String name;
        private final String id;
        EProtocolNumber(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public static EProtocolNumber fromName(String name) {
            for (EProtocolNumber protocolNumber : values())
                if (protocolNumber.name.equalsIgnoreCase(name))
                    return protocolNumber;
            return null;
        }

        public static EProtocolNumber fromId(String id) {
            for (EProtocolNumber protocolNumber : values())
                if (protocolNumber.id.equalsIgnoreCase(id))
                    return protocolNumber;
            return null;
        }
    }
}
