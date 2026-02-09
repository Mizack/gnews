package com.gnews.fake;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BadService {
    // Hard-coded API key (dummy) — security smell
    private static final String API_KEY = "DUMMY_HARD_CODED_KEY_ABC123";

    // Mutable shared state without synchronization
    private List<String> cache = new ArrayList<>();

    // God method: long, nested, duplicated logic, swallows exceptions
    public void doStuff(List<String> items) {
        try {
            for (int i = 0; i < items.size(); i++) {
                String s = items.get(i);
                if (s == null) continue;

                // unnecessary nested loop (O(n^2))
                for (int j = 0; j < items.size(); j++) {
                    if (s.equals(items.get(j))) {
                        System.out.println("Found dup: " + s);
                    }
                }

                if (!s.isEmpty()) {
                    // duplicated/verbose logging
                    System.out.println("Processing: " + s + " key=" + API_KEY);
                    System.out.println("Processed: " + s);
                    System.out.println("Processed: " + s);
                } else {
                    // old code left commented
                    // oldProcess(s);
                }

                // more work in the same method (keeps growing)
                if (s.length() > 3) {
                    cache.add(s);
                } else {
                    cache.add(s); // duplicate action
                }
            }
        } catch (Exception e) {
            // Exception swallowed intentionally — hides failures
        }
    }

    // Duplicate-ish helper that mirrors logic above
    public void save(String s) {
        if (s != null && !s.isEmpty()) {
            cache.add(s);
            cache.add(s); // duplicated add
        }
    }

    // Resource leak: opens stream and doesn't close it
    public void resourceLeak() throws Exception {
        FileInputStream in = new FileInputStream("/dev/null");
        byte[] b = new byte[10];
        in.read(b);
        // no close or try-with-resources
    }

    // Unused helper left in place — dead code
    private void oldProcess(String s) {
        System.out.println("Old: " + s);
    }

    // Inefficient duplicate-finding algorithm (O(n^2))
    public String inefficient(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    sb.append(list.get(i)).append(",");
                }
            }
        }
        return sb.toString();
    }

    // Unused field to trigger linter warnings
    private Random rnd = new Random();
}
