package net.mrlatte.khanjar.collections;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Jongha on 2/14/16.
 */
public class CollectionKnifeTest {

    @Test
    public void testToArray() throws Exception {
        int size = 100;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            list.add(i + "");
        }

        String[] stringList = CollectionKnife.toArray(list);
        Assert.assertEquals(stringList.length, size);
        for (int i = 0; i < size; ++i) {
            Assert.assertEquals(stringList[i], i + "");
        }

        // for reverse
        ArrayList<String> reverseList = new ArrayList<>();
        for (int i = size - 1; i >= 0; --i) {
            reverseList.add(i + "");
        }

        String[] reverseStringList = CollectionKnife.toArray(reverseList);
        Assert.assertEquals(reverseStringList.length, size);
        for (int i = 0; i < size; ++i) {
            Assert.assertEquals(reverseStringList[i], (size - i - 1) + "");
        }
    }

    @Test
    public void testToPrimitives() throws Exception {
        long[] primitive = CollectionKnife.toPrimitives(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        Assert.assertEquals(primitive.length, 10);
        for (int i = 0; i < 10; ++i) {
            Assert.assertEquals(primitive[i], i);
        }
    }

    @Test
    public void testToClass() throws Exception {
        Long[] classList = CollectionKnife.toClass(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Assert.assertEquals(classList.length, 10);
        for (int i = 0; i < 10; ++i) {
            Assert.assertEquals(classList[i], new Long(i));
        }
    }
}