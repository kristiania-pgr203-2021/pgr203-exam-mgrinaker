package no.kristiania.http;

import no.kristiania.person.RoleDao;

import java.sql.SQLException;

public class RoleOptionsController implements no.kristiania.http.HttpController {
    private RoleDao roleDao;

    public RoleOptionsController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public String getPath() {
        return "/api/roleOptions";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String responseText = "";
        String contentType = "text/html; charset=utf-8";

        int value = 1;
        for (String role : roleDao.listAll()) {
            responseText += "<option value=" + (value++) + ">" + role + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
