package no.kristiania.http;

import java.io.IOException;
import java.sql.SQLException;

public class FileController implements HttpController{
    @Override
    public String getPath() {
        return null;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        return null;
    }
}
