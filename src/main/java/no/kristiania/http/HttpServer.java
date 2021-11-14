package no.kristiania.http;

import no.kristiania.http.controllers.HttpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final HashMap<String, HttpController> controllers = new HashMap<>();
    public static String requestTarget;
    public static String query = null;
    public static String fileTarget;
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);
        new Thread(this::handleClients).start();
    }

    private void handleClients() {
        try {
            while (true){
                handleClient();
            }
        } catch (IndexOutOfBoundsException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException, SQLException {
        Socket clientSocket = serverSocket.accept();

        HttpMessage httpMessage = new HttpMessage(clientSocket);
        String[] requestLine = httpMessage.startLine.split(" ");
        requestTarget = requestLine[1];

        int questionPos = requestTarget.indexOf('?');

        if (questionPos != -1){
            fileTarget = requestTarget.substring(0, questionPos);
            query = requestTarget.substring(questionPos + 1);
        } else {
            fileTarget = requestTarget;
        }

        if(controllers.containsKey(fileTarget)){
            HttpMessage response = controllers.get(fileTarget).handle(httpMessage);
            response.write(clientSocket);
        } else {
            InputStream fileResource = getClass().getResourceAsStream(fileTarget);
            if(fileResource != null) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                fileResource.transferTo(buffer);
                String responseText = buffer.toString();

                String contentType = "text/plain";
                if (requestTarget.endsWith(".html")) {
                    contentType = "text/html; charset=utf-8";
                } else if (requestTarget.endsWith(".css")) {
                    contentType = "text/css";
                }
                writeOkResponse(clientSocket, responseText, contentType);
                return;
            }else{
                String responseText = "File not found: " + requestTarget;

                String response = "HTTP/1.1 404 Not found\r\n" +
                        "Content-Length: " + responseText.length() + "\r\n" +
                        "Content-Type: text/html; charset=utf-8\r\n" +
                        "Connection: close\r\n" +
                        "\r\n" +
                        responseText;
                clientSocket.getOutputStream().write(response.getBytes());
            }

            String responseText = "Statuscode 500";

            String response = "HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "Content-Type: text/html; charset=utf-8\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());

            logger.info("Oh no, something went terribly wrong. Statuscode 500.");
        }
    }

    private void writeOkResponse(Socket clientSocket, String responseText, String contentType) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseText.getBytes().length + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseText;

        clientSocket.getOutputStream().write(response.getBytes());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void addController(HttpController controller) {
        controllers.put(controller.getPath(), controller);
    }
}
