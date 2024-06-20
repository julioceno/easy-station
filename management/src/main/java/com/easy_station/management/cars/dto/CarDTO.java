package com.easy_station.management.cars.dto;

import com.easy_station.management.cars.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDTO {
    private String id;
    private String ownerName;
    private String plate;
    private String courtyardId;
    private Double hourPrice;
    private Date checkIn;
    private Date checkOut;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.ownerName = car.getOwnerName();
        this.plate = car.getPlate();
        this.courtyardId = car.getCourtyardId();
        this.hourPrice = car.getHourPrice();
        this.checkIn = car.getCreatedAt();
        this.checkOut = car.getUpdatedAt();
    }
}
