package rocks.zipcode.atm;

/**
 * @author ZipCodeWilmington
 */
public class ActionResult<T> {

    private T data;
    private String errorMessage;
    private String specialMessage;

    private ActionResult(T data) {
        this.data = data;
    }

    private ActionResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ActionResult(String specialMessage, T data){
        this.specialMessage = specialMessage;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getSpecialMessage(){
        return specialMessage;
    }

    public boolean isSuccess() {
        return data != null;
    }

    public static <E> ActionResult<E> success(E data) {
        return new ActionResult<E>(data);
    }

    public static <E> ActionResult<E> successWithMessage(String specialMessage, E data){
        return new ActionResult<E>(specialMessage, data);
    }

    public static <E> ActionResult<E> fail(String errorMessage) {
        return new ActionResult<E>(errorMessage);
    }
}
