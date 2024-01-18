package org.example;

import com.google.protobuf.util.Timestamps;
import io.quarkus.test.junit.QuarkusTest;
import org.example.raw.Person;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
class PersonResourceTest {

    private static final Logger LOG = Logger.getLogger(PersonResourceTest.class);

    @Test
    void testPersonEndpoint() throws Exception {
        LOG.info("Running testPersonEndpoint");

        var now = Timestamps.fromMillis(Instant.now().toEpochMilli());
        var person = Person.newBuilder()
                .setName("Alice")
                .setAge(95)
                .setTimestamp(now)
                .build();

        given()
                .when()
                .body(person.toByteArray())
                .post("/person")
                .then()
                .statusCode(201);

        var serializedData = given()
                .when().get("/person")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asByteArray();

        var deserializedPerson = Person.parseFrom(serializedData);

        assertThat(deserializedPerson.getName(), is("Alice"));
    }

}