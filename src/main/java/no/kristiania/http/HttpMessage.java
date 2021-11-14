package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    public String startLine;
    public final HashMap<String, String> headerFields = new HashMap<>();
    public String messageBody;
    public String location;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readHeaders(socket);
        if(headerFields.containsKey("Content-Length")){
            messageBody = HttpMessage.readCharacters(socket, getContentLength());
        }
    }

    public HttpMessage(String startLine, String messageBody){
        this.startLine = startLine;
        this.messageBody = messageBody;
    }

    public HttpMessage(String startLine, String messageBody, String location){
        this.startLine = startLine;
        this.messageBody = messageBody;
        this.location = location;
    }

    public static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> queryMap = new HashMap<>();
        for (String queryParameter : query.split("&")) {
            int equalsPos = queryParameter.indexOf('=');
            String parameterName = queryParameter.substring(0, equalsPos);
            String parameterValue = queryParameter.substring(equalsPos+1);
            queryMap.put(parameterName, parameterValue);
        }
        return queryMap;
    }

    public int getContentLength(){
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }

    static String readCharacters(Socket socket, int contentLength) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = socket.getInputStream();

        for (int i = 0; i < contentLength; i++) {
            result.append((char) in.read());
        }
        return result.toString();
    }

    private void readHeaders(Socket socket) throws IOException {
        String headerLine;
        while(!(headerLine = HttpMessage.readLine(socket)).isBlank()){
            int colonPos = headerLine.indexOf(':');
            String key = headerLine.substring(0, colonPos);
            String value = headerLine.substring(colonPos + 1).trim();
            headerFields.put(key, value);
        }
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = socket.getInputStream();

        int c;
        while ((c = in.read()) != -1 && c != '\r'){
            result.append((char)c);
        }
        in.read();
        return result.toString();
    }

    public void write(Socket socket) throws IOException {
        String response = startLine + "\r\n" +
                "Content-Length: " + messageBody.getBytes().length + "\r\n" +
                "Content-Type: text/html; utf-8\r\n" +
                "Connection: close\r\n" +
                "Location: " + location + "\r\n" +
                "\r\n" +
                messageBody;

        socket.getOutputStream().write(response.getBytes());
    }
}
