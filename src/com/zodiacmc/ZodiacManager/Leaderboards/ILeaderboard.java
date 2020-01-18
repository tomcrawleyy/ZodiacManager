package com.zodiacmc.ZodiacManager.Leaderboards;

import java.util.Set;

/**
 * The interface for Leaderboards.
 * @param <K> the Key of each element in the array.  The key must be unique to each element.
 * @param <V> the Value of each element in the array.  The value does not have to be unique.
 * 
 * @author Brian Reich
 * @version 1.0.0
 */
public interface ILeaderboard<K, V extends Comparable<V>> {
    /**
     * Get an element based on its key.
     * @param key the key of the element to get.
     * @return the element with the given key.  If no such element exists, <code>null</code> is returned.
     */
    V get(K key);

    /**
     * Get if the Leaderboard contains an element with the given key.
     * @param key the key to search the Leaderboard for.
     * @return true if the key is found, otherwise returns false.
     */
    boolean containsKey(K key);

    /**
     * Get if the Leaderboard contains an element with the given value;
     * @param value
     * @return the amount of entries in the leaderboard.
     */
    boolean containsValue(V value);

    /**
     * Get the amount of entries in the leaderboard.
     * @return the amount of entries in the leaderboard.
     */
    int size();

    /**
     * Get the ranking of the element with the given key such that elementKey.equals(key) == true.
     * @param key the key of the element to search for.
     * @return the position of the element with the given key.  
     * If no such element exists, <code>-1</code> is returned.
     */
    int getPosition(K key);

    /**
     * Get the Leaderboard Entry at the given position on the Leaderboard.
     * @param position the position of the element to get.
     * @return the element, given its position/ranking.  
     * If no such element exists, <code>null</code> is returned.
     */
    ILeaderboard.Entry<K, V> getEntry(int position);

    /**
     * Refresh the Leaderboards entries so they are up to date, 
     * including adjusting the order to match.
     */
    void refresh();

    /**
     * Get a Set of each entry in the Leaderboard.
     * @return a Set containing each entry in the leaderboard.
     */
    Set<ILeaderboard.Entry<K, V>> entrySet();

    /**
     * Add an element to the Leaderboard.
     * @param key the key of the element to add to the leaderboard.
     * @param value the value of the element to add to the leaderboard.
     * @return true if the element was added successfully, otherwise returns false.
     */
    boolean add(K key, V value);

    /**
     * Remove an entry in the Leaderboard given its key.
     * @param key the key of the element to remove.
     */
    ILeaderboard.Entry<K, V> remove(K key);

    /**
     * Removes all entries from the leaderboard.
     */
    void clear();
    
    /**
     * Get a given range of users in String form.
     * 
     * @param first the starting point of the range.
     * @param last the ending point of the range.
     * @return the range in string form.
     */
    default String getStringRange(int first, int last) {
        final StringBuilder sb = new StringBuilder();

        for (int i = first; i < last; i++) {
            ILeaderboard.Entry<K, V> e = getEntry(i);
            sb.append((i + 1) + ". " + e.getKey() + "   " + e.getValue() + "\n");
        }

        return sb.toString();
    }

    String toString();


    /**
     * Holds an entry for a leaderboard.
     * @param <K> The type of the key that the Entry holds.
     * @param <V> The type of the value that the Entry holds.
     */
    public interface Entry<K, V extends Comparable<V>> {

        /**
         * Get the key of the entry.
         * @return the key.
         */
        K getKey();

        /**
         * Get the value of the entry.
         * @return the entry.
         */
        V getValue();

        /**
         * Set the value of the entry.
         */
        void setValue(V value);
    }
}