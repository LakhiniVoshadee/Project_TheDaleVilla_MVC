package lk.ijse.thedale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Customer {

    private String cusId;
    private String cusName;
    private String sex;
    private String nic;
    private String contact;
    private String email;
    private String UserID;
}
