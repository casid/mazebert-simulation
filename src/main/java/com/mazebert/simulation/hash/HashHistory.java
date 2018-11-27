package com.mazebert.simulation.hash;

public strictfp class HashHistory {
    private int[] hashes;
    private int hashIndex;

    public HashHistory(int size) {
        hashes = new int[size];
    }

    public void add(int hash) {
        int lastIndex = hashes.length - 1;
        if (hashIndex > lastIndex) {
            System.arraycopy(hashes, 1, hashes, 0, lastIndex);
            --hashIndex;
        }

        hashes[hashIndex++] = hash;
    }

    public int getOldest() {
        return hashes[0];
    }
}
