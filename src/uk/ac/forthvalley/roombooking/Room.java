package uk.ac.forthvalley.roombooking;

import java.io.Serializable;
/**
 * Class for the Room object
 * @author Davide Kovac
 *
 */
public class Room implements Serializable {
	/**Serialization version for the Room class*/
	private static final long serialVersionUID=1l;
	/**The room id , that is unique*/
	private int roomId;
	/**The number of seats in the room*/
	private int numberOfSeats;
	/**The Room number*/
	private String roomNumber;
	/**The number of computer in the room*/
	private int numberOfComputers;
	/**The number of the breakouts in the room*/
	private int numberOfBreakouts;
	/**Boolean to tell if the printer is available*/
	private boolean printerAvailability;
	/**Boolean to tell if the smartboard is available*/
	private boolean smartboardAvailability;
	/**
	 * The Constructor for the room
	 * @param roomId Id for the room
	 * @param numberOfSeats Number of the seats in the room
	 * @param numberOfComputers Number of computers in the room
	 * @param roomNumber Number of the room
	 * @param numberOfBreakouts Number of the breakouts in the room
	 * @param printerAvailability If the printer is available
	 * @param smartboardAvailability If the smartboard is available
	 */
	public Room(int roomId, int numberOfSeats,int numberOfComputers, String roomNumber, int numberOfBreakouts, boolean printerAvailability,
			boolean smartboardAvailability) {
		this.roomId = roomId;
		this.numberOfSeats = numberOfSeats;
		this.numberOfComputers=numberOfComputers;
		this.roomNumber = roomNumber;
		this.numberOfBreakouts = numberOfBreakouts;
		this.printerAvailability = printerAvailability;
		this.smartboardAvailability = smartboardAvailability;
	}
	/**
	 * Set the room id
	 * @param roomId
	 */
	public void setIdNumber(int roomId)
	{
		this.roomId=roomId;
	}
	/**
	 * @return The id number for the room
	 */
	public int getIdNumber()
	{
		return roomId;
	}
    /**
     * @return the number of seats
     */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
    /**
     * Set the number of seats
     * @param numberOfSeats
     */
	private void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	/**
	 * @return the number of computers
	 */
	public int getNumberOfComputers() {
		return numberOfComputers;
	}
    /**
     * Set the number of computers
     * @param numberOfComputers
     */
	private void setNumberOfComputers(int numberOfComputers) {
		this.numberOfComputers = numberOfComputers;
	}
	/**
	 * @return room number
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
    /**
     * Set the room number
     * @param roomNumber
     */
	private void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
    /**
     * @return the number of breakouts
     */
	public int getNumberOfBreakouts() {
		return numberOfBreakouts;
	}
    /**
     * Set the number of breakouts
     * @param numberOfBreakouts
     */
	private void setNumberOfBreakouts(int numberOfBreakouts) {
		this.numberOfBreakouts = numberOfBreakouts;
	}
    /**
     * @return if the printer is available
     */
	public boolean isPrinterAvailability() {
		return printerAvailability;
	}
    /**
     * Set the printer availability
     * @param printerAvailability
     */
	private void setPrinterAvailability(boolean printerAvailability) {
		this.printerAvailability = printerAvailability;
	}
    /**
     * @return  if the smartboard is available
     */
	public boolean isSmartboardAvailability() {
		return smartboardAvailability;
	}
    /**
     * Set the if the smartboard is available
     * @param smartboardAvailability
     */
	private void setSmartboardAvailability(boolean smartboardAvailability) {
		this.smartboardAvailability = smartboardAvailability;
	}
	
}