package Intern.moonpd_crawling.exception;

public class CrawlingException extends RuntimeException {

    public CrawlingException(String message) {
        super(message);
    }

    public CrawlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
