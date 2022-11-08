package no.kristiania.chatapp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatAppTest {

    private ChatApp server;

    @Test
    void httpResponseShouldBe200Test() throws IOException {
        var con = openConnection("/");
        assertThat(con.getResponseCode()).isEqualTo(200);
    }

    private HttpURLConnection openConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), spec).openConnection();
    }
}
