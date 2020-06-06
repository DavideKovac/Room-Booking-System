package uk.ac.forthvalley.roombooking;

import java.io.Serializable;
/**
 * Class with basic Data of the client object
 *
 * @author Davide Kovac
 *
 */
public class Client implements Serializable {
/** Serialization version for Client */	
private static final long serialVersionUID=1l;
/**The client id  */
private int clientId;
/** The email of the client*/
private String clientEmail;
/** The client number*/
private String clientNumber;
/**
 * Constructor for the client
 * @param clientId the id for the client
 * @param clientEmail the email of the client
 * @param clientNumber the client phone number
 */
public Client(int clientId, String clientEmail, String clientNumber) {
	this.clientId = clientId;
	this.clientEmail = clientEmail;
	this.clientNumber = clientNumber;
}
/**
 * @return The client Id
 */
public int getClientId() {
	return clientId;
}
/**
 * Set the client Id
 * @param clientId 
 */
private void setClientId(int clientId) {
	this.clientId = clientId;
}
/**
 * @return The client email
 */
public String getClientEmail() {
	return clientEmail;
}
/**
 * Set the client email
 * @param clientEmail
 */
private void setClientEmail(String clientEmail) {
	this.clientEmail = clientEmail;
}
/**
 * @return The client phone number
 */
public String getClientNumber() {
	return clientNumber;
}
/**
 * Set the client phone number
 * @param clientNumber
 */
private void setClientNumber(String clientNumber) {
	this.clientNumber = clientNumber;
}


}