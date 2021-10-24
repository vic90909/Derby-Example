package model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ElectricCar extends Automobile{

    private Long power;
    private Long batteryCapacity;
    private Long range;

    @Override
    public String toString() {
        return "ElectricCar{" +
                "power=" + power +
                ", batteryCapacity=" + batteryCapacity +
                ", range=" + range +
                '}';
    }
}
