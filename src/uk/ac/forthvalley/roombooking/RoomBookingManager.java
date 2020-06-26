package uk.ac.forthvalley.roombooking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
/**
 *  The Class that manage all the function to edit and show the class Booking,Room and Clients
 * @author Davide Kovac
 *
 */
public class RoomBookingManager {
	/**Map that stores all the Booking,can be retrieve using the booking id*/
	private Map<Integer,Booking> bookings;
	/**ArrayList keeping all the rooms*/
	private static ArrayList<Room> rooms;
	/**Map that stores all the Client,can be retrieve using the client id*/
	private Map<Integer,Client> clients;
	/**The location of the file*/
	private File storageLocation;
	/**The Booking id ,to keep it count*/
	int bookingId;
	/**The client id, to keep the count of it*/
	int clientId;
	/**
	 * The Contructor for the Room Booking Manager
	 * @param storageLocation the location of the file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
public RoomBookingManager(File storageLocation)
throws IOException,ClassNotFoundException
{
	Objects.requireNonNull(storageLocation,"File cannot be null");
	this.storageLocation=storageLocation;
	bookings=new HashMap<Integer,Booking>();
	clients= new HashMap<Integer,Client>();
	rooms=new ArrayList<Room>();
	addAllRoom();
	//Prevents error if the Files are empty.
	if(storageLocation.exists() && storageLocation.length()!=0)
	{
		loadRecords();
	}
	else
	{
		saveRecords();
	}
}
/**
 * Populate the ArrayList made of room
 */
public static void addAllRoom()
{

	rooms.add(new Room(1,0,"006",12,false,true));
	rooms.add(new Room(2,18,"008",10,true,true));
	rooms.add(new Room(3,20,"011",0,true,true));
	rooms.add(new Room(4,6,"013",0,false,false));
	rooms.add(new Room(5,18,"014",2,true,true));
	rooms.add(new Room(6,18,"015",10,true,true));
	rooms.add(new Room(7,10,"017",10,true,true));
	rooms.add(new Room(8,0,"108",20,true,false));
	rooms.add(new Room(9,0,"120",0,true,true));
	rooms.add(new Room(10,6,"301",6,true,true));
}
/**
 * Write the Client to the File, taking the information from the driver
 * @param clientEmail the email of the client
 * @param clientPhoneNumber the phonenumber of the client
 * @throws FileNotFoundException
 * @throws IOException
 * @throws ClassNotFoundException
 */
public void addClient(String clientEmail,String clientPhoneNumber) throws FileNotFoundException, IOException, ClassNotFoundException
{
	clientId=getClientLastId();
	Client c= new Client(clientId,clientEmail,clientPhoneNumber);
	clients.put(clientId,c);
	clientId++;
	saveRecords();
	System.out.println("ClientID: "+c.getClientId());
	
}
/**
 * Retrieve all the rooms id , where the number of computer requested from the client is enough
 * @param numberOfComputers the number of computers requested
 * @return The arraylist of the room with enough computers
 */
private ArrayList<Integer> getRoomsId(int numberOfComputers)
{
	
ArrayList<Integer> roomsId=new ArrayList<>();
/**Itarate throught the array to find the rooms with enough computers*/
for(Room r:rooms)
{
	if(r.getNumberOfComputers()>=numberOfComputers)
	{
		roomsId.add(r.getIdNumber());
	}
 }
return roomsId;
 }
/**
 * Check using throught the bookings Map to find which rooms are available are at the Date and Time that the user needs.
 * @param Date the date of the booking
 * @param StartTime the start time of the booking
 * @param FinishTime the finish time of the booking 
 * @param numberOfComputers the number of computers for the booking
 * @return The available rooms in an array
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws EOFException
 * @throws IOException
 */
private ArrayList<Integer> getRoomsId(LocalDate Date,LocalTime StartTime,LocalTime FinishTime,int numberOfComputers) throws FileNotFoundException, ClassNotFoundException, EOFException, IOException 
{
/**The Rooms with enough number of computers*/	
ArrayList<Integer>roomsAvailable=getRoomsId(numberOfComputers);
/**Remove safely from the list */
Iterator it=roomsAvailable.iterator();
/**inerithate throught the Rooms id to check teh available rooms */
if(!roomsAvailable.isEmpty()) {
while(it.hasNext()) {
int id=(int)it.next();
for(Booking b:bookings.values()) {	
	 if(id==b.getRoomId() && Date.equals(b.getBookingDate()))
	 {
		 if(StartTime.equals(b.getStartingTime())&&FinishTime.equals(b.getFinishTime())) {
			it.remove();
		 }
		 else if(StartTime.isAfter(b.getStartingTime())&&StartTime.isBefore(b.getFinishTime()) || FinishTime.isBefore(b.getFinishTime())&&FinishTime.isAfter(b.getStartingTime()))
		 {
			 it.remove();
		 }
		 else if(StartTime.equals(b.getStartingTime()) && FinishTime.isAfter(b.getFinishTime()))
		 {
			 it.remove();
		 }
		 else if(StartTime.isBefore(b.getStartingTime())&&FinishTime.equals(b.getFinishTime()))
		 {
			 it.remove();
		 }
		
	 }  
   }
   
}
/**If there were no data retrieve from the getRoomsId() method , that means there is no room with enough computers */
}else
   {
	   System.out.println("No room available with that Number of Computers");
   }
return roomsAvailable;
}
/**
 * This Methods check that the room given is teh best available(closest number of computers)
 * @param numberOfComputers the number of computers
 * @param Date date of the booking
 * @param StartTime start time of the booking
 * @param FinishTime finish time of the booking
 * @return The id of the best available matching room
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws EOFException
 * @throws IOException
 */
private int getBestMatch(int numberOfComputers,LocalDate Date,LocalTime StartTime,LocalTime FinishTime) throws FileNotFoundException, ClassNotFoundException, EOFException, IOException
{
	/**Get all the available rooms after checking the number of computers , and if his available at requested time or date*/
	ArrayList<Integer>roomsAvailable=getRoomsId(Date,StartTime,FinishTime,numberOfComputers);
	/**this store the id of the best fit room */
	int bestFitId=0;
	/**The difference between the computers requested and the one of the available rooms */
	int computerDifference;
	/**the difference of the best match at the moment, is a huge number so at the start the first room is going to become the best match */
	int bestMatchDifference=1000;
	/**check if there are available rooms,if there arent return a 0 */
	if(!roomsAvailable.isEmpty())
	{
		/**Iterate trought the room */
		for(Room r:rooms)
		{
			/**iterate trought the available room */
			for(int id:roomsAvailable)
			{
				/**If a room is on the list then do the check of the difference*/
				if(id==r.getIdNumber())
				{
					/**Get the difference between the requested computers,0 is best fit , less the best */
					computerDifference=r.getNumberOfComputers()-numberOfComputers;
				    /**Compare the difference between the best match for now and the one just checked,if the number is smaller then change the best fit */
					if(computerDifference<=bestMatchDifference)
					{
						/**If the if statement is true, then change the best match difference */
						bestMatchDifference=computerDifference;
						/**change the id of the best fit */
						bestFitId=id;
					}
				}
			}
		}
	}
	else
	{
		return 0;
	}
	return bestFitId;
		
}
/**
 * Actually write if a  booking  is found in the file, and add them to the Hash Map
 * @param clientID The id of the client of the booking
 * @param numberOfComputers The number of computers in the booking
 * @param Date The Date of the booking
 * @param StartTime The starting time of the booking
 * @param FinishTime The finishing time of the booking
 * @throws FileNotFoundException
 * @throws IOException
 * @throws ClassNotFoundException
 */
public void addBooking(int clientID,int numberOfComputers,LocalDate Date,LocalTime StartTime,LocalTime FinishTime) throws FileNotFoundException, IOException, ClassNotFoundException
{ 
	/**Retrieve last ID */
	bookingId=getBookingLastId();
	/**get the best matching ID for the room*/
	int roomId=getBestMatch(numberOfComputers,Date,StartTime,FinishTime);
	/** Check if no room was found*/
	if(roomId!=0) {
    /**Check if that the Start time is before the Finish Time */
	if(StartTime.isBefore(FinishTime))
		{
		      /**Create the booking */
			  Booking b=new Booking(bookingId,Date,StartTime,FinishTime,clientID,roomId);
			  /**Add the booking to the Hash Map */
			  bookings.put(bookingId, b);
			  /**Save it to the file */
			  saveRecords();
			  /**Print out the ID found */
	    	System.out.println("Booking added , ID:"+b.getIdNumber());
		}
	/**Message if the finish time is before starting time */
	else {
		System.out.println("Finish Time before Starting Time");
	}
	}
	/**Message if no room is found */
	else{
		System.out.println("No rooms Available with that number of computers at this date and time");
		
	}
    
}
/**
 * Delete the Booking using an Id
 * @param bookingId the booking id of the booking that want to be deleted
 * @return if the booking was deleted
 * @throws FileNotFoundException
 * @throws IOException
 */
public boolean removeBooking(int bookingId) throws FileNotFoundException, IOException
{
   Booking b=bookings.remove(bookingId);
   saveRecords();
   bookingId++;
   return b!=null;
}
/**
 * Save the records on the File
 * @throws FileNotFoundException
 * @throws IOException
 */
private void saveRecords() throws FileNotFoundException,IOException
{
try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(storageLocation)))
{
	
		oos.writeObject(bookings);
		oos.writeObject(clients);
	
}
}
/**
 * Load the file into the Maps
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws IOException
 * @throws EOFException
 */
@SuppressWarnings("unchecked")
private void loadRecords()
throws FileNotFoundException,ClassNotFoundException,IOException,EOFException
{
	if(storageLocation.exists())
	{
		try(ObjectInputStream ois =new ObjectInputStream(new FileInputStream(storageLocation)))
		{
			
				bookings=(Map<Integer,Booking>)ois.readObject();
				clients=(Map<Integer,Client>)ois.readObject();
			
			    
		}
	}
	
}
/**
 * Get the Last id of the Booking
 * @return The last id on the system for booking +1
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws EOFException
 * @throws IOException
 */
public int getBookingLastId() throws FileNotFoundException, ClassNotFoundException, EOFException, IOException
{
	
	loadRecords();
	int bookingId=0;
	/**Iterate trought the system to find the latest ID for the booking */
	for(Booking b:bookings.values())
	{
		if(b.getIdNumber()>bookingId)
		{
			bookingId=b.getIdNumber();
		}
	}
	/**Add one to the ID to give the new one*/
	bookingId+=1;
	return bookingId;
	
}
/**
 * Get the Last client Id
 * @return The Last client ID +1 
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws EOFException
 * @throws IOException
 */
public int getClientLastId() throws FileNotFoundException, ClassNotFoundException, EOFException, IOException
{
	loadRecords();
	int clientId=0;
	/**Iterate throught the Map to find the last ID */
	for(Client c:clients.values())
	{
		if(c.getClientId()>clientId)
		{
			clientId=c.getClientId();
		}
	}
	/**Add one to the ID to use it for the next client*/
	clientId+=1;
	return clientId;
	
}
/**
 * Get the size of the booking Map
 * @return the size of the booking
 */
public int getBookingSize()
{
	return bookings.size();
}
/**
 * Return all the Bookings in the system to be displayed
 * @return All the booking in the system
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws IOException
 */
public ArrayList<Booking> getBooking() throws FileNotFoundException, ClassNotFoundException, IOException
{
	loadRecords();
	ArrayList<Booking> allBooking = new ArrayList<>(bookings.values());
	return allBooking;
}
/**
 * Return the size of the Client Map
 * @return the size of the client 
 */
public int getClientSize()
{
	return clients.size();
}
/**
 * Return all the Clients in the system to be displayed
 * @return All the Clients in the system
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws IOException
 */
public ArrayList<Client> getClient() throws FileNotFoundException, ClassNotFoundException, IOException
{
	loadRecords();
	ArrayList<Client> allClient = new ArrayList<>(clients.values());
	return allClient;
}
public String roomNumber(int roomId)
{
	String roomNumber="";
	for(Room r:rooms)
	{
		if(r.getIdNumber()==roomId)
		{
			roomNumber=r.getRoomNumber();
		}
	}
	return roomNumber;
}
}
