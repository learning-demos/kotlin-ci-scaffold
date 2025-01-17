package com.qomolangma.frameworks.payload.core;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapFieldTypeTest {
    @Test
    void should_be_able_to_convert_into_maps_from_maps() {
        assertThat(MapFieldType.asMap().from(Map.of("a", "b"))).isEqualTo(Map.of("a", "b"));
    }

}
