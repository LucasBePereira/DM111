package br.inatel.pos.dm111.vfr.api.core;

public enum AppErrorCode {

    CONFLICTED_USER_EMAIL("User.email.conflicted", "Provided email is alreadyy in use!", 409),
    USER_NOT_FOUND("User.not.found", "User was not found", 404),
    RESTAURANT_NOT_FOUND("Restaurant.not.found", "Restaurant was not found", 404),
    INVALID_USER_TYPE("user.invalid.type", "Not valid", 403),
    INVALID_USER_CREDENTIALS("credentials.invalid.type", "Provided credentials are not valid!", 401),
    OPERATION_NOT_SUPPORTED("operation.not.supported", "Operation not supported by the given user type.", 403),
    INTERNAL_DATABASE_COMMUNICATION_ERROR("internal.error", "Failure to communicate with repository.", 500);

    private String code;
    private String message;
    private int status;

    AppErrorCode(String code, String message, int status){
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
