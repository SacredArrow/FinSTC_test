package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.ConvertibleCollectionImpl;
import org.eagleinvsys.test.converters.impl.ConvertibleMessageImpl;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvConverterTests {

    private List<String> baseTest (ConvertibleCollection collection) {
        CsvConverter converter = new CsvConverter();
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("./test.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Problem opening a stream!");
            e.printStackTrace();
        }
        converter.convert(collection, stream);

        // Now we read from file and check that content is OK
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./test.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    @Test
    void simpleWritingToFileTest() {
        List<ConvertibleMessage> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map1.put("key1", "Lorem");
        map1.put("key2", "ipsum");
        map2.put("key1", "dolor");
        map2.put("key2", "sit amet");
        ConvertibleMessage message1 = new ConvertibleMessageImpl(map1);
        ConvertibleMessage message2 = new ConvertibleMessageImpl(map2);
        list.add(message1);
        list.add(message2);
        List<String> headers = new ArrayList<>();
        headers.add("key1");
        headers.add("key2");
        ConvertibleCollection collection = new ConvertibleCollectionImpl(headers, list);
        List<String> fileContent = baseTest(collection);
        // Create list which we expect as a result
        List<String> needed = new ArrayList<>();
        needed.add("key1,key2");
        needed.add("Lorem,ipsum");
        needed.add("dolor,sit amet");
        assertEquals(fileContent, needed);
    }

    @Test
    void onlyHeadersTest() {
        List<ConvertibleMessage> list = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        headers.add("key1");
        headers.add("key2");
        ConvertibleCollection collection = new ConvertibleCollectionImpl(headers, list);
        List<String> fileContent = baseTest(collection);
        // Create list which we expect as a result
        List<String> needed = new ArrayList<>();
        needed.add("key1,key2");
        assertEquals(fileContent, needed);
    }

    @Test
    void nullTest() {
        List<ConvertibleMessage> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", null);
        ConvertibleMessage message1 = new ConvertibleMessageImpl(map1);
        list.add(message1);
        List<String> headers = new ArrayList<>();
        headers.add("key1");
        ConvertibleCollection collection = new ConvertibleCollectionImpl(headers, list);
        List<String> fileContent = baseTest(collection);
        // Create list which we expect as a result
        List<String> needed = new ArrayList<>();
        needed.add("key1");
        needed.add("null");
        assertEquals(fileContent, needed);
    }

}