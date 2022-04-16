package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleCollection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        map1.put("key1", "Lorem");
        map1.put("key2", "ipsum");
        map2.put("key1", "dolor");
        map2.put("key2", "sit amet");
        list.add(map1);
        list.add(map2);

//        ConvertibleCollection collection = new ConvertibleCollectionImpl(list);
        CsvConverter converter = new CsvConverter();
        StandardCsvConverter standartConverter = new StandardCsvConverter(converter);
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("./out.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Problem opening a stream!");
            e.printStackTrace();
        }
//        converter.convert(collection, stream);
        standartConverter.convert(list, stream);
    }
}
