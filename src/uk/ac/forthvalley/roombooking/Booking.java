package uk.ac.forthvalley.roombooking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * The Class for the basic data for the Bookings
 * @author Davide Kovac
 *
 */
public class Booking implements Serializable{
/**Serialization version for the Booking class*/
private static final long serialVersionUID=1l;
/**The id for the booking class*/
private  int bookingId;
/**The date of the booking*/
private LocalDate bookingDate;
/**The starting time for the booking*/
private LocalTime startTime;
/**The finishing time for the booking*/
private LocalTime finishTime;
/**The id of the client that placed the booking*/
private int clientID;
/**The id of the room in the booking*/
private int roomID;
/**
 * Constructor for the Booking class
 * @param bookingId The id for the booking object
 * @param bookingDate The date of the booking
 * @param startTime The time that  the booking start
 * @param finishTime The time that the booking finish
 * @param clientID The id for the client that placed the booking
 * @param roomID The id of the room involved in the booking
 */
public Booking(int bookingId, LocalDate bookingDate, LocalTime startTime,LocalTime finishTime, int clientID,int roomID) {
	
	this.bookingId = bookingId;
	this.bookingDate = bookingDate;
	this.startTime = startTime;
	this.finishTime=finishTime;
	this.clientID=clientID;
	this.roomID=roomID;
}
/**
 * @return The id number for the booking
 */
public  int getIdNumber() {
	return bookingId;
} 
/**
 * Set the booking id
 * @param bookingId
 */
private void setBookingId(int bookingId) {
	this.bookingId = bookingId;
}
/**
 * @return The Date of the booking
 */
public LocalDate getBookingDate() {
	return bookingDate;
}
/**
 * Set the date for the booking
 * @param bookingDate
 */
private void setBookingDate(LocalDate bookingDate) {
	this.bookingDate = bookingDate;
}
/**
 * @return The starting time for the booking
 */
public LocalTime getStartingTime() {
	return startTime;
}
/**
 * Set the Starting time for the booking
 * @param startTime
 */
private void setStartingTime(LocalTime startTime) {
	this.startTime=startTime;
}
/**
 * @return the finish time of the booking
 */
public LocalTime getFinishTime() {
	return finishTime;
}
/**
 * Set the finish time for the booking
 * @param finishTime
 */
private void setFinishTime(LocalTime finishTime) {
	this.finishTime=finishTime;
}
/**
 * @return the client id for the booking
 */
public int getClientId()
{
	return clientID;
}
/**
 * Set the client id for the booking
 * @param clientID
 */
private void setClientId(int clientID)
{
     this.clientID=clientID;	
}
/**
 * @return the room id for the booking
 */
public int getRoomId()
{
	return roomID;
}
/**
 * Set the room id for the booking
 * @param roomID
 */
private void setRoomId(int roomID)
{
     this.roomID=roomID;	
}
}