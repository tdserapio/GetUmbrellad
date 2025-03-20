package getumbrellad.models.exceptions;

public class ItemNotFoundException extends Exception {
    
    public ItemNotFoundException() {
        super("Item not found.");
    }
    
    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
