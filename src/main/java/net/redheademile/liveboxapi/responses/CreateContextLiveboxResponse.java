package net.redheademile.liveboxapi.responses;

public class CreateContextLiveboxResponse {
    public int status;
    public CreateContextLiveboxResponseData data;

    public static class CreateContextLiveboxResponseData {
        public String contextID;
        public String username;
        public String groups;
    }
}
