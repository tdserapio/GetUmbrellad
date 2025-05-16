package getumbrellad.models.exceptions;

public class LevelDoesNotExistException extends Exception {
    
    public LevelDoesNotExistException() {
        super("Level not found.");
    }
    
    public LevelDoesNotExistException(String msg) {
        super(msg);
    }
}
