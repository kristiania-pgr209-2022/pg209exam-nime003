package no.kristiania.chatapp.db.jdbc;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDaoTest {
    private final MessageDao dao;
    @Test
    void ShouldRetrieveAllMessages() {
        assertThat(dao.viewAllMessages()).asString(StandardCharsets.UTF_8).contains("Hei jeg heter nils!");
    }
}
