package model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FuelCar extends Automobile {

    private Long engine;
    private Long fuelCapacity;
    private int hp;
    private boolean isGas;

    @Override
    public String toString() {

        return super.toString() +
                ", engine=" + engine +
                ", fuelCapacity=" + fuelCapacity +
                ", hp=" + hp +
                ", isGas=" + isGas +
                '}';
    }
}
