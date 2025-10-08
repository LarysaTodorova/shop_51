package ait.shop.exception.handling;

public class Response {

    private String message;
    private Object resalt;

    public Response(Object resalt) {
        this.resalt = resalt;
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Object getResalt() {
        return resalt;
    }

    public boolean isError() {
        return message != null;
    }

    @Override
    public String toString() {
        return message != null ? message : resalt.toString();
    }
}
