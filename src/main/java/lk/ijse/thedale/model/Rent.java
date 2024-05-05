package lk.ijse.thedale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Rent {
    private String RentID;
    private int Qty;
    private String Description;
    private String Type;

}
