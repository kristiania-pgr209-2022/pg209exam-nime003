package no.kristiania.chatapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class ChatAppServer {
    private static final Logger logger = LoggerFactory.getLogger(no.kristiania.chatapp.ChatAppServer.class);
    private final Server chatAppServer;

    public ChatAppServer(int port) {
        this.chatAppServer = new Server(port);

        var wContext = new WebAppContext();
        wContext.setContextPath("/");
        wContext.setBaseResource(Resource.newClassPathResource("/webapp"));

        chatAppServer.setHandler(new HandlerList(wContext));
    }
    public void start() throws Exception {
        chatAppServer.start();
    }
    public URL getURL() throws MalformedURLException {
        return chatAppServer.getURI().toURL();
    }

    public static void main(String[] args) throws Exception {
        int port = Optional.ofNullable(System.getenv("HTTP_PLATFORM_PORT"))
                .map(Integer::parseInt)
                .orElse(9090);
        var chatAppServer = new ChatAppServer(port/*, Database.getDataSource()*/);
        chatAppServer.start();
        logger.warn("Starting at port {}", chatAppServer.getURL());
    }
}
