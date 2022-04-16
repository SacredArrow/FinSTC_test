package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.Map;

public class ConvertibleMessageImpl implements ConvertibleMessage {
    private Map<String, String> records;

    public ConvertibleMessageImpl(Map<String, String> records) {
        this.records = records;
    }

    @Override
    public String getElement(String elementId) {
        return records.get(elementId);
    }
}
