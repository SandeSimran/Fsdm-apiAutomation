import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Simran sande on May 4 , 2020
 */


public class Bookingid {

    @SerializedName("bookingid")
    @Expose
    private Integer bookingid;
    @SerializedName("booking")
    @Expose
    private Bookingdetails booking;

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public Bookingdetails getBooking() {
        return booking;
    }

    public void setBooking(Bookingdetails booking) {
        this.booking = booking;
    }

}