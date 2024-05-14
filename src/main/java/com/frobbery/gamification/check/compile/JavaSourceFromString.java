package com.frobbery.gamification.check.compile;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class JavaSourceFromString extends SimpleJavaFileObject {

    private final String sourceCode;

    public JavaSourceFromString(String name, String sourceCode) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
            Kind.SOURCE);
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}