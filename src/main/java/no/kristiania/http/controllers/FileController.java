package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.http.HttpServer;

import java.io.*;
import java.sql.SQLException;
import java.util.Map;

import static no.kristiania.http.HttpServer.fileTarget;
import static no.kristiania.http.HttpServer.requestTarget;

public class FileController implements HttpController{
    private final HttpServer httpServer;
    private String requestTarget;
    private Map<String, String> query;
    private OutputStream out;

    public FileController(HttpServer httpServer) {this.httpServer = httpServer;}
    @Override
    public String getPath() {
        return null;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {

        try {
            String requestPath = requestTarget;
            int questionPos = requestTarget.indexOf('?');
            if(questionPos != -1){
                requestPath = requestTarget.substring(0,questionPos);
            }
            File file = new File(httpServer.getFileLocation() + requestPath);
            if (file.exists()) {
                String fileExtension = requestPath.substring(requestPath.lastIndexOf('.')).trim();
                String contentType = findContentType(fileExtension);

                out.write(("HTTP/1.1 200 OK\r\n").getBytes());
                out.write(("Content-Type: " + contentType + "\r\n").getBytes());
                out.write(("Content-Length: " + file.length() + "\r\n").getBytes());
                out.write(("Connection: close\r\n").getBytes());
                out.write(("\r\n").getBytes());

                new FileInputStream(file).transferTo(out);
            } else {
                out.write(("HTTP/1.1 404 Not found\r\n").getBytes());
                out.write(("\r\n").getBytes());
                out.write(("404 - Not Found").getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
        return null;
    }

    private String findContentType(String fileExtension) throws IOException {
        InputStream fileResource = getClass().getResourceAsStream(fileTarget);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        String responseText = buffer.toString();

        if(fileResource != null){
            fileResource.transferTo(buffer);
            String contentType = "text/plain";
            if (requestTarget.endsWith("html")){
                contentType = "text/html;";
            } else if (requestTarget.endsWith(".css")) {
                contentType ="text/css; "; //charset=utf-8
            }
        }
        return null;
    }
}
