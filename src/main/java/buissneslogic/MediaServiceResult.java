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
	// todo can values be anything? 
	OK(200), INVALID_ISBN(213), ISBN_RESERVED(214), ISBN_NOT_FOUND(413), ISBN_NOT_EQUALE(414), MISSING_BOOK_INFO(222) ,BAD_REQUEST(400), NOT_FOUND(404), IM_A_TEAPOT(418); // error values
	
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
	
}
