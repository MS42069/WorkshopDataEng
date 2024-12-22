package com.fhwedel.softwareprojekt.v1.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implements a HashMap that is limited in size. Once the map has reached the maximum entry size,
 * the oldest entry is deleted each time a new entry is added.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class SizeLimitedHashMap<K, V> extends LinkedHashMap<K, V> {

    /** The maximal entry size the map is allowed to grow. */
    private final int maxEntries;

    /**
     * Constructs a SizeLimitedHashMap with the specified maximum number of entries.
     *
     * @param maxEntries the maximum number of entries the map is allowed to contain.
     */
    public SizeLimitedHashMap(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * Returns {@code true} if this map should remove its eldest entry. This method is invoked by
     * {@code put} and {@code putAll} after inserting a new entry into the map. It provides the
     * implementor with the opportunity to remove the eldest entry each time a new one is added.
     * This is useful if the map represents a cache.
     *
     * @param eldest the least recently inserted entry in the map, or if this is the first entry
     *     added to the map, the least recently inserted entry in the map for which {@code put} or
     *     {@code putAll} was invoked.
     * @return {@code true} if the eldest entry should be removed from the map; {@code false} if it
     *     should be retained.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > maxEntries;
    }
}
