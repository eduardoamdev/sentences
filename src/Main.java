import java.io.*;
import java.net.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Connection conn = Postgresql.getConnection();
        int port = 3000;
        Templates templates = new Templates();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                if (line.contains("GET /home ")) {
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                    out.println(templates.buildTemplate("main"));
                    break;
                } else if (line.contains("GET /newSentence ")) {
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                    out.println(templates.buildTemplate("newSentence"));
                    break;
                } else if (line.contains("POST /newSentence ")) {
                    int contentLength = 0;
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        if (line.startsWith("Content-Length: ")) {
                            contentLength = Integer.parseInt(line.substring("Content-Length: ".length()));
                        }
                    }
                    char[] bodyChars = new char[contentLength];
                    in.read(bodyChars);
                    String body = new String(bodyChars);
                    String key = "\"sentence\"";
                    int startIndex = body.indexOf(key) + key.length() + 2;
                    int endIndex = body.indexOf("\"", startIndex);
                    String sentence = body.substring(startIndex, endIndex);
                    try (Statement stmt = conn.createStatement()) {
                        String sql = "INSERT INTO \"CRUD\".sentences (sentence) VALUES ('" + sentence + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("A new sentence has been inserted.");
                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: application/json");
                        out.println();
                        out.println("{" +
                                "\"severity\": \"INFO\"," +
                                "\"message\": \"Sentence successfully created\"" +
                                "}");
                        break;
                    } catch (SQLException error) {
                        String errorStatus;
                        String errorMessage;
                        if (error.getMessage().contains("duplicate key")) {
                            errorStatus = "400 BAD_REQUEST";
                            errorMessage = "That sentence already exists";
                        }else {
                            errorStatus = "500 SERVER_ERROR";
                            errorMessage = "There is an error";
                        }
                        System.out.println("There is the following error: " + error.getMessage());
                        out.println("HTTP/1.1" + errorStatus);
                        out.println("Content-Type: application/json");
                        out.println();
                        out.println("{" +
                                "\"severity\": \"ERROR\"," +
                                "\"message\":\"" + errorMessage + "\"" +
                                "}");
                        break;
                    }
                } else if (line.contains("GET /sentences ")) {
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                    out.println(templates.buildTemplate("sentences"));
                    break;
                } else if (line.contains("GET /fiveSentences ")) {
                    try (Statement stmt = conn.createStatement()) {
                        String sql = "SELECT * FROM \"CRUD\".sentences";
                        ResultSet result = stmt.executeQuery(sql);
                        ArrayList<String> sentences = new ArrayList<>();
                        while (result.next()) {
                            sentences.add("\"" + result.getString("sentence") + "\"");
                        }
                        System.out.println("Sentences got from database: " + sentences);
                        out.println("HTTP/1.1 200 OK");
                        out.println("application/json");
                        out.println();
                        out.println("{" + "\"severity\": \"INFO\"," +
                                "\"message\": \"Sentence successfully got\"," +
                                "\"info\":" + sentences  +
                                "}");
                        break;
                    } catch (SQLException error) {
                        System.out.println("There is the following error: " + error.getMessage());
                        out.println("HTTP/1.1 500 SERVER_ERROR" );
                        out.println("Content-Type: application/json");
                        out.println();
                        out.println("{" +
                                "\"severity\": \"ERROR\"," +
                                "\"message\":\"SERVER_ERROR\"" +
                                "}");
                        break;
                    }
                }
            }
            in.close();
            out.close();
            socket.close();
        }
    }
}
