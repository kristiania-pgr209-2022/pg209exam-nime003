package no.kristiania.chatapp;

import no.kristiania.chatapp.db.Database;
import no.kristiania.chatapp.endpoints.UserEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class ChatAppServer {
    private static final Logger logger = LoggerFactory.getLogger(no.kristiania.chatapp.ChatAppServer.class);
    private final Server chatAppServer;

    public ChatAppServer(int port, DataSource dataSource) throws IOException {
        this.chatAppServer = new Server(port);

        var wContext = new WebAppContext();
        wContext.setContextPath("/");
        wContext.setBaseResource(Resource.newClassPathResource("/webapp"));

        Resource resources = Resource.newClassPathResource("/webapp");

        var sourceDirectory = getSourceDirectory(resources);

        if (sourceDirectory != null && sourceDirectory.isDirectory()) {
            wContext.setBaseResource(Resource.newResource(sourceDirectory));
            wContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            wContext.setBaseResource(resources);
        }

        wContext.addServlet(new ServletHolder(new ServletContainer(new ChatappConfig(dataSource))), "/api/*");

        chatAppServer.setHandler(new HandlerList(wContext));
    }

    private File getSourceDirectory(Resource resources) throws IOException {
        if(resources.getFile() == null) return null;

        var sourceDir = new File(resources.getFile().getAbsoluteFile().toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));
        return sourceDir.exists() ? sourceDir : null;
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
        var chatAppServer = new ChatAppServer(port, Database.getDataSource());
        chatAppServer.start();
        logger.warn("Starting at port {}", chatAppServer.getURL());
    }
}
