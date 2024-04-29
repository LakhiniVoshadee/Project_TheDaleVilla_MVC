package lk.ijse.thedale.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeTm {
    private String EmpID;
    private String Name;
    private String Type;
    private String Email;
    private String DOB;

}
