package au.com.anthonybruno.gymbuddy.exception;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(400, message);
    }
}
