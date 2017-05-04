/**
 * 
 */
package edu.hm.shareit.service;

import javax.ws.rs.core.Response.Status;

/**
 * MediaServiceResult enum for diffrent error messages.
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public enum MediaServiceResult {

    OK(200), INVALID_ISBN(300), ISBN_RESERVED(301), MISSING_ISBN(302), 
    ISBN_NOT_EQUAL(303), INVALID_BARCODE(500), 
    BARCODE_RESERVED(501), MISSING_BARCODE(502), 
    BARCODE_NOT_EQUAL(503), MISSING_INFO(400), 
    NOT_FOUND(404), IM_A_TEAPOT(418), 
    MEDIUM_ALREADY_EXISTS(777);

    private int errorNum;

    /**
     * MediaServiceResult creates new error message.
     * 
     * @param errorNum
     *            error number
     */
    MediaServiceResult(int errorNum) {
        this.setErrorNum(errorNum);
    }

    /**
     * gets the status.
     * 
     * @return status
     */
    Status getStatus() {
        return null;
    }

    /**
     * gets the error number.
     * 
     * @return the errorNum
     */
    public int getErrorNum() {
        return errorNum;
    }

    /**
     * sets the error number.
     * 
     * @param errorNum
     *            the errorNum to set
     */
    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    /**
     * return the error message to the given error.
     * 
     * @param error
     *            error number
     * @return message
     */
    public static String getErrorMessage(MediaServiceResult error) {
        String message;
        switch (error) {
        case OK:
            message = "Everything ok";
            break;
        case INVALID_ISBN:
            message = "Isbn is not valid. Please check " + "the security digit.";
            break;
        case ISBN_RESERVED:
            message = "Isbn is alread reserved.";
            break;
        case MISSING_ISBN:
            message = "Isbn could not be found.";
            break;
        case ISBN_NOT_EQUAL:
            message = "The isbn nummers do not match.";
            break;
        case BARCODE_RESERVED:
            message = "Barcode is already reserved.";
            break;
        case MISSING_BARCODE:
            message = "Barcode not found.";
            break;
        case BARCODE_NOT_EQUAL:
            message = "Barcodes do not match.";
            break;
        case MISSING_INFO:
            message = "Medium is missing information. " + "Please check title and author or " + "director are given.";
            break;
        case NOT_FOUND:
            message = "Not Found";
            break;
        case MEDIUM_ALREADY_EXISTS:
            message = "the medium already exists in database.";
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
