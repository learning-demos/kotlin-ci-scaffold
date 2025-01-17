package com.qomolangma.frameworks.payload.core;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PayloadTest {

    @Test
    void should_be_able_to_convert_typed_values() {
        Payload payload = new Payload("a text");
        assertThat(payload.get(StringFieldType.asString())).isEqualTo("a text");
    }

    @Test
    void should_be_able_to_read_typed_fields() {
        Payload payload = new Payload(Map.of("name", "John"));
        assertThat(payload.get("name", StringFieldType.asString())).isEqualTo("John");
    }

    @Test
    void should_be_able_to_read_object_fields() {
        Payload payload = new Payload(Map.of("nested", Map.of("name", "John")));
        assertThat(payload.get("nested", ObjectFieldType.asObject()).get("name", StringFieldType.asString())).isEqualTo("John");
    }

    @Test
    void should_be_able_to_read_objects_from_null_fields() {
        Payload payload = new Payload(Map.of());
        assertThat(payload.get("nested", ObjectFieldType.asObject().nullToEmpty()).get("name", StringFieldType.asString().allowNull())).isEqualTo(null);
    }

}
