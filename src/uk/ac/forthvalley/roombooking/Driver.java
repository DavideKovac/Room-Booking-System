package uk.ac.forthvalley.roombooking;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
/**
 * The Class where the user insert info for the Bookings and the Client
 * @author Davide Kovac
 *
 */
public class Driver {
	/**The File name for the file that store the bookings*/
    private static final String fileNameBooking="BookingStorage.bin";
    /**The RoomBookingManager that manage booking methods */
    private static RoomBookingManager managerbooking;
    /**The File name for the file that store the clients */
    private static final String fileNameClient="ClientStorage.bin";
    /**The RoomBookingManager that manage client methods */
    private static RoomBookingManager managerclient;
	public static void main(String[] args) throws ClassNotFoundException, IOException  {
		initializeSystem();
		Menu();
	   
	    
	}
	/**
	 * Initialize the files for the different RoomBookingManager
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static void initializeSystem()
			throws FileNotFoundException, IOException, ClassNotFoundException
	{
		File fileLocationBooking = new File(fileNameBooking);
		File fileLocationClient = new File(fileNameClient);
		if (!fileLocationBooking.exists() || !fileLocationClient.exists()) {
			System.out.println("File not found");
			}
		managerbooking=new RoomBookingManager(fileLocationBooking);
		managerclient=new RoomBookingManager(fileLocationClient);
	
		}

		
	/**
	 * The Menu class, where all the user input get handled
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
    public static void Menu() throws ClassNotFoundException, IOException
    {
    	initializeSystem();
    	/**Store the choice of the client in the menu */
    	int choice;
    	do{
    	Scanner s=new Scanner(System.in);
    	System.out.println("1.Add a Client \n2.Add a Booking \n3.Remove Booking \n4.Show Bookings \n5.Show Clients \n6.Exit ");
    	/**Get the user choice */
    	choice=s.nextInt();
    	s.nextLine();
    	/**Add a Client to the system*/
    	if(choice==1)
    	{
    		System.out.println("Email: ");
    	    String email=s.nextLine();
    		System.out.println("PhoneNumber: ");
    		String phoneNumber=s.nextLine();
    		managerclient.addClient(email,phoneNumber);
    	}
    	/**Add a booking to the System*/
    	else if (choice==2)
    	{
    		System.out.println("Client Id:");
    		int clientId=s.nextInt();
    		s.nextLine();
    		System.out.println("Number Of Computers:");
    	    int numOfComp=s.nextInt();
    	    s.nextLine();
    		System.out.println("Date(dd/mm/yyyy):");
    		String dateStr=s.nextLine();
    		LocalDate date=LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    		System.out.println("Starting Time(hh:mm):");
    		String startingTimeStr=s.nextLine();
    		LocalTime startingTime=LocalTime.parse(startingTimeStr,DateTimeFormatter.ofPattern("HH:mm"));
    		System.out.println("Finish Time(hh:mm):");
    		String finishTimeStr=s.nextLine();
    		LocalTime finishTime=LocalTime.parse(finishTimeStr,DateTimeFormatter.ofPattern("HH:mm"));
    		managerbooking.addBooking(clientId,numOfComp,date,startingTime,finishTime);
    		
    	}
    	/**Delete a booking,using a booking id*/
    	else if(choice==3)
    	{
    	    System.out.println("Insert BookingID: ");
    	    int bookingId=s.nextInt();
    	    managerbooking.removeBooking(bookingId);
    	}
    	/**Display all the booking*/
    	else if(choice==4)
    	{
    		int recordCount = managerbooking.getBookingSize();
    		ArrayList<Booking> bookingList =managerbooking.getBooking();

    		System.out.println("------\nBooking list.\n");
    		System.out.printf("Number of bookings: %d\n", recordCount);
    		for (Booking b : bookingList) {
    			System.out.println("ID:"+b.getIdNumber()+" Date:"+b.getBookingDate()+" Start Time: "+b.getStartingTime()+" Finish Time:"+b.getFinishTime()+" Client Id: "+b.getClientId()+" Room Id: "+b.getRoomId());
    		}
    		System.out.print("\n-------------\n");
    	}
    	/**Display all the client*/
    	else if(choice==5)
    	{
    		int recordCount = managerclient.getClientSize();
    		ArrayList<Client> clientList =managerclient.getClient();

    		System.out.println("------\nClient list.\n");
    		System.out.printf("Number of clients: %d\n", recordCount);
    		for (Client c : clientList) {
    			System.out.println("ID:"+c.getClientId()+" Email:"+c.getClientEmail()+" Client PhoneNumber:"+c.getClientNumber());
    		}
    		System.out.print("\n-------------\n");
    	}
    	/**Give a message to the user that is exiting the menu */
    	else if(choice==6)
    	{
    		System.out.println("Exiting Menu");
    	}
    	/**Give the message at the user if a wrong input is given*/
    	else
    	{
    		System.out.println("No menu choice");
    	}
    	}while(choice!=6);
    }
}