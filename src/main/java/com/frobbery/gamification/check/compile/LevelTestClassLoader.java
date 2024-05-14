package com.frobbery.gamification.check.compile;

import java.util.Map;

public class LevelTestClassLoader extends ClassLoader {
    private final LevelTestClassManager manager;

    public LevelTestClassLoader(ClassLoader parent, LevelTestClassManager manager) {
        super(parent);
        this.manager = manager;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Map<String, LevelTestClassAsBytes> compiledClasses = manager
                .getBytesMap();
        if (compiledClasses.containsKey(name)) {
            byte[] bytes = compiledClasses.get(name)
                    .getBytes();
            return defineClass(name, bytes, 0, bytes.length);
        } else {
            throw new ClassNotFoundException();
        }
    }
}
