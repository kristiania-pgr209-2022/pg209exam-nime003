package no.kristiania.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatAppServerTest {

    private ChatAppServer server;
    @BeforeEach
    void setup() throws Exception {
        server = new ChatAppServer(0);
        server.start();
    }

    @Test
    void httpResponseShouldBe200Test() throws IOException {
        var con = openConnection("/");
        assertThat(con.getResponseCode()).isEqualTo(200);
    }

    @Test
    void ShouldServeCorrectTitle() throws IOException {
        var con = openConnection("/");
        assertThat(con.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Welcome to ChatApp!</title>");
    }

    private HttpURLConnection openConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), spec).openConnection();
    }
}
