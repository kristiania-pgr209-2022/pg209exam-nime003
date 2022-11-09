package no.kristiania.chatapp;

import org.eclipse.jetty.server.Server;

import java.net.MalformedURLException;
import java.net.URL;

public class ChatAppServer {
    private final Server ChatAppServer;

    public ChatAppServer(int port) {
        this.ChatAppServer = new Server(port);
    }
    public void start() throws Exception {
        ChatAppServer.start();
    }
    public URL getURL() throws MalformedURLException {
        return ChatAppServer.getURI().toURL();
    }
}
