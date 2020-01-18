package com.zodiacmc.ZodiacManager.Leaderboards;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import com.zodiacmc.ZodiacManager.Plugins.IPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArrayLeaderboard<K, V extends Comparable<V>> implements ILeaderboard<K, V> {
    
    private ArrayList<Entry<K, V>> leaderboard;
    private transient EntrySet entrySet;

    private transient File file;
    private transient FileConfiguration config;


    ArrayLeaderboard() {
        // Initialize the ArrayList that holds the entries.
        this.leaderboard = new ArrayList<>();
    }

    public ArrayLeaderboard(IPlugin plugin, LeaderboardType type) {
        this(); // Call to self to initialize ArrayList.

        this.file = new File(plugin.getBaseCommand().getFilePath() + '/' + type.getName());
        
        if (this.file.exists()) { // If the file exists:
            this.config = YamlConfiguration.loadConfiguration(file); // Load the configuration from the file.
        } else { // Otherwise:
            try {
                this.file.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(file);
            } catch (IOException e) {

            }
            
        }
    }


    @Override
    public synchronized V get(K key) {
        for (Entry<K, V> e : leaderboard) {
            // Check if the key of the current element in iteration is equal to the one being searched for.
            if (e.getKey().equals(key)) 
                return e.getValue(); // If so, return the value.
        }

        return null; // If nothing is found, return null.
    }

    @Override
    public synchronized boolean containsKey(K key) {
        for (Entry<K, V> e : leaderboard) {
            // If the current element in iteration has the same key as being searched for.
            if (e.getKey().equals(key)) 
                return true; // Guess what happens here.
        }

        return false; // Otherwise, guess what happens here. (It rhymes with breturn balse).
    }

    @Override
    public synchronized boolean containsValue(V value) {
        // Because the Leaderboard is sorted based on the value of each entry, we can use Binary Search to
        // check if the Leaderboard contains the given value.
        return Collections.binarySearch(leaderboard, new Entry<K, V>(null, value)) >= 0;
    }

    @Override
    public synchronized int size() {
        return leaderboard.size(); // hmm...  I wonder what this could do.
    }

    @Override
    public synchronized int getPosition(K key) {
        for (int i = leaderboard.size() - 1; i >= 0; i--) {
            if (leaderboard.get(i).getKey().equals(key)) { // Check if the key is equal to the given key.
                return leaderboard.size() - i; // if so, return the current position.
            }
        }

        return -1; // If it wasn't found, return '-1'.
    }

    @Override
    public synchronized ILeaderboard.Entry<K, V> getEntry(int position) {
        try {
            return leaderboard.get(position); // Get the entry if the position is within the index range.
        } catch (IndexOutOfBoundsException e) {
            return null; // If the index is not in range, return null.
        }
    }

    @Override
    public synchronized void refresh() {
        Collections.sort(leaderboard); // Sort it.
    }

    @Override
    public synchronized Set<ILeaderboard.Entry<K, V>> entrySet() {
        // Check if entrySet exists in cache, if so, return that, otherwise make a new one.
        return ((entrySet == null)? entrySet = new EntrySet() : entrySet);
    }

    @Override
    public synchronized boolean add(K key, V value) {
        Entry<K, V> newElement = new Entry<>(key, value);
        int index = Collections.binarySearch(leaderboard, newElement); // Get the 'would-be' position of the element.
        // System.out.println(index + ": " + newElement);
        if (size() == 0) {
            leaderboard.add(newElement);
            return true;
        }

        if (index > 0) // if its already in the Leaderboard.
            return false; // STOP and return false.
        
        leaderboard.add(((index + 1) * -1), newElement); // Calculate the positive position of the element and add it.
        return true;
    }

    @Override
    public synchronized ILeaderboard.Entry<K, V> remove(K key) {
        Entry<K, V> element; // This will hold the removed element so it can be returned.
        for (int i = 0; i < leaderboard.size(); i++) { // Iterate through all elements.
            if (leaderboard.get(i).getKey().equals(key)) { // If the element key is equal to key param...
                element = leaderboard.get(i); // Store the element at index i
                leaderboard.remove(element); // Remove the element at index i
                return element; // return the removed element.
            }
        }

        return null; // If not found, return null.
    }

    @Override
    public synchronized void clear() {
        leaderboard.clear(); // Clear the list.
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = leaderboard.size() - 1; i >= 0; i--) {
            Entry<K, V> element = leaderboard.get(i);
            sb.append((leaderboard.size() - i) + ". " + element.getKey() + ": " + element.getValue() + "\n");
        }
        sb.delete(sb.length() - 1, sb.length());

        return sb.toString();
    }

    public void save() throws IOException {
        for (Entry<K, V> e : leaderboard) {
            config.set("leaderboard." + e.getKey(), e.getValue());
        }

        config.save(file);
    }

    public <T> void loadLeadeboard(Transformer<T> transformer) {
        for (String k : config.getKeys(false)) {
            add((K) transformer.transform(k), (V) config.get(k));
        }
    }


    @SuppressWarnings("hiding")
    class Entry<K, V extends Comparable<V>> implements ILeaderboard.Entry<K, V>, Comparable<Entry<K, V>>, Serializable {

        private static final long serialVersionUID = 8225664440817990721L;

        K key;
        V value;


        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public int compareTo(Entry<K, V> other) {
            return value.compareTo(other.value);
        }

        @Override
        public String toString() {
            return key + ": " + value;
        }
    }

    class EntrySet extends AbstractSet<ILeaderboard.Entry<K, V>> {

        @Override
        public Iterator<ILeaderboard.Entry<K, V>> iterator() {
            return null;
        }

        @Override
        public int size() {
            return ArrayLeaderboard.this.size();
        }

        @Override
        public void clear() {
            ArrayLeaderboard.this.clear();
        }

        @Override
        public void forEach(Consumer<? super ILeaderboard.Entry<K, V>> action) {
            if (action == null)
                throw new NullPointerException();
            
            if (leaderboard.size() > 0) {
                for (int i = 0; i < size(); i++) {
                    action.accept(leaderboard.get(i));
                }
            }
        }
        
    }
}