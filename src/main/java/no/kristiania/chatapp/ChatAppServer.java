package no.kristiania.chatapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.MalformedURLException;
import java.net.URL;

public class ChatAppServer {
    private final Server ChatAppServer;

    public ChatAppServer(int port) {
        this.ChatAppServer = new Server(port);

        var wContext = new WebAppContext();
        wContext.setContextPath("/");
        wContext.setBaseResource(Resource.newClassPathResource("/webapp"));

        ChatAppServer.setHandler(new HandlerList(wContext));
    }
    public void start() throws Exception {
        ChatAppServer.start();
    }
    public URL getURL() throws MalformedURLException {
        return ChatAppServer.getURI().toURL();
    }
}
