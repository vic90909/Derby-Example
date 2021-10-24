package model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Automobile {
    private Long id;
    private String manufacturer;
    private String model;
    private String color;
    private int noOfDoors;
    private BigDecimal price;
    private boolean isManual;

    @Override
    public String toString() {
        return "Automobile{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", noOfDoors=" + noOfDoors +
                ", price=" + price +
                ", isManual=" + isManual;
    }
}
