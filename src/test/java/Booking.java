import com.google.gson.Gson;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.RestAssured;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.logging.Logger;

/**
 * Created by Simran sande on May 4 , 2020
 */


public class Booking {

    private Gson gg = new Gson();

    // Create a Logger
    Logger logger
            = Logger.getLogger(
            Booking.class.getName());

    /**
     * Common method which is used in every test to set the details and get the details
     */
    public Bookingdetails setterGetter(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneed) {
        Bookingdetails bookingDetails = new Bookingdetails();
        Bookingdates bookingDates = new Bookingdates();
        bookingDetails.setFirstname(firstname);
        bookingDetails.setLastname(lastname);

        if (totalprice == null) {
            bookingDetails.setTotalprice(null);
        } else {
            bookingDetails.setTotalprice(Integer.parseInt(totalprice));
        }

        if (depositpaid == null) {
            bookingDetails.setDepositpaid(null);
        } else {
            bookingDetails.setDepositpaid(Boolean.parseBoolean(depositpaid));
        }
        bookingDetails.setAdditionalneeds(additionalneed);
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);
        bookingDetails.setBookingdates(bookingDates);

        return bookingDetails;
    }

    @Parameters({"baseURI", "header_Content_Type", "header_application_json", "firstname", "lastname", "totalprice", "depositpaid", "checkin", "checkout", "additionalneeds"})
    @Test
    public void createBooking(String baseURI, String header_Content_Type, String header_application_json, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneed) {

        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(header_Content_Type, header_application_json);
        Bookingdetails bookingDetails = setterGetter(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneed);
        httpRequest.body(bookingDetails);
        Response response = httpRequest.request(Method.POST);
        Helper.setBookingId(gg.fromJson(response.asString(), Bookingid.class));
        logger.info("Booking created  ------------->" + response.body().asString());
        Helper.bookingIdAssertion(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneed);
    }

    @Parameters({"baseURI", "header_Content_Type", "header_application_json", "firstname", "lastname", "totalprice", "depositpaid", "checkin", "checkout", "additionalneeds"})
    @Test
    public void getBooking(String baseURI, String header_Content_Type, String header_application_json, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneed) {

        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(header_Content_Type, header_application_json);
        /**
         * Creating get endpoint with the id
         */
        String getURL = baseURI + "/" + Helper.getBookingId().getBookingid().toString();
        RequestSpecification httpRequest_get = RestAssured.given();
        httpRequest_get.baseUri(getURL);
        Response getResponse = httpRequest_get.request(Method.GET);
        logger.info("GET response of booking ----------->" + getResponse.body().asString());
        Bookingdetails cc = gg.fromJson(getResponse.asString(), Bookingdetails.class);
        Helper.bookingAssertion(cc, firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneed);

    }

    @Parameters({"baseURI", "header_Content_Type", "header_application_json", "firstname", "lastname", "totalprice", "depositpaid", "checkin", "checkout", "additionalneeds", "invalid_date"})
    @Test
    public void createBookingWrongChekoutValue(String baseURI, String header_Content_Type, String header_application_json, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneed, String invalid_date) {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(header_Content_Type, header_application_json);
        Bookingdetails bookingDetails = setterGetter(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneed);
        httpRequest.body(bookingDetails);
        Response response = httpRequest.request(Method.POST);
        logger.info("After providing XYZ as checkout value --------->" + response.body().asString());
        Helper.assertEqual(response.body().asString(), invalid_date);

    }

    @Parameters({"baseURI", "header_Content_Type", "header_application_json", "firstname", "lastname", "totalprice", "depositpaid", "checkin", "additionalneeds", "internal_server_error"})
    @Test
    public void createBookingWitNullParameter(String baseURI, String header_Content_Type, String header_application_json, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String additionalneed, String internal_server_error) {

        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(header_Content_Type, header_application_json);
        Bookingdetails bookingDetails = setterGetter(firstname, lastname, totalprice, depositpaid, checkin, null, additionalneed);
        httpRequest.body(bookingDetails);
        Response response = httpRequest.request(Method.POST);
        logger.info("fter providing Chekout value as NULL ------->" + response.body().asString());
        Helper.assertEqual(response.body().asString(), internal_server_error);

    }

    @Parameters({"baseURI", "header_Content_Type", "header_application_json", "Authorisation", "basicAuth", "updated_firstname", "updated_lastname", "updated_totalprice", "updated_depositpaid", "updated_checkin", "updated_checkout", "updated_additionalneeds"})
    @Test
    public void updateBooking(String baseURI, String header_Content_Type, String header_application_json,String Authorisation, String basicAuth,  String updated_firstname, String updated_lastname, String updated_totalprice, String updated_depositpaid, String updated_checkin, String updated_checkout, String updated_additionalneeds) {

        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(header_Content_Type, header_application_json);

        /**
         * Creating get endpoint with the id
         */
        String getURL = baseURI + "/" + Helper.getBookingId().getBookingid().toString();
        RequestSpecification httpRequest_get = RestAssured.given();
        httpRequest_get.baseUri(getURL);

        Response getResponse = httpRequest_get.request(Method.GET);

        logger.info("Before updation the detail is  ------->" + getResponse.body().asString());
        RequestSpecification httpRequest_put = RestAssured.given();
        httpRequest_put.header(header_Content_Type, header_application_json);
        httpRequest_put.header(Authorisation, basicAuth);
        /**
         * used same URL which is used for GET request as the endpoint is same with id
         */
        httpRequest_put.baseUri(getURL);
        Bookingdetails updated_bookingDetails = setterGetter(updated_firstname, updated_lastname, updated_totalprice, updated_depositpaid, updated_checkin, updated_checkout, updated_additionalneeds);
        httpRequest_put.body(updated_bookingDetails);
        Response updatedResponse = httpRequest_put.request(Method.PUT);
        logger.info("After updation the response is ------->" + updatedResponse.body().asString());
        Bookingdetails cc = gg.fromJson(updatedResponse.asString(), Bookingdetails.class);

        Helper.bookingAssertion(cc, updated_firstname, updated_lastname, updated_totalprice, updated_depositpaid, updated_checkin, updated_checkout, updated_additionalneeds);

    }

    @Parameters({"baseURI", "header_Content_Type", "header_application_json", "Authorisation", "basicAuth" ,"updated_firstname", "updated_lastname"})
    @Test
    public void partialUpdateBooking(String baseURI, String header_Content_Type, String header_application_json, String Authorisation, String basicAuth ,String updated_firstname, String updated_lastname) {

        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(header_Content_Type, header_application_json);

        /**
         * Creating get endpoint with the id
         */
        String getURL = baseURI + "/" + Helper.getBookingId().getBookingid().toString();
        RequestSpecification httpRequest_get = RestAssured.given();
        httpRequest_get.baseUri(getURL);
        Response getResponse = httpRequest_get.request(Method.GET);
        logger.info("Before updation the detail is  ------->" + getResponse.body().asString());
        httpRequest.header(Authorisation, basicAuth);

        /**
         * used same URL which is used for GET request as the endpoint is same with id
         */
        httpRequest.baseUri(getURL);
        Bookingdetails bookingdetails=Helper.getBookingId().getBooking();
        Bookingdetails UpdatedBookingDetails = setterGetter(updated_firstname, updated_lastname, bookingdetails.getTotalprice().toString(), bookingdetails.getDepositpaid().toString(), bookingdetails.getBookingdates().getCheckin(), bookingdetails.getBookingdates().getCheckout(), bookingdetails.getAdditionalneeds());
        httpRequest.body(UpdatedBookingDetails);
        Response updatedResponse = httpRequest.request(Method.PUT);
        logger.info("After partial updation the response is ------->" + updatedResponse.body().asString());
        Bookingdetails cc = gg.fromJson(updatedResponse.asString(), Bookingdetails.class);

        Helper.bookingAssertion(cc, updated_firstname, updated_lastname, bookingdetails.getTotalprice().toString(), bookingdetails.getDepositpaid().toString(), bookingdetails.getBookingdates().getCheckin(), bookingdetails.getBookingdates().getCheckout(), bookingdetails.getAdditionalneeds());

    }

}
