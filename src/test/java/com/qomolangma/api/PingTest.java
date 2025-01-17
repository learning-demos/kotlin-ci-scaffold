package com.qomolangma.api;

import com.qomolangma.frameworks.test.web.Documentation;
import com.qomolangma.frameworks.test.web.IntegrationTest;
import com.qomolangma.frameworks.test.web.PathVariables;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.qomolangma.frameworks.test.web.Documentation.doc;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class PingTest extends IntegrationTest {
    @Test
    void should_reply_pong() {
        ValidatableResponse post = post("/ping", PathVariables.variables(Map.of()), Map.of("data", "pong"), documentation());
        post.statusCode(is(200)).body("ping", is("pong")).body("message", is("ok"));
    }

    public Documentation documentation() {
        return doc("test.ping", requestFields(
                fieldWithPath("data").description("测试数据")
        ), responseFields(
                fieldWithPath("ping").description("ping"),
                fieldWithPath("message").description("message")
        ));
    }
}
