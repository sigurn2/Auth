package com.neusoft.sl.si.authserver.uaa.exception;

public class ExceptionMsgDTO {

    private String message;

    private String localizedMessage;

    private String detail;

    public ExceptionMsgDTO(String message, String localizedMessage) {
        this.message = message;
        this.localizedMessage = localizedMessage;
        this.detail = message;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     *            the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the localizedMessage
     */
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    /**
     * @param localizedMessage
     *            the localizedMessage to set
     */
    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

}
