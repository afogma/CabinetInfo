package local.clinic1.CabinetInfo.cabinets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cabinets")
public class Cabinet {

    @Id
    private int number;
    private int floor;
    private String department;
}
