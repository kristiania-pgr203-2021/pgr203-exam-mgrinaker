package no.kristiania.http;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookiesController extends CookieHandler {
    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> cookie = retrieveCookies(uri, requestHeaders);
        map.put("Cookie", cookie);
        return Collections.unmodifiableMap(map);
    }

    private List<String> retrieveCookies(URI uri, Map<String, List<String>> requestHeaders) {
        return null;
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
        List cookies = (List) responseHeaders.get("Set-Cookie2");
        if (cookies != null) {
            // save the cookies in a cookie cache
            storeCookies(uri, cookies);
        }
    }

    private void storeCookies(URI uri, List cookies) {
        System.out.println(cookies);
    }
}
