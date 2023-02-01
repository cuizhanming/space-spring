package com.cuizhanming.template.identity;

import com.cuizhanming.template.identity.entity.Identity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class IdentityJsonTest {

    @Autowired
    private JacksonTester<Identity> json;

    @Test
    public void cashCardSerializationTest() throws IOException {
        var id = "37c1b92a-94db-401c-b05f-61daeec032da";
        var email = "abc@email.com";
        Identity cashCard = new Identity(UUID.fromString(id), email);
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("/identity/expected.json");
        assertThat(json.write(cashCard)).hasJsonPathStringValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathStringValue("@.id").isEqualTo(id);
        assertThat(json.write(cashCard)).hasJsonPathStringValue("@.email");
        assertThat(json.write(cashCard)).extractingJsonPathStringValue("@.email").isEqualTo(email);
    }

    @Test
    public void cashCardDeserializationTest() throws IOException {
        UUID id = UUID.randomUUID();
        var email = "abc@email.com";
        String expected = """
                {
                    "id":"$id",
                    "email":"$email"
                }
                """;

        expected = expected.replace("$id", id.toString()).replace("$email", email);
        assertThat(json.parse(expected)).isEqualTo(new Identity(id, email));
        assertThat(json.parseObject(expected).id()).isEqualTo(id);
        assertThat(json.parseObject(expected).email()).isEqualTo(email);
    }

}