package com.frobbery.gamification.check.compile;

import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class JavaSourceFromStringTest {

    private final JavaSourceFromString sut = new JavaSourceFromString("name.name", "sourceCode");

    @Test
    void shouldContainRightUriAndKind() {
        assertEquals(URI.create("string:///name/name.java"), sut.toUri());
        assertEquals(JavaFileObject.Kind.SOURCE, sut.getKind());
    }

    @Test
    void shouldReturnSourceCode_whenGetCharContent() {
        assertEquals("sourceCode", sut.getCharContent(true));
    }
}