package com.car.platform.controller;

import com.car.platform.dto.request.AutocompleteRequestDto;
import com.car.platform.dto.request.CarRequestDto;
import com.car.platform.dto.request.SearchCarByBrandRequestDto;
import com.car.platform.dto.response.CarResponseDto;
import com.car.platform.service.CarService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get/all")
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/get-by-model-and-brand")
    public ResponseEntity<List<CarResponseDto>> getAllCars(@RequestBody SearchCarByBrandRequestDto requestDto) {
        return carService.getCarByBrand(requestDto);
    }

    @PostMapping("/get-autocomplete-results")
    public ResponseEntity<List<CarResponseDto>> getAutocompleteResults(@RequestBody AutocompleteRequestDto requestDto) {
        return carService.getAutocompleteResults(requestDto);
    }
}
