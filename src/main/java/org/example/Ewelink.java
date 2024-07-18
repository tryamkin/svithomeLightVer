package org.example;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Ewelink {
    private static final String API_URL = "https://eu-apia.coolkit.cc/v2/device/thing";
    private static String AUTHORIZATION_HEADER;
    private static final String CONTENT_TYPE_HEADER = "application/json";
    private static final String ORIGIN_HEADER = "origin: https://web.ewelink.cc";
    private static final String REQUEST_BODY = "{\"thingList\":[{\"id\":\"100063cabd\"}]}";
    private static boolean online = false;
    private static boolean repair = false;

    public static void main(String[] args) throws InterruptedException {
        Status();
    }


    public static String login (){

        Response response =   given()
                .baseUri("https://eu-apia.coolkit.cc/v2/user/login")
                .header("Authorization", "Sign ccuYCNZ+FXsBZMNTlNb2GxEP1f/4nfZg/ecXCosjZLg=")
                .header("Content-Type", CONTENT_TYPE_HEADER)
                .headers("x-ck-appid","K0OCDSvIaBWdEaU4zxlKEwk26kmshoXK")
                .header("Origin", ORIGIN_HEADER)
                .body("{\"countryCode\":\"+380\",\"password\":\"12345678\",\"lang\":\"en\",\"email\":\"tryamkin@gmail.com\"}")
                .when()
                .post()
                .then()
                .log().body()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        System.out.println("Token -  "+ jsonPath.get("data.at").toString());
        AUTHORIZATION_HEADER = "Bearer " + jsonPath.get("data.at").toString();
        return AUTHORIZATION_HEADER;
    }


    public static boolean Status (){
        if (AUTHORIZATION_HEADER==null){
            login();
        }
        Response response = given()
                .baseUri(API_URL)
                .header("Authorization", AUTHORIZATION_HEADER)
                .header("Content-Type", CONTENT_TYPE_HEADER)
                .header("Origin", ORIGIN_HEADER)
                .body(REQUEST_BODY)
                .when()
                .post()
                .then()
                // .statusCode(200) // Assert successful response (optional)
                //.log().body()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        if (jsonPath.get("error").toString().equals("401")){
            return repair = true;
        }

        Utils.showTime();
        // System.out.println("Errors - " + jsonPath.get("error").toString());
        online = jsonPath.get("data.thingList[0].itemData.online");
        System.out.println("Online - " +jsonPath.get("data.thingList[0].itemData.online").toString());
        //System.out.println("switch - " +jsonPath.get("data.thingList[0].itemData.params.switch").toString());
        //  System.out.println("pulse - " +jsonPath.get("data.thingList[0].itemData.params.pulse").toString());
        repair = false;
        return online;
    }

    public static boolean getRepairStatus (){
        return repair;
    }


}