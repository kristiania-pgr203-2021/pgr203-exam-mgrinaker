package no.kristiania.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static no.kristiania.http.HttpServer.fileTarget;
import static no.kristiania.http.HttpServer.requestTarget;

class CheckFileExtensionController implements HttpController {
    @Override
    public String getPath() {
        return " ";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws IOException {
        InputStream fileResource = getClass().getResourceAsStream(fileTarget);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        String responseText = buffer.toString();

        if(fileResource != null){
            fileResource.transferTo(buffer);

            String contentType = "text/plain";
            if(requestTarget.endsWith(".html")){
                contentType = "text/html; charset=utf-8";
            } else if (requestTarget.endsWith(".css")) {
                contentType ="text/css; charset=utf-8";
            }
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}