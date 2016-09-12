package net.mrlatte.khanjar.collections;

import java.util.List;

/**
 * Created by jongha on 9/8/15.
 */
public class CollectionKnife {
    public static String[] toArray(List<String> object) {
        String[] objects = new String[object.size()];
        objects = object.toArray(objects);

        return objects;
    }

    public static long[] toPrimitives(Long... objects) {
        long[] primitives = new long[objects.length];
        for (int i = 0; i < objects.length; i++)
            primitives[i] = objects[i];

        return primitives;
    }

    public static Long[] toClass(long... objects) {
        Long[] classType = new Long[objects.length];
        for (int i = 0; i < objects.length; i++)
            classType[i] = objects[i];

        return classType;
    }
}
