package com.car.platform.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.car.platform.dto.CarRequestDto;
import com.car.platform.entity.CarEntity;
import com.car.platform.mapper.CarMapper;
import com.car.platform.repository.ICarRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private ElasticsearchClient elasticsearchClient;
    private ICarRepository carRepository;
    private CarMapper carMapper;

    public CarService(ElasticsearchClient elasticsearchClient, ICarRepository carRepository, CarMapper carMapper) {
        this.elasticsearchClient = elasticsearchClient;
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public void saveCars(List<CarRequestDto> request) {
        List<CarEntity> requestEntities
                = request.stream().map(req -> carMapper.toCarEntity(req)).collect(Collectors.toList());
        carRepository.saveAll(requestEntities);
    }
}
