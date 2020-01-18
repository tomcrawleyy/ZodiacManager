package com.zodiacmc.ZodiacManager.Leaderboards;

import java.io.Serializable;
import java.util.Set;

public class LinkedLeaderboard<K, V extends Comparable<V>> implements ILeaderboard<K, V> {

    @Override
    public V get(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPosition(K key) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Entry<K, V> getEntry(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<ILeaderboard.Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(K key, V value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Entry<K, V> remove(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
    }


    @SuppressWarnings("hiding")
    class Entry<K, V extends Comparable<V>> implements ILeaderboard.Entry<K, V>, Serializable {

        private static final long serialVersionUID = -2136569876408773597L;
        
        K key;
        V value;

        Entry<K, V> link;


        @Override
        public K getKey() {
            // TODO Auto-generated method stub
            return key;
        }

        @Override
        public V getValue() {
            // TODO Auto-generated method stub
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

    }
}