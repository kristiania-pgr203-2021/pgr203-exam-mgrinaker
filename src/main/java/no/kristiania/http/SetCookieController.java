package no.kristiania.http;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static no.kristiania.http.HttpServer.query;

public class SetCookieController implements HttpController {

    @Override
    public String getPath() {
        return "/api/setCookie";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        //CookieManager cookieManager = new CookieManager();
        String yourName = "";
        String headerCookies = request.getHeader("Set-Cookie");
        //List<HttpCookie> cookies = HttpCookie.parse(headerCookies);

        /*for (HttpCookie cookie:cookies) {
            cookieManager.getCookieStore().add(null, cookie);
        }*/
        if (headerCookies != null) {
            Map<String, String> queryMap = HttpMessage.parseRequestParameters(headerCookies);
            yourName = queryMap.get("cookieName");
        }

        String responseText = "<p>Hello " + yourName + "!</p>";
        String contentType = "text/html; charset=utf-8";
        System.out.println(responseText);
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
