package taskaurlrequestsjsonapi.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClientUtil {

    // ===================== GET =====================
    public String sendGet(String urlStr) {
        HttpURLConnection connection = null;

        try {
            URL url = URI.create(urlStr).toURL();
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            InputStream is = (status >= 200 && status < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            return readStream(is);

        } catch (IOException e) {
            throw new RuntimeException("GET request failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // ===================== POST =====================
    public String sendPost(String urlStr, String jsonBody) {
        return sendWithBody(urlStr, "POST", jsonBody);
    }

    // ===================== PUT =====================
    public String sendPut(String urlStr, String jsonBody) {
        return sendWithBody(urlStr, "PUT", jsonBody);
    }

    // ===================== DELETE =====================
    public int sendDelete(String urlStr) {
        HttpURLConnection connection = null;

        try {
            URL url = URI.create(urlStr).toURL();
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            return connection.getResponseCode();

        } catch (IOException e) {
            throw new RuntimeException("DELETE request failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // ===================== COMMON (POST/PUT) =====================
    private String sendWithBody(String urlStr, String method, String jsonBody) {
        HttpURLConnection connection = null;

        try {
            URL url = URI.create(urlStr).toURL();
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            connection.setDoOutput(true); // важно!

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // отправка тела
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input);
                os.flush();
            }

            int status = connection.getResponseCode();

            InputStream is = (status >= 200 && status < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            return readStream(is);

        } catch (IOException e) {
            throw new RuntimeException(method + " request failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // ===================== READ STREAM =====================
    private String readStream(InputStream is) throws IOException {
        if (is == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }
}

