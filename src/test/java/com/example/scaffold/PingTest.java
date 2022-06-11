package com.example.scaffold;

import com.example.scaffold.frameworks.test.web.IntegrationTest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.example.scaffold.frameworks.test.web.PathVariables.variables;
import static org.hamcrest.Matchers.is;

public class PingTest extends IntegrationTest {
    @Test
    void should_reply_pong() {
        ValidatableResponse post = post("/ping", variables(Map.of()), Map.of("data", "pong"), null);
        post.statusCode(is(200)).body("body.ping", is("pong"));
    }
}
