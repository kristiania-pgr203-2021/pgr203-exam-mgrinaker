package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SetCookieController implements HttpController {
    public final HashMap<String, String> cookieFields = new HashMap<>();

    @Override
    public String getPath() {
        return "/api/setCookie";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String yourName = "";
        String cookieHeaders = request.getHeader("Cookie");

        if (cookieHeaders != null) {
            Map<String, String> queryMap = HttpMessage.parseRequestParameters(cookieHeaders);
            yourName = queryMap.get("firstName");
        }

        String responseText = "<p>Hello " + yourName + "!</p>";
        System.out.println(responseText);
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}




