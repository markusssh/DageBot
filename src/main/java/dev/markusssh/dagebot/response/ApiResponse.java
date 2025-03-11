package dev.markusssh.dagebot.response;

public class ApiResponse<T> {
    private String message;
    private T data;
    private Object metadata;

    public ApiResponse(String message, T data, Object metadata) {
        this.message = message;
        this.data = data;
        this.metadata = metadata;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Object getMetadata() {
        return metadata;
    }
}
