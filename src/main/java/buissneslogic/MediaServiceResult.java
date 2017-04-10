/**
 * 
 */
package buissneslogic;

import javax.ws.rs.core.Response.Status;

/**
 * @author rebec
 *
 */
enum MediaServiceResult {

	// todo should result be more general eg. INVALID_ISBN => INVALID_FIELD
	// todo can values be anything? 
	OK(200), INVALID_ISBN(213), ISBN_RESERVED(214), ISBN_NOT_FOUND(413), ISBN_NOT_EQUALE(414), MISSING_BOOK_INFO(222) ,BAD_REQUEST(400), NOT_FOUND(404), IM_A_TEAPOT(418); // error values
	
	private int errorNum;
	
	private MediaServiceResult(int errorNum){
		this.setErrorNum(errorNum);
	}
	
	Status getStatus() {
		return null;
	}

	/**
	 * @return the errorNum
	 */
	public int getErrorNum() {
		return errorNum;
	}

	/**
	 * @param errorNum the errorNum to set
	 */
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	
}
