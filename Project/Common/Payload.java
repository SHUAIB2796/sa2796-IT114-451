package Project;

import java.io.Serializable;

public class Payload implements Serializable {
    private static final long serialVersionUID = 1L;
    private PayloadType payloadType;
    private long clientId;
    private String message;

    

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



    @Override
    public String toString(){
        return String.format("Payload[%s] Client Id [%s] Message: [%s]", getPayloadType(), getClientId(), getMessage());
    }
}
