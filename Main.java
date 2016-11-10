import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * Main class
     */
    private static Trie trie = new Trie(false);
    private static final int COUNT_LIMIT = 10;

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(final Map<K, V> mapToSort) {
        // Sort the HashMap by value
        List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(mapToSort.size());
        
        entries.addAll(mapToSort.entrySet());
        
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(final Map.Entry<K, V> entry1, final Map.Entry<K, V> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        int count = 0;
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
            count ++;
            if (count == COUNT_LIMIT) break;
        }
        return sortedMap;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }
        final File indexableDirectory = new File(args[0]);
        File[] indexableFiles = indexableDirectory.listFiles();
        System.out.println(String.format("%d files read in directory %s", indexableFiles.length, args[0]));
        // Inserting all the words into the Trie
        for (File file: indexableFiles) {
            try {
                Scanner wordScan = new Scanner(file);
                while (wordScan.hasNextLine()) {
                    Scanner scan = new Scanner(wordScan.nextLine());
                    while (scan.hasNext()) {
                        String str = scan.next();
                        trie.addWord(str, file.getName());
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        Scanner keyboard = new Scanner(System.in);
        Map<String, Integer> stats = new HashMap<String, Integer>();
        while (true) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            if (line.equals(":quit")) break;
            Scanner scan = new Scanner(line);
            int wordCount = 0;
            // Initilisation of stats HashMap
            stats = new HashMap<String, Integer>();
            for (File file: indexableFiles) {
                stats.put(file.getName(), 0);
            }
            while (scan.hasNext()) {
                String str = scan.next();
                // Search word on the tree, get the occurence map
                Map<String, Integer> result = trie.searchWord(str);
                if (result != null) {
                    for(String key: result.keySet()) {
                        stats.put(key, stats.get(key) + 1);
                    }
                }
                wordCount ++;
            }
            stats = sortMapByValue(stats);
            // Show the result
            for (String key: stats.keySet()) {
                System.out.println(String.format("%s: %.0f%%", key, stats.get(key) * 100.0 / wordCount));
            }
        }
    }
}

