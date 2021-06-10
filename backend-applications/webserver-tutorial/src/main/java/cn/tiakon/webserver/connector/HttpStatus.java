package cn.tiakon.webserver.connector;

public enum HttpStatus {

    SC_OK(200,"OK"),
    SC_NOT_FOUND(404,"File Not Found");

    private int statusCode;
    private String describe;

    HttpStatus(int statusCode, String describe) {
        this.statusCode = statusCode;
        this.describe = describe;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public String getDescribe() {
        return describe;
    }
}
