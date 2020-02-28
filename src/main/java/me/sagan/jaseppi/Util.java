package me.sagan.jaseppi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Util {

    public static boolean allEqual(String[] arr, String val) {
        boolean equal = true;
        for (String s : arr) {
            if (!s.equalsIgnoreCase(val)) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static boolean allEqual(String[][] arr, String val) {
        boolean equal = true;
        for (String[] s : arr) {
            if (!allEqual(s, val)) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static String jsonGet(String path, File f) {

        ObjectMapper mapper = new ObjectMapper();

        String[] paths = path.contains(".") ? path.split(".") : new String[]{path};

        try {
            JsonNode locatedNode = mapper.readTree(f);

            for (String p : paths) {
                locatedNode = locatedNode.path(p);
            }

            return locatedNode.asText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String jsonGet(String path, String json) {

        ObjectMapper mapper = new ObjectMapper();

        String[] paths = path.contains(".") ? path.split("\\.") : new String[]{path};

        try {
            JsonNode locatedNode = mapper.readTree(json);

            for (String p : paths) {
                locatedNode = locatedNode.path(p);
            }

            return locatedNode.asText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}