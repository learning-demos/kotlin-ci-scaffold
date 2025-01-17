package com.qomolangma.frameworks.payload.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayFieldTypeTest {
    @Test
    void should_be_able_to_convert_into_lists_from_array_lists() {
        assertThat(StringFieldType.asString().array().from(List.of("1", "2", "3"))).isEqualTo(List.of("1", "2", "3"));
    }

    @Test
    void should_be_able_to_convert_into_lists_from_null_values() {
        assertThat(StringFieldType.asString().array().nullToEmpty().from(null)).isEqualTo(List.of());
    }
}
