/**
 * 
 */
package buissneslogic;

import javax.ws.rs.core.Response.Status;

/**
 * MediaServiceResult enum for diffrent error messages.
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
enum MediaServiceResult {

	// todo should result be more general eg. INVALID_ISBN => INVALID_FIELD
	OK(200),
	INVALID_ISBN(300), 		ISBN_RESERVED(301), 	ISBN_NOT_FOUND(302), 	ISBN_NOT_EQUALE(303), 	 // error values for book 3xx
	INVALID_BARCODE(500), 	BARCODE_RESERVED(501), 	BARCODE_NOT_FOUND(502), BARCODE_NOT_EQUALE(503), // error values for discs 5xx
	MISSING_INFO(400), 		BAD_REQUEST(401), 		NOT_FOUND(404), 		IM_A_TEAPOT(418);		 // general error values 4xx
	
	private int errorNum;
	
	/**
	 * MediaServiceResult creates new error message.
	 * @param errorNum
	 */
	private MediaServiceResult(int errorNum){
		this.setErrorNum(errorNum);
	}
	
	/**
	 * gets the status of ???.
	 * @return
	 */
	Status getStatus() {
		return null;
	}

	/**
	 * gets the error number.
	 * @return the errorNum
	 */
	public int getErrorNum() {
		return errorNum;
	}

	/**
	 * sets the error number.
	 * @param errorNum the errorNum to set
	 */
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	
	/**
	 * return the error message to the given error
	 * @param error
	 * @return message
	 */
	public static String getErrorMessage(MediaServiceResult error) {
		String message;
		switch(error) {
		case OK:
			message = "Everything ok";
			break;
		case INVALID_ISBN:
			message = "Isbn is not valid. Please check the security digit.";
			break;
		case ISBN_RESERVED:
			message = "Isbn is alread reserved.";
			break;
		case ISBN_NOT_FOUND:
			message = "Isbn could not be found.";
			break;
		case ISBN_NOT_EQUALE:
			message = "The isbn nummers do not match.";
			break;
		case INVALID_BARCODE:
			message = "Barcode is invalid.";
			break;
		case BARCODE_RESERVED:
			message = "Barcode is already reserved.";
			break;
		case BARCODE_NOT_FOUND:
			message = "Barcode not found.";
			break;
		case BARCODE_NOT_EQUALE:
			message = "Barcodes do not match.";
			break;
		case MISSING_INFO:
			message = "Medium is missing information. Please check title and author or director are given.";
			break;
		case BAD_REQUEST:
			message = "Bad request. Could not be processed. Please check REST api.";
			break;
		case NOT_FOUND:
			message = "Not Found";
			break;
		case IM_A_TEAPOT:
			message = "Have a cup of tea and then it will work.";
			break;
		default:
			message = "Error code not found";
		}
		
		return message;
	}
	
}
