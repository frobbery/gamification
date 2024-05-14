package com.frobbery.gamification.check.compile;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class LevelTestClassAsBytes extends SimpleJavaFileObject {

    protected final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public LevelTestClassAsBytes(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    public byte[] getBytes() {
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
        return byteArrayOutputStream;
    }
}
