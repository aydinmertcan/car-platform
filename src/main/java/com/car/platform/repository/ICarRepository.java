package com.car.platform.repository;

import com.car.platform.dto.request.SearchCarByBrandRequestDto;
import com.car.platform.entity.CarEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarRepository extends ElasticsearchRepository<CarEntity, Integer> {
}
