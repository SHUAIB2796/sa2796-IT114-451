package Project.Common;

import java.io.Serializable;

public class Payload implements Serializable {
    private static final long serialVersionUID = 1L;
    private PayloadType payloadType;
    private long clientId;
    private String message;
    private String targetUsername;
    private boolean isPrivate;

     // Getter and setter for targetUsername
     public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }


    

    public PayloadType getPayloadType() {
        return payloadType;
    }



    public void setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
    }



    public long getClientId() {
        return clientId;                                                        //UCID: sa2796 Date: 6-29-24
    }              



    public void setClientId(long clientId) {
        this.clientId = clientId;
    }



    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }




    @Override
    public String toString(){
        return String.format("Payload[%s] Client Id [%s] Message: [%s]", getPayloadType(), getClientId(), getMessage());
    }
}
