package com.iota.iri.model;

import com.iota.iri.storage.Persistable;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paul on 3/8/17 for iri.
 */
public class Hashes implements Persistable {
    public final Set<Hash> set = new HashSet<>();
    private static final byte delimiter = ",".getBytes()[0];

    public byte[] bytes() {
        return set.parallelStream()
                .map(Hash::bytes)
                .reduce((a,b) -> ArrayUtils.addAll(ArrayUtils.add(a, delimiter), b))
                .orElse(new byte[0]);
    }

    public void read(byte[] bytes) {
        if(bytes != null) {
            for (int i = 0; i < bytes.length; i += 1 + Hash.SIZE_IN_BYTES) {
                set.add(new Hash(bytes, i, Hash.SIZE_IN_BYTES));
            }
        }
    }

    @Override
    public byte[] metadata() {
        return new byte[0];
    }

    @Override
    public void readMetadata(byte[] bytes) {

    }

    @Override
    public boolean merge() {
        return true;
    }
}
