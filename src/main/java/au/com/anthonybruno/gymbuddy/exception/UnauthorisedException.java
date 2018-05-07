package au.com.anthonybruno.gymbuddy.exception;

public class UnauthorisedException extends HttpException {

    public UnauthorisedException(String message) {
        super(401, message);
    }
}
