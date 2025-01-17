package com.qomolangma.frameworks.core.test;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;

@WithMocks
public class WithMocksTest {
    private Person errorPerson;
    private @Mock Persons persons;
    private @Spy Persons spyPerson;
    private @Captor ArgumentCaptor<Person> captor;

    @BeforeEach
    void setUp() {
        errorPerson = new Person(null, null);
        doThrow(new RuntimeException("error, name cannot be null.")).when(persons).add(errorPerson);
    }

    @Test
    void should_be_able_to_mock_one_object_successfully() {
        assertThatThrownBy(() -> persons.add(errorPerson))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("error, name cannot be null.");
    }

    @Test
    void should_be_able_to_spy_one_real_object() {
        Person neil = new Person("Neil Wang", "webmaster@neilwang.wiki");
        spyPerson.add(neil);
        then(spyPerson).should(only()).add(neil);
    }

    @Test
    void should_be_able_to_use_captor_to_get_test_response() {
        new SavePersonUseCase(persons).execute();
        then(persons).should(only()).add(captor.capture());
        assertEquals("Neil", captor.getValue().name);
        assertEquals(1, captor.getValue().email.length());
        assertThat(Integer.parseInt(captor.getValue().email)).isNotNegative();
        assertThat(Integer.parseInt(captor.getValue().email)).isLessThan(5);
    }

    @AllArgsConstructor
    private static class SavePersonUseCase {
        private final Persons persons;

        private void execute() {
            persons.add(new Person("Neil", String.valueOf(new Random().nextInt(5))));
        }
    }

    @AllArgsConstructor
    private static class Person {
        private final String name;
        private final String email;
    }

    interface Persons {
        void add(Person person);
    }
}
