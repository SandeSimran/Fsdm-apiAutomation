import org.testng.asserts.SoftAssert;

/**
 * Created by Simran sande on May 4 , 2020
 */


public class Helper {

    static SoftAssert sa = new SoftAssert();
    /**
     * regex which represents the below date format
     * Ex: "YYYY-MM-DD"
     * "2020-02-01"
     */
    static String dateFormat = "(\\d{4})-(\\d{2})-(\\d{2})";

    public static Bookingid globalBookingId;

    public static void setBookingId(Bookingid bId) {
        globalBookingId = bId;
    }

    public static Bookingid getBookingId() {
        return globalBookingId;
    }

    /**
     * Asserting booking details with booking id
     */
    public static void bookingIdAssertion( String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneed) {

        Bookingdetails bookingdetails = globalBookingId.getBooking();
        sa.assertNotEquals(globalBookingId.getBookingid(),null,"Booking id is null");
        sa.assertEquals(bookingdetails.getFirstname(), firstname, "Expected name is " + firstname + " but geting " + bookingdetails.getFirstname());
        sa.assertEquals(bookingdetails.getLastname(), lastname, "Expected last name is " + lastname + " but geting " + bookingdetails.getLastname());
        sa.assertEquals(bookingdetails.getDepositpaid().toString(), depositpaid, "Expected deposit paid is " + depositpaid + " but geting " + bookingdetails.getDepositpaid());
        sa.assertEquals(bookingdetails.getTotalprice().toString(), totalprice, "Expected total prize is " + totalprice + " but geting " + bookingdetails.getTotalprice());
        sa.assertEquals(bookingdetails.getAdditionalneeds(), additionalneed, "Expected additional nedd is " + additionalneed + " but geting " + bookingdetails.getAdditionalneeds());

        sa.assertEquals(bookingdetails.getBookingdates().getCheckin(), checkin, "Expected checkin is " + checkin + " but geting " + bookingdetails.getBookingdates().getCheckin());
        sa.assertEquals(bookingdetails.getBookingdates().getCheckout(), checkout, "Expected checkout is " + checkout + " but geting " + bookingdetails.getBookingdates().getCheckout());

        String chekinDate = bookingdetails.getBookingdates().getCheckin();
        String chekOutDate = bookingdetails.getBookingdates().getCheckout();
        if(chekinDate.matches(dateFormat)){
            sa.assertTrue(true, "This will succeed.");
        }
        if(chekOutDate.matches(dateFormat)){
            sa.assertTrue(true, "This will succeed.");
        }
        sa.assertAll();

    }

    /**
     * Asserting booking with booking details
     */
    public static void bookingAssertion(Bookingdetails bookingdetails, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneed) {

        sa.assertEquals(bookingdetails.getFirstname(), firstname, "Expected name is " + firstname + " but geting " + bookingdetails.getFirstname());
        sa.assertEquals(bookingdetails.getLastname(), lastname, "Expected last name is " + lastname + " but geting " + bookingdetails.getLastname());
        sa.assertEquals(bookingdetails.getDepositpaid().toString(), depositpaid, "Expected deposit paid is " + depositpaid + " but geting " + bookingdetails.getDepositpaid());
        sa.assertEquals(bookingdetails.getTotalprice().toString(), totalprice, "Expected total prize is " + totalprice + " but geting " + bookingdetails.getTotalprice());
        sa.assertEquals(bookingdetails.getAdditionalneeds(), additionalneed, "Expected additional nedd is " + additionalneed + " but geting " + bookingdetails.getAdditionalneeds());

        sa.assertEquals(bookingdetails.getBookingdates().getCheckin(), checkin, "Expected checkin is " + checkin + " but geting " + bookingdetails.getBookingdates().getCheckin());
        sa.assertEquals(bookingdetails.getBookingdates().getCheckout(), checkout, "Expected checkout is " + checkout + " but geting " + bookingdetails.getBookingdates().getCheckout());


        String chekinDate = bookingdetails.getBookingdates().getCheckin();
        String chekOutDate = bookingdetails.getBookingdates().getCheckout();
        if(chekinDate.matches(dateFormat)){
            sa.assertTrue(true, "This will succeed.");
        }
        if(chekOutDate.matches(dateFormat)){
            sa.assertTrue(true, "This will succeed.");
        }
        sa.assertAll();
        sa.assertAll();

    }

    /**
     *  Assertion for negative test senarios
     */
    public static void assertEqual(String expected, String actual) {
        sa.assertEquals(expected, actual);
        sa.assertAll();

    }
}