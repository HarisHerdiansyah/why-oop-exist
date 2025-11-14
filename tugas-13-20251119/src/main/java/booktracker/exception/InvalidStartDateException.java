package booktracker.exception;

public class InvalidStartDateException extends RuntimeException {
    public InvalidStartDateException() {
        super("Tanggal mulai tidak bisa lebih dari tanggal selesai.");
    }
}
