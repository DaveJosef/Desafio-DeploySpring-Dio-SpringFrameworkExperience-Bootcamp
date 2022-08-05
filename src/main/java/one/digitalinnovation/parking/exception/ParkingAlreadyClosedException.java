package one.digitalinnovation.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingAlreadyClosedException extends RuntimeException {

    public ParkingAlreadyClosedException(String id) {
        super("Parking with ID: " + id + " is already closed");
    }
}
