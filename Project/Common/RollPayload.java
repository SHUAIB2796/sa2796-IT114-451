package Project.Common;


public class RollPayload extends Payload {
    
    private int Dicenumber;
    
    private int Sidesnumber;
    
    public RollPayload() {
        setPayloadType(PayloadType.ROLL);              
    }
   
    public int getDiceNumber() {
        return Dicenumber;
    }
   
    public void setDicenumber(int Dicenumber) {
        this.Dicenumber = Dicenumber;
    }
    
    public int getSidesNumber() {
        return Sidesnumber; 
    }
   
    public void setSidesnumber(int Sidesnumber) {
        this.Sidesnumber = Sidesnumber;
    }
    @Override
    public String toString() {
        return "RollPayload [Dicenumber=" + Dicenumber + ", Sidesnumber=" + Sidesnumber + "]";
    }
}