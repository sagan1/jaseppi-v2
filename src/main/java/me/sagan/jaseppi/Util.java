package me.sagan.jaseppi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    public static String jsonGrab(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        String json;

        try {
            json = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public static String jsonPost(String url, Map<String, String> headersNameValue, Map<String, String> data, Map<String, String> authCredentials) {

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder postBuilder = new FormBody.Builder();
        data.forEach(postBuilder::add);

        Request.Builder requestBuilder = new Request.Builder().url(url).post(postBuilder.build());
        headersNameValue.forEach(requestBuilder::addHeader);
        authCredentials.forEach((s, s2) -> requestBuilder.addHeader("Authorization", Credentials.basic(s, s2)));

        Request request = requestBuilder.build();

        String json;

        try {
            json = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public static String jsonGrab(String url, Map<String, String> headersNameValue, Map<String, String> authCredentials) {

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        headersNameValue.forEach(requestBuilder::addHeader);
        authCredentials.forEach((s, s2) -> requestBuilder.addHeader("Authorization", Credentials.basic(s, s2)));

        Request request = requestBuilder.build();

        String json;

        try {
            json = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
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

    public static String[] jsonGetArray(@Nullable String path, String json) {

        ObjectMapper mapper = new ObjectMapper();

        String[] paths = path.contains(".") ? path.split("\\.") : new String[]{path};

        if (path.equalsIgnoreCase("null")) {
            paths = null;
        }

        try {
            JsonNode locatedNode = mapper.readTree(json);

            if (paths != null) {
                for (String p : paths) {
                    locatedNode = locatedNode.path(p);
                }
            }

            if (locatedNode.isArray()) {

                ArrayNode arrayNode = (ArrayNode) locatedNode;

                String[] returnable = new String[arrayNode.size()];

                for (int i = 0; i < arrayNode.size(); i++) {
                    returnable[i] = arrayNode.get(i).textValue();
                }

                return returnable;
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonNode[] jsonGetObjArray(String path, String json) {

        ObjectMapper mapper = new ObjectMapper();

        String[] paths = path.contains(".") ? path.split("\\.") : new String[]{path};

        try {
            JsonNode locatedNode = mapper.readTree(json);

            for (String p : paths) {
                locatedNode = locatedNode.path(p);
            }

            if (locatedNode.isArray()) {

                ArrayNode arrayNode = (ArrayNode) locatedNode;

                JsonNode[] returnable = new JsonNode[arrayNode.size()];

                for (int i = 0; i < arrayNode.size(); i++) {
                    returnable[i] = arrayNode.get(i);
                }

                return returnable;
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}