import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Application {


    public static void main(String[] args) {

        // String dateParam = "Thu Jul 11 2019 00:00:00 GMT+1000 (Australian Eastern Standard Time)";
        // "Thu Jul 11 2019"
        // "E, MMMM d, yyyy",   // Wed, March 14, 2001 => Tue, January 1, 2013
        // String dateParam = "Thu Jul 11 2019";
        // LocalDate date = LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("EEE MMMM d yyyy"));

        String dateParam = "Thu Jul 11 2019";

        LocalDate date = LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("EEE MMM d yyyy"));



    }
}
