package buruiana.calcfin.model;

public class EspressioneException extends Exception{

    private String detailedMessage;

    public EspressioneException(String message, String detailedMessage) {
        super(message);
        this.detailedMessage = detailedMessage;
    }
    public String getDetailedMessage() {
        return detailedMessage;
    }
}
