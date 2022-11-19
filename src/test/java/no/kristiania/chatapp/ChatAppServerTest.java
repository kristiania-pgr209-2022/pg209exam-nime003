package no.kristiania.chatapp;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import no.kristiania.chatapp.db.Database;
import no.kristiania.chatapp.db.InMemoryDatasource;
import no.kristiania.chatapp.db.jdbc.SampleData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatAppServerTest {
    private static final Logger logger = LoggerFactory.getLogger(no.kristiania.chatapp.ChatAppServer.class);
    private ChatAppServer server;
    private SampleData sampleData = new SampleData();
    @BeforeEach
    void setup() throws Exception {
        server = new ChatAppServer(0, InMemoryDatasource.createTestDataSource());
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

    @Test
        void shouldRetrieveAllUsers() throws IOException {
        var postConn = openConnection("/api/user");
        postConn.setRequestMethod("POST");
        postConn.setRequestProperty("Content-Type", "application/json");
        postConn.setDoOutput(true);
        var user = sampleData.sampleUser();
        postConn.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("id", user.getId())
                        .add("username", user.getUsername())
                        .add("password", user.getPassword())
                        .build()
                        .toString().getBytes(StandardCharsets.UTF_8)
        );
        checkHttpResponse(postConn, 204);

        var conn = openConnection("/api/user");
        assertThat(conn.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"username\":\""+ user.getUsername() + "\"");
    }

    @Test
    void populateDatabaseTest() throws SQLException {
        InMemoryDatasource inMemoryDatasource = new InMemoryDatasource();
        inMemoryDatasource.populateDatabase(InMemoryDatasource.createTestDataSource());
    }
    private HttpURLConnection openConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), spec).openConnection();
    }

    private void checkHttpResponse(HttpURLConnection connection,int expectedResponse) throws IOException {
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(expectedResponse);
    }

    // main used for testing react etc.
    public static void main(String[] args) throws Exception {
        var testDataSource = new InMemoryDatasource();
        testDataSource.populateDatabase(InMemoryDatasource.createTestDataSource());
        int port = Optional.ofNullable(System.getenv("HTTP_PLATFORM_PORT"))
                .map(Integer::parseInt)
                .orElse(9090);
        var chatAppServer = new ChatAppServer(port, InMemoryDatasource.createTestDataSource());
        chatAppServer.start();
        logger.warn("Starting at port {}", chatAppServer.getURL());
    }
}
