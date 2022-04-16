package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardCsvConverterTests {

    private List<String> baseTest (List<Map<String, String>> list) {
        CsvConverter converter = new CsvConverter();
        StandardCsvConverter standardConverter = new StandardCsvConverter(converter);
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("./test.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Problem opening a stream!");
            e.printStackTrace();
        }
        standardConverter.convert(list, stream);

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
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map1.put("key1", "Lorem");
        map1.put("key2", "ipsum");
        map2.put("key1", "dolor");
        map2.put("key2", "sit amet");
        list.add(map1);
        list.add(map2);
        List<String> fileContent = baseTest(list);
        // Create list which we expect as a result
        List<String> needed = new ArrayList<>();
        needed.add("key1,key2");
        needed.add("Lorem,ipsum");
        needed.add("dolor,sit amet");
        assertEquals(fileContent, needed);
    }

    // Test that there are no OOB or any other exception
    @Test
    void emptyListTest() {
        List<Map<String, String>> list = new ArrayList<>();
        List<String> fileContent = baseTest(list);
        List<String> needed = new ArrayList<>();
        needed.add(""); // Since there is still space written
        assertEquals(fileContent, needed);
    }

    @Test
    void nullTest() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("NullVal", null);
        list.add(map);
        List<String> fileContent = baseTest(list);
        List<String> needed = new ArrayList<>();
        needed.add("NullVal");
        needed.add("null");
        assertEquals(fileContent, needed);
    }
}