package com.easy_station.management.cars;

import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import com.easy_station.management.cars.services.CarsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/cars")
public class CarController {
    @Autowired
    private CarsService carsService;

    @PostMapping("check-in")
    public ResponseEntity<CarDTO> checkIn(@RequestAttribute("companyId") String companyId, @RequestBody @Valid CreateCarDTO dto) {
        CarDTO carDTO = carsService.checkIn(dto, companyId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(carDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(carDTO);
    }

    @PostMapping("/check-out/{id}")
    public ResponseEntity<CarDTO> checkOut(@RequestAttribute("companyId") String companyId, @PathVariable("id") String id) {
        CarDTO carDTO = carsService.checkOut(id, companyId);
        return ResponseEntity.ok(carDTO);
    }
}
