package rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jdbc.ConnectionDb;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.glassfish.jersey.client.ClientConfig;
import org.xml.sax.InputSource;
import pojo.TripPOJO;

import static javax.swing.UIManager.getInt;


@ManagedBean(name = "Rest", eager = true)
@ApplicationScoped
public class rest {
    private static rest single_instance = null;
    TripXML trips;
    private ConnectionDb dbConnector = ConnectionDb.getInstance();

    public static rest getInstance()
    {
        single_instance= new rest();

        return single_instance;
    }
    public String getRequest() throws SQLException {
        try {

            ClientConfig config = new ClientConfig();
            Client client = ClientBuilder.newClient(config);
            WebTarget service = client.target(getBaseURI());


            // Get the trips
           // System.out.println(service.path("rest").path("trips").request().accept(MediaType.TEXT_XML).get(String.class));
           System.out.println(service.path("rest").path("trips").queryParam("c","4561").request().accept(MediaType.TEXT_XML).get(String.class));

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            String xml = service.path("rest").path("trips").request().accept(MediaType.TEXT_XML).get(String.class);
            Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
            System.out.println(doc.getDocumentURI());
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("tripPOJO");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    TripPOJO tripPOJO = new TripPOJO();

                    Element eElement = (Element) nNode;

                    tripPOJO.setDeparture_date(
                            eElement
                                    .getElementsByTagName("departure_date")
                                    .item(0)
                                    .getTextContent());

                    tripPOJO.setDestination(eElement
                            .getElementsByTagName("destination")
                            .item(0)
                            .getTextContent());
                    tripPOJO.setDiscount(getInt(eElement
                            .getElementsByTagName("discount")
                            .item(0)
                            .getTextContent()));
                    tripPOJO.setHotel_name(eElement
                            .getElementsByTagName("hotel_name")
                            .item(0)
                            .getTextContent());
                    tripPOJO.setMode_of_transport(eElement
                            .getElementsByTagName("mode_of_transport")
                            .item(0)
                            .getTextContent());
                    tripPOJO.setNo_of_nights(getInt(eElement
                            .getElementsByTagName("no_of_nights")
                            .item(0)
                            .getTextContent()));
                    tripPOJO.setPackage_cost(getInt(eElement
                            .getElementsByTagName("package_cost")
                            .item(0)
                            .getTextContent()));
                    tripPOJO.setPackage_id(getInt(
                            eElement
                                    .getElementsByTagName("package_id")
                                    .item(0)
                                    .getTextContent()));
                    tripPOJO.setPackage_name(eElement
                            .getElementsByTagName("package_name")
                            .item(0)
                            .getTextContent());
                    tripPOJO.setSource(eElement
                            .getElementsByTagName("source")
                            .item(0)
                            .getTextContent());
                    tripPOJO.setTrip_desc(eElement
                            .getElementsByTagName("trip_desc")
                            .item(0)
                            .getTextContent());
                    int resultCount = dbConnector.insert("INSERT INTO trips(package_name,source,destination,departure_date,hotel_name,no_of_nights,mode_of_transport,trip_desc,package_cost,discount)"
                            + " values ('" + tripPOJO.getPackage_name() + "',"
                            + "'" + tripPOJO.getSource() + "','" + tripPOJO.getDestination() + "','" + tripPOJO.getDeparture_date() + "',"
                            + "'" + tripPOJO.getHotel_name() + "','" + tripPOJO.getNo_of_nights() + "','" + tripPOJO.getMode_of_transport() + "'"
                            + ",'" + tripPOJO.getTrip_desc() + "','" + tripPOJO.getPackage_cost() + "'"
                            + ",'" + tripPOJO.getDiscount() + "')");
                    if (resultCount > 0)
                        System.out.println("yeaaaahhhh");
                    else
                        System.out.println("Error Inserting Record Please Try Again");


                }

            }return "/hello.html";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080").build();
    }

    public String getBooktickets() throws SQLException {
        try {

            ClientConfig config = new ClientConfig();
            Client client = ClientBuilder.newClient(config);
            WebTarget service = client.target(getBaseURI());


            WebTarget target = client.target("http://localhost:8080/rest/trips/{c}")
                    .resolveTemplate("c", "1111")
                    .queryParam("verbose", true);
            System.out.println(target.request().accept(MediaType.TEXT_XML).get(String.class));

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

}