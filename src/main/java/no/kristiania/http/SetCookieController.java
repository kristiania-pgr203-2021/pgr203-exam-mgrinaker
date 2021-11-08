package no.kristiania.http;

import java.io.IOException;
import java.sql.SQLException;

public class SetCookieController implements HttpController {
    @Override
    public String getPath() {
        return "/api/setCookie";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        return null;
    }
}
