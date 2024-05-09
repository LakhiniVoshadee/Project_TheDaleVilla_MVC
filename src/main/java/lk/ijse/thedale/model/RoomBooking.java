package lk.ijse.thedale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RoomBooking {
    private String RoomBookingID;
    private String cusID;
    private Date date;


}
