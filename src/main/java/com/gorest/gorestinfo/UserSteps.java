package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {
    static final String token = "Bearer f455785d32ec6c5ff6987faae9fdd5bfa1d1b7e1f450028198ae63d39e0cc645";

    @Step("Creating a new user with name : {0}, email : {1}, gender : {2}, status : {3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .body(userPojo)
                .post(EndPoints.CREATE_USER)
                .then().log().all().statusCode(201);
    }

    @Step("Getting all users records")
    public ValidatableResponse getUsersDetail() {
        return SerenityRest.given().log().all()
                .header("Authorization", token)
                .header("Connection", "keep-alive")
                .when()
                .get(EndPoints.GET_USERS)
                .then().log().all().statusCode(200);
    }

    @Step("Getting a user by id : {0} ")
    public HashMap<String ,Object> getUersDetailBYId(int id) {
        return (HashMap<String, Object>) SerenityRest.given().log().all()
                .header("Authorization", token)
                .header("Connection", "keep-alive")
                .pathParam("Id", id)
                .when()
                .get()
                .then().log().all().statusCode(200);
    }
    @Step("Getting user information from the email : {0}")
    public HashMap<String,Object> getInfoByEmail(String email){
        String p1 = "findAll{it.email == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .header("Authorization","Bearer f455785d32ec6c5ff6987faae9fdd5bfa1d1b7e1f450028198ae63d39e0cc645")
                .get()
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("findAll{it.email == '" + email + "'}.get(0)");
    }




    @Step("Update user details id: {0}, name: {1}, email: {2}, gender: {3} and status: {4}")
    public ValidatableResponse updateUser(int id, String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("id", id)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then().log().all().statusCode(200);
    }
    @Step("Deleting existing user record with id: {0}")
    public ValidatableResponse deleteUserRecord(int id) {
        return SerenityRest.given().log().all()
                .header("Authorization", token)
                .header("Connection", "keep-alive")
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then().log().all();
    }

}
