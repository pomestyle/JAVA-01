package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoad  extends ClassLoader{

    private String path;

    public MyClassLoad( String path) {
        this.path = path;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = decode(getBytes(path));
        if (bytes == null)
            throw new ClassNotFoundException();
        return defineClass(name, bytes, 0, bytes.length);
    }


    public byte[] getBytes(String filepath) {
        try {
            return Files.readAllBytes(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 反码
     * @param origin
     * @return
     */
    public byte[] decode(byte[] origin) {
        int len = origin.length;
        byte[] result = new byte[len];
        System.arraycopy(origin, 0, result, 0, len);
        for (int i = 0; i < len; i++)
            result[i] = (byte) (255 - origin[i]);
        return result;
    }

}
