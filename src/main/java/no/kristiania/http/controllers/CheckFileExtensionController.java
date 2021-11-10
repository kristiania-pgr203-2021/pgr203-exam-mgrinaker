package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.http.HttpServer;
import no.kristiania.http.controllers.HttpController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static no.kristiania.http.HttpServer.fileTarget;
import static no.kristiania.http.HttpServer.requestTarget;

public class CheckFileExtensionController implements HttpController {
    //private ServerSocket serverSocket;

    @Override
    public String getPath() {
        return "src/main/resources";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws IOException {
        //Socket clientSocket = serverSocket.accept();
        InputStream fileResource = getClass().getResourceAsStream(fileTarget);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        String responseText = buffer.toString();

        if(fileResource != null){
            fileResource.transferTo(buffer);
            String contentType = "text/plain";
            if(requestTarget.endsWith(".html")){
                contentType = "text/html; "; //charset=utf-8
            } else if (requestTarget.endsWith(".css")) {
                contentType ="text/css; "; //charset=utf-8
            }
            //writeOkResponse(clientSocket, responseText, contentType);
            //return;
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }

    /*private void writeOkResponse(Socket clientSocket, String responseText, String contentType) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseText.getBytes().length + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseText;

        clientSocket.getOutputStream().write(response.getBytes());
    }*/
}
