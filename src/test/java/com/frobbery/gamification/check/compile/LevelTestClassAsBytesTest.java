package com.frobbery.gamification.check.compile;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LevelTestClassAsBytesTest {

    private final LevelTestClassAsBytes sut = new LevelTestClassAsBytes("name.name", JavaFileObject.Kind.CLASS);

    @Test
    void shouldContainRightUriAndKind() {
        assertEquals(URI.create("string:///name/name.class"), sut.toUri());
        assertEquals(JavaFileObject.Kind.CLASS, sut.getKind());
    }

    @Test
    @SneakyThrows
    void shouldOpenOutputStream() {
        //when
        var outputStream = sut.openOutputStream();

        //then
        assertTrue(outputStream instanceof ByteArrayOutputStream);
        outputStream.close();
    }

    @Test
    @SneakyThrows
    void shouldGetBytes() {
        //given
        var expectedBytes = new byte [1];
        expectedBytes[0] = (byte) 1;
        var outputStream = sut.openOutputStream();
        outputStream.write(expectedBytes);
        outputStream.close();

        //when
        var actualBytes = sut.getBytes();

        //then
        assertThat(actualBytes).isEqualTo(expectedBytes);
    }
}