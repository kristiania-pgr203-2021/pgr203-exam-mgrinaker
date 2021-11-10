package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.http.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static no.kristiania.http.HttpServer.fileTarget;
import static no.kristiania.http.HttpServer.requestTarget;

public class FileController implements HttpController{
    @Override
    public String getPath() {
        return null;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        InputStream fileResource = getClass().getResourceAsStream(fileTarget);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        String responseText = buffer.toString();

        if(fileResource != null){
            fileResource.transferTo(buffer);

            String contentType = "text/plain";
            if(requestTarget.endsWith(".txt")){
                contentType ="text/plain";
            }
            //return contentType;
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);

    }
}
