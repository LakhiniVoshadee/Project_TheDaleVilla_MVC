package lk.ijse.thedale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Admin {
    private String UserId;
    private String Password;
    private String UserName;
    private String Mob_num;
}
