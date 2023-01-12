package com.car.platform.repository;

import com.car.platform.entity.CarEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends ElasticsearchRepository<CarEntity, Integer> {
}
