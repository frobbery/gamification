package com.frobbery.gamification.check.compile;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.util.Hashtable;
import java.util.Map;

public class LevelTestClassManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, LevelTestClassAsBytes> compiledClasses;
    private final ClassLoader loader;

    public LevelTestClassManager(StandardJavaFileManager standardManager) {
        super(standardManager);
        this.compiledClasses = new Hashtable<>();
        this.loader = new LevelTestClassLoader(this.getClass().getClassLoader(), this);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return loader;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
                                               FileObject sibling) {

        LevelTestClassAsBytes classAsBytes = new LevelTestClassAsBytes(
                className, kind);
        compiledClasses.put(className, classAsBytes);
        return classAsBytes;
    }

    public Map<String, LevelTestClassAsBytes> getBytesMap() {
        return compiledClasses;
    }
}
