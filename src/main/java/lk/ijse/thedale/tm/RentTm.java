package lk.ijse.thedale.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RentTm {
    private String RentID;
    private int Qty;
    private String Description;
    private String Type;
}
