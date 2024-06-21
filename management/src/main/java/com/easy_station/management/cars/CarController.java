package com.easy_station.management.cars;

import com.easy_station.management.cars.dto.CalculatemountToChargedDTO;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import com.easy_station.management.cars.services.CarsService;
import com.easy_station.management.common.annotation.Role;
import com.easy_station.management.common.enums.UserRoleEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cars")
public class CarController {
    @Autowired
    private CarsService carsService;

    @PostMapping("check-in")
    @Role(UserRoleEnum.USER)
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
    @Role(UserRoleEnum.USER)
    public ResponseEntity<CarDTO> checkOut(@RequestAttribute("companyId") String companyId, @PathVariable("id") String id) {
        CarDTO carDTO = carsService.checkOut(id, companyId);
        return ResponseEntity.ok(carDTO);
    }

    @GetMapping
    @Role(UserRoleEnum.USER)
    public ResponseEntity<List<CarDTO>> findAll(@RequestAttribute("companyId") String companyId) {
        List<CarDTO> list = carsService.findAll(companyId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Role(UserRoleEnum.USER)
    public ResponseEntity<CarDTO> findOne(@RequestAttribute("companyId") String companyId, @PathVariable("id") String id) {
        CarDTO carDTO = carsService.findOne(id, companyId);
        return ResponseEntity.ok(carDTO);
    }

    @GetMapping("/{id}/calculate-price")
    @Role(UserRoleEnum.USER)
    public ResponseEntity<CalculatemountToChargedDTO> calculatePrice(@RequestAttribute("companyId") String companyId, @PathVariable("id") String id) {
        CalculatemountToChargedDTO value = carsService.calculateAmountToCharged(id, companyId);
        return ResponseEntity.ok(value);
    }
}
