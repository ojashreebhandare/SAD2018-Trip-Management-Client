import jdbc.ConnectionDb;
import pojo.TicketPOJO;
import pojo.TripPOJO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class TripBean implements Serializable {
    private static   final long serialVersionUID= 1L;
    private ConnectionDb dbConnector = ConnectionDb.getInstance();


    public List<TripPOJO> getAllTrips() {
        List<TripPOJO> items = new ArrayList<TripPOJO>();

        try {
            String sql = "SELECT * FROM trips ";
            ResultSet allTripsResultSet = this.dbConnector.selectQuery(sql);
            if (allTripsResultSet == null) {
                System.out.println("NULLLLLLLLLLLLLLLLLLLL");
            }

            while(allTripsResultSet.next()) {
                TripPOJO item = new TripPOJO();

                item.setPackage_id( allTripsResultSet.getInt("package_id"));
                item.setPackage_name(allTripsResultSet.getString("package_name"));
                item.setSource(allTripsResultSet.getString("source"));
                item.setDestination( allTripsResultSet.getString("destination"));
                item.setDeparture_date( allTripsResultSet.getString("departure_date"));
                item.setHotel_name( allTripsResultSet.getString("hotel_name"));
                item.setNo_of_nights( allTripsResultSet.getInt("no_of_nights"));
                item.setMode_of_transport( allTripsResultSet.getString("mode_of_transport"));
                item.setTrip_desc( allTripsResultSet.getString("trip_desc"));
                item.setPackage_cost( allTripsResultSet.getInt("package_cost"));
                item.setDiscount( allTripsResultSet.getInt("discount"));
                items.add(item);
            }
        } catch (Exception var3) {
            System.out.println(var3);
        }
        return items;
    }
    public List<TicketPOJO> getBookedTrips(){
        List<TicketPOJO> items = new ArrayList<TicketPOJO>();

        try {
            ResultSet allBookingResultSet = dbConnector.selectQuery("SELECT * FROM ticket");
            if (allBookingResultSet == null) {
                System.out.println("NULLLLLLLLLLLLLLLLLLLL");
            }
            while(allBookingResultSet.next()) {
                TicketPOJO item = new TicketPOJO();

                item.setPackage_id(allBookingResultSet.getInt("package_id"));
                item.setTicket_no(allBookingResultSet.getInt("ticket_no"));
                item.setUser_id(allBookingResultSet.getInt("user_id"));

                items.add(item);
            }
        } catch (Exception var3) {
            System.out.println(var3);
        }
        return items;
    }

}
