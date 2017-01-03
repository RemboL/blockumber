package pl.rembol.librarian

import groovy.json.JsonBuilder
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.not

class BorrowActions {

    def lastOperation

    void clearAll() {
        RestAssured
                .delete("/borrows")
    }

    void borrow(String client, String book) {
        lastOperation = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new JsonBuilder([clientName: client, bookName: book]).toString())
                .when()
                .post("/borrows")
    }

    boolean isAmongstBorrowedBy(String client, String book) {
        RestAssured
                .when()
                .get("/borrows/byClient/$client")
                .jsonPath()
                .getList("")
                .any { it.book.name == book }
    }

    void "return"(String client, String book) {
        lastOperation = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new JsonBuilder([clientName: client, bookName: book]).toString())
                .when()
                .post("/borrows/return")
    }

    void operationRefused() {
        lastOperation.then().statusCode(not(equalTo(200)))
    }

    String operationMessage() {
        lastOperation.body.asString()
    }
}
