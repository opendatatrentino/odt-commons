package eu.trentorise.opendata.commons;

public class OdtParseException extends OdtException {
    
 
    /**
     * Creates the OdtParseException using the provided throwable
     * @since 1.1
     */
    public OdtParseException(Throwable tr) {
        super(tr);
    }

    /**
     * Creates the OdtParseException using the provided message and throwable
     */
    public OdtParseException(String msg, Throwable tr) {
        super(msg, tr);
    }

    /**
     * Creates the OdtParseException using the provided message
     */
    public OdtParseException(String msg) {
        super(msg);
    }
}
