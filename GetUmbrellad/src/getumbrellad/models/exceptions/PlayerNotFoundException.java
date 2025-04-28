package getumbrellad.models.exceptions;

public class PlayerNotFoundException extends Exception {
    
    public PlayerNotFoundException() {
        super("Player not found.");
    }
    
    public PlayerNotFoundException(String msg) {
        super(msg);
    }
}
