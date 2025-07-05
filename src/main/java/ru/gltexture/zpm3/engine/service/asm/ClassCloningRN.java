package ru.gltexture.zpm3.engine.service.asm;

import net.minecraft.server.MinecraftServer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.io.IOException;
import java.io.InputStream;

public final class ClassCloningRN {
    private final Class<?> source;
    private final String destination;

    public ClassCloningRN(Class<?> source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public void cloneClassInRuntime() {
        final String sourceInternalName = this.source.getName().replace('.', '/');
        final String destinationInternalName = this.destination.replace('.', '/');

        try (InputStream classStream = this.source.getClassLoader().getResourceAsStream(sourceInternalName + ".class")) {
            byte[] newClassBytes = this.getBytes(classStream, sourceInternalName, destinationInternalName);

            class DynamicLoader extends ClassLoader {
                public DynamicLoader(ClassLoader parent) {
                    super(parent);
                }

                public void define(String name, byte[] bytes) {
                    super.defineClass(name, bytes, 0, bytes.length);
                }
            }

            (new DynamicLoader(MinecraftServer.class.getClassLoader())).define(this.destination, newClassBytes);
        } catch (IOException e) {
            throw new ZPRuntimeException(e);
        }
    }

    private byte[] getBytes(InputStream classStream, String sourceInternalName, String destinationInternalName) throws IOException {
        if (classStream == null) {
            throw new IOException("Class not found: " + this.source.getName());
        }

        ClassReader reader = new ClassReader(classStream);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        Remapper remapper = new Remapper() {
            @Override
            public String map(String internalName) {
                return internalName.equals(sourceInternalName) ? destinationInternalName : internalName;
            }
        };

        ClassRemapper classRemapper = new ClassRemapper(writer, remapper);
        reader.accept(classRemapper, ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }
}
