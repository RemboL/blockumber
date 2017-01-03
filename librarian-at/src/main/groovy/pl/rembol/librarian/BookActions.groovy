package pl.rembol.librarian

import groovy.json.JsonBuilder
import io.restassured.RestAssured
import io.restassured.http.ContentType

class BookActions {

    def searchResult

    void clearAll() {
        RestAssured
                .delete("/books")
    }

    void add(String name, String author) {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new JsonBuilder([name: name, author: author]).toString())
                .when()
                .post("/books")
    }

    boolean canBeFound(String name) {
        RestAssured
                .when()
                .get("/books/byName/$name")
                .jsonPath().getList("").size() > 0
    }

    boolean canBeBorrowed(String name) {
        RestAssured
                .when()
                .get("/books/byName/$name")
                .jsonPath().getList("")
                .any { it.available > 0 }
    }

    void searchByAuthor(String author) {
        searchResult = RestAssured
                .when()
                .get("/books/byAuthor/$author")
                .jsonPath().getList("")
    }

    boolean isFound(String book) {
        searchResult.any { it.name == book }
    }
}
