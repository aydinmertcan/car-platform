package com.car.platform.controller;

import com.car.platform.dto.CarRequestDto;
import com.car.platform.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/save")
    public void saveCars(@RequestBody List<CarRequestDto> request) {
        carService.saveCars(request);
    }
}
