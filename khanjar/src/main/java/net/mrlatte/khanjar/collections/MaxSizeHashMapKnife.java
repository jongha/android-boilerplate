package net.mrlatte.khanjar.collections;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jongha on 7/24/16.
 */
public class MaxSizeHashMapKnife<K, V> extends LinkedHashMap<K, V> {
    private final int mMaxSize;

    public MaxSizeHashMapKnife(int maxSize) {
        this.mMaxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > mMaxSize;
    }
}