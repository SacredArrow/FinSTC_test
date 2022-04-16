package org.eagleinvsys.test.converters.impl;


import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ConvertibleCollectionImpl implements ConvertibleCollection {

    private Collection<String> headers;
    private List<ConvertibleMessage> records;

    public ConvertibleCollectionImpl(Collection<String> headers, List<ConvertibleMessage> records) {
        this.headers = headers;
        this.records = records;
    }

    public ConvertibleCollectionImpl(List<Map<String, String>> records) {
        this.records = new ArrayList<>();
        for (Map<String, String> map : records) {
            ConvertibleMessage message = new ConvertibleMessageImpl(map);
            this.records.add(message);
        }
        if (records.size() > 0) {
            this.headers = records.get(0).keySet(); // Since all keys are the same
        } else {
            this.headers = new ArrayList<>();
        }
    }

    @Override
    public Collection<String> getHeaders() {
        return this.headers;
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        return this.records;
    }
}
