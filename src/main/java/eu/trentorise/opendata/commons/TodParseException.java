package eu.trentorise.opendata.commons;

public class TodParseException extends TodException {
    
 
    /**
     * Creates the TodParseException using the provided throwable
     * @since 1.1
     */
    public TodParseException(Throwable tr) {
        super(tr);
    }

    /**
     * Creates the TodParseException using the provided message and throwable
     */
    public TodParseException(String msg, Throwable tr) {
        super(msg, tr);
    }

    /**
     * Creates the TodParseException using the provided message
     */
    public TodParseException(String msg) {
        super(msg);
    }
}
