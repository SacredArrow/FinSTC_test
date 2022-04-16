package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CsvConverter implements Converter {

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {
        try (Writer w = new OutputStreamWriter(outputStream, "UTF-8")) {
            Collection<String> headers = collectionToConvert.getHeaders();
            String headersStr = String.join(",", headers);
            w.write(headersStr);
            w.write('\n');
            for (ConvertibleMessage message : collectionToConvert.getRecords()) {
                List<String> messageValues = new ArrayList<>(headers.size()); // Collect values from our message
                for (String header : headers) {
                    messageValues.add(message.getElement(header));
                }
                String valuesStr = String.join(",", messageValues); // Convert our values to CSV string
                w.write(valuesStr);
                w.write('\n');
            }

        } catch (UnsupportedEncodingException e) {
            System.out.println("The encoding is unsupported!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was a problem while writing to file!");
            e.printStackTrace();
        }
    }

}