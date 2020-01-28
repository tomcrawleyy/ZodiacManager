package com.zodiacmc.ZodiacManager.Leaderboards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * A more efficient way of storing leaderboard information using Java HashMaps.
 * 
 * @param <K> The type for each key value in the leaderboard. Every key is
 *            unique, however more constraints can be added.
 * @param <V> The type for each value in the leaderboard. Constraints may be
 *            added.
 * 
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class HashMapLeaderboard<K, V extends Comparable<V>> {
    private LinkedHashMap<K, V> leaderboard;
    // private LeaderboardType type;
    // private transient IPlugin plugin;

    // private transient File file;
    // private transient FileConfiguration config;

    public Predicate<K> keyConstraint;
    public Predicate<V> valueConstraint;

    /**
     * Creates a new HashMapLeaderboard.
     */
    public HashMapLeaderboard() {
        leaderboard = new LinkedHashMap<>();
    }

    /**
     * Creates a new HashMapLeaderboard given the constraints for the keys and
     * values.
     * 
     * @param keyConstraint
     * @param valueConstraint
     */
    public HashMapLeaderboard(Predicate<K> keyConstraint, Predicate<V> valueConstraint) {
        this();
        this.keyConstraint = keyConstraint;
        this.valueConstraint = valueConstraint;
    }

    /*
     * Creates a new HashMapLeaderboard given the plugin and LeaderboardType.
     * 
     * @param plugin
     * @param type
     */
    // public HashMapLeaderboard(IPlugin plugin, LeaderboardType type) {
    //     this();
    //     this.plugin = plugin;
    //     this.type = type;
    // }

    /**
     * Get an element based on its key.
     * 
     * @param key the key of the element to get.
     * @return the element with the given key. If no such element exists,
     *         <code>null</code> is returned.
     */
    public V get(K key) {
        return leaderboard.get(key);
    }

    /**
     * Get whether the Leaderboard contains an element with the given key.
     * 
     * @param key the key to search the Leaderboard for.
     * @return true if the key is found, otherwise returns false.
     */
    public boolean containsKey(K key) {
        return leaderboard.containsKey(key);
    }

    /**
     * Get whether the Leaderboard contains an element with the given value.
     * 
     * @param value
     * @return the amount of entries in the leaderboard.
     */
    public boolean containsValue(V value) {
        return leaderboard.containsValue(value);
    }

    /**
     * Get the amount of entries in the leaderboard.
     * 
     * @return the amount of entries in the leaderboard.
     */
    public int size() {
        return leaderboard.size();
    }

    /**
     * Get the ranking of the element with the given key such that
     * elementKey.equals(key) == true.
     * 
     * @param key the key of the element to search for.
     * @return the position of the element with the given key. If no such element
     *         exists, <code>-1</code> is returned.
     */
    public int getPosition(K key) {
        int i = 0;
        for (Map.Entry<K, V> e : leaderboard.entrySet()) {
            if (e.getKey().equals(key)) {
                return i;
            }

            i++;
        } 

        return -1;
    }

    /**
     * Get a Set containing each entry in the Leaderboard.
     * 
     * @return a Set containing each entry in the leaderboard.
     * @see Set
     */
    public Set<Map.Entry<K, V>> entrySet() {
        return leaderboard.entrySet();
    }

    /**
     * Put an element to the Leaderboard. If an element with an equal key exists,
     * the value is changed to the new value.
     * 
     * @param key   the key of the element to add to the leaderboard.
     * @param value the value of the element to add to the leaderboard.
     * @return <code>true</code> if the element was added successfully, otherwise
     *         returns <code>false</code>.
     */
    public synchronized V put(K key, V value) {
        if ((keyConstraint != null && !keyConstraint.test(key))
                || (valueConstraint != null && !valueConstraint.test(value))) {
            return null;
        }

        return leaderboard.put(key, value);
    }

    /**
     * Remove an entry in the Leaderboard given its key.
     * 
     * @param key the key of the element to remove.
     * @return the value associated with the given key.
     */
    public synchronized V remove(K key) {
        return leaderboard.remove(key);
    }

    /**
     * Remove an entry in the Leaderboard given its key and its value.
     * 
     * @param key   the key of the element to remove.
     * @param value the value of the element to remove.
     * 
     * @return <code>true</code> if the value was removed.
     */
    public synchronized boolean remove(K key, V value) {
        return leaderboard.remove(key, value);
    }

    /**
     * Removes all entries from the leaderboard.
     */
    public synchronized void clear() {
        leaderboard.clear();
    }

    /**
     * Sorts the leaderboard given the Comparator c.
     * 
     * @param c the comparator to use to sort the leaderboard.
     */
    public synchronized void sort(Comparator<Map.Entry<K, V>> c) {
        Set<Map.Entry<K, V>> es = leaderboard.entrySet();
        List<Map.Entry<K, V>> list = new LinkedList<>(es);
        Iterator<Map.Entry<K, V>> it = list.iterator();
        
        // First check if the Leaderboard is already sorted.
        $sortCheck: {
            Map.Entry<K, V> a = it.next();
            while (it.hasNext()) {
                Map.Entry<K, V> b = it.next();
                if (c.compare(a, b) > 0) {
                    break $sortCheck;
                }

                a = b;
            }
            
            return; // Don't bother sorting if it is already sorted.
        }

        // If the leaderboard needs to be sorted, sort it here.
        Collections.sort(list, c);
        this.leaderboard = new LinkedHashMap<>();

        for (Map.Entry<K, V> e : list) {
            leaderboard.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<K, V> e : leaderboard.entrySet()) {
            sb.append(i++ + ". " + e.getKey() + ": " + e.getValue() + "\n");
        }

        return sb.toString();
    }

    /**
     * Saves the data from the leaderboard to a file in the YAML format.
     * @param file
     * @throws IOException
     */
    public void save(File file) throws IOException {
        Yaml yaml = new Yaml(getDumperOptions());

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            yaml.dump(leaderboard, writer);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Load a saved leaderboard from a file.
     * @param file The file holding values for each user.
     * @throws IOException If the given file could not be found.
     * @throws ClassCastException If the values in the file cannot be cast to generic parameters <K> and <V>.
     */
    public void load(File file) throws IOException, ClassCastException {
        Yaml yaml = new Yaml();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            Map<Object, Object> map = (Map<Object, Object>) yaml.load(reader);
            
            for (Map.Entry<Object, Object> e : map.entrySet()) {
                if (!(e.getValue() instanceof Map)) {
                    leaderboard.put((K) e.getKey(), (V) e.getValue());
                }
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Load values into the leaderboard from a given String that is valid YAML.
     * @param txt A vaild YAML String.
     * @throws ClassCastException If the values in the file cannot be cast to generic parameters <K> and <V>.
     */
    public void load(String txt) throws ClassCastException {
        Yaml yaml = new Yaml();
        
        Map<Object, Object> map = (Map<Object, Object>) yaml.load(txt);
        
        for (Map.Entry<Object, Object> e : map.entrySet()) {
            if (!(e.getValue() instanceof Map)) {
                leaderboard.put((K) e.getKey(), (V) e.getValue());
            }
        }

    }

    /*
     * Get special options to make writing to file look pretty.
     */
    private DumperOptions getDumperOptions() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
		options.setPrettyFlow(true);
		options.setIndent(2);
		
		return options;
	}
}