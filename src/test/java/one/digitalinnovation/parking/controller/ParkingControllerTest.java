package one.digitalinnovation.parking.controller;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTest extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth().basic("user", "12345")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenFindByIdThenCheckResult() {
        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("WRT-5555");
        parkingCreateDTO.setModel("BRASILIA");
        parkingCreateDTO.setState("SP");

        String id = RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .extract().path("id");

        System.out.println("id:" + id);

        RestAssured.given()
                .auth().basic("user", "12345")
                .when()
                .get("/parking/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("license", Matchers.equalTo("WRT-5555"))
                .body("color", Matchers.equalTo("AMARELO"));
    }

    @Test
    void whenFindByIdWithInvalidIdThenCheckResult() {

        String id = "asdf45d";

        System.out.println("id:" + id);

        RestAssured.given()
                .auth().basic("user", "12345")
                .when()
                .get("/parking/" + id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenCreateThenTestIsCreated() {
        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("WRT-5555");
        parkingCreateDTO.setModel("BRASILIA");
        parkingCreateDTO.setState("SP");

        RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("WRT-5555"))
                .body("color", Matchers.equalTo("AMARELO"));
    }

    @Test
    void whenUpdateThenTestIsUpdated() {
        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("WRT-5555");
        parkingCreateDTO.setModel("BRASILIA");
        parkingCreateDTO.setState("SP");

        String id = RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .extract().path("id");

        System.out.println("id:" + id);

        RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .put("/parking/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("license", Matchers.equalTo("WRT-5555"))
                .body("color", Matchers.equalTo("AMARELO"));
    }

    @Test
    void whenCheckOutThenTestIsCheckedOut() {
        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("WRT-5555");
        parkingCreateDTO.setModel("BRASILIA");
        parkingCreateDTO.setState("SP");

        String id = RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .extract().path("id");

        System.out.println("id:" + id);

        RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking/" + id + "/exit")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("exitDate", Matchers.notNullValue());
    }

    @Test
    void whenCheckOutAnAlreadyClosedParkingThenCheckResult() {
        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("WRT-5555");
        parkingCreateDTO.setModel("BRASILIA");
        parkingCreateDTO.setState("SP");

        String id = RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .extract().path("id");

        System.out.println("id:" + id);

        RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking/" + id + "/exit");

        RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking/" + id + "/exit")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void whenDeleteThenTestIsDeleted() {
        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("WRT-5555");
        parkingCreateDTO.setModel("BRASILIA");
        parkingCreateDTO.setState("SP");

        String id = RestAssured.given()
                .when()
                .auth().basic("user", "12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .extract().path("id");

        System.out.println("id:" + id);

        RestAssured.given()
                .when()
                .auth().basic("user", "12345").delete("/parking/" + id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
