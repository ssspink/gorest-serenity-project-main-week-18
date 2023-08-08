package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {
     static int id;
     static String name = "Pinkal" + TestUtils.getRandomValue();      // intializing variable from pojo class apart from id as alwyas new id is generated only in deleting reocrod with id we give specific id we wanted to delete
     static String email = "pink" + TestUtils.getRandomValue() +"@gmail.com";
     static String gender = "male";
     static String status = "active";
     @Steps
     UserSteps userSteps;

     @Title("Creating a new user")
     @Test
     public void test01(){
          ValidatableResponse response = userSteps.createUser(name,email,gender,status);
          response.log().all().statusCode(201);
     }
     @Title("Verify user is created by id")
     @Test
     public void test02(){
         HashMap<String, Object> userMap= userSteps.getUersDetailBYId(id);
          Assert.assertThat(userMap, hasValue(id));
         id = (int) userMap.get("id");

//          @Title("This method will create a new users record and verify it by its ID")
//          @Test
//          public void test002() {
//               ValidatableResponse getId = userSteps.createUser(name, email, gender, status);
//               id = getId.extract().path("id");
//               ValidatableResponse response = userSteps.getUersDetailBYId();
//               ArrayList<?> userId = response.extract().path("id");
//               Assert.assertTrue(userId.contains(id));
//          }

     }
     @Title("Updating the user and verifying updated information.")
     @Test
     public void test003() {
          name = name + "-Updated";
          userSteps.updateUser(id, name, email, gender, status)
                  .log().all().statusCode(200);
          HashMap<String, Object> userMap = userSteps.getUersDetailBYId(id);
          Assert.assertThat(userMap, hasValue(id));
     }

     @Title("Deleting the user and verifying user was deleted.")
     @Test
     public void test004() {
          userSteps.deleteUserRecord(id).statusCode(204);
          userSteps.getUersDetailBYId(id);

     }
}



