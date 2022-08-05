package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.model.Parking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingCheckOutTest {

    @Test
    void whenGetBillOfOneHourOccupiedParkingThenReturnOneHourValue() {
        Parking oneHourOccupiedParking = new Parking();
        oneHourOccupiedParking.setEntryDate(LocalDateTime.now());
        oneHourOccupiedParking.setExitDate(oneHourOccupiedParking.getEntryDate().plus(1, ChronoUnit.HOURS));

        Assertions.assertEquals(ParkingCheckOut.ONE_HOUR_VALUE, ParkingCheckOut.getBill(oneHourOccupiedParking));
    }

    @Test
    void whenGetBillOfOneDayOccupiedParkingThenReturnOneHourValuePlusAdditionalHoursValue() {
        Parking oneDayOccupiedParking = new Parking();
        oneDayOccupiedParking.setEntryDate(LocalDateTime.now());
        oneDayOccupiedParking.setExitDate(oneDayOccupiedParking.getEntryDate().plus(1, ChronoUnit.DAYS));
        long hoursOccupied = oneDayOccupiedParking.getEntryDate().until(oneDayOccupiedParking.getExitDate(), ChronoUnit.HOURS);

        Assertions.assertEquals(ParkingCheckOut.ONE_HOUR_VALUE + ParkingCheckOut.ADDITIONAL_PER_HOUR_VALUE * hoursOccupied, ParkingCheckOut.getBill(oneDayOccupiedParking));
    }

    @Test
    void whenGetBillOfOneDayPlusOccupiedParkingThenReturnDaysValue() {
        Parking oneDayPlusOccupiedParking = new Parking();
        oneDayPlusOccupiedParking.setEntryDate(LocalDateTime.now());
        oneDayPlusOccupiedParking.setExitDate(oneDayPlusOccupiedParking.getEntryDate().plus(3, ChronoUnit.DAYS));
        long daysOccupied = oneDayPlusOccupiedParking.getEntryDate().until(oneDayPlusOccupiedParking.getExitDate(), ChronoUnit.DAYS);

        Assertions.assertEquals(ParkingCheckOut.DAY_VALUE * daysOccupied, ParkingCheckOut.getBill(oneDayPlusOccupiedParking));
    }
}
