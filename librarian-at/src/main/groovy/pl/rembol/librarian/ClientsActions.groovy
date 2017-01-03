package pl.rembol.librarian

import groovy.json.JsonBuilder
import io.restassured.RestAssured
import io.restassured.http.ContentType

class ClientsActions {

    Map<String, Integer> clients = [:]

    void createClient(String name) {
        clients[name] = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new JsonBuilder([name: name]).toString())
                .when()
                .post("/clients")
                .body
                .jsonPath()
                .getInt("id")
    }

    String findClient(String name) {
        RestAssured
                .when()
                .get("/clients/${clients[name]}")
                .body
                .jsonPath()
                .getString("name")
    }
}
