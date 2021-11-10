package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public interface HttpController {

    String getPath();

    HttpMessage handle(HttpMessage request) throws SQLException, IOException;
}
