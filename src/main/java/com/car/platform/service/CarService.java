package com.car.platform.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.car.platform.dto.request.AutocompleteRequestDto;
import com.car.platform.dto.request.CarRequestDto;
import com.car.platform.dto.request.SearchCarByBrandRequestDto;
import com.car.platform.dto.response.CarResponseDto;
import com.car.platform.entity.CarEntity;
import com.car.platform.mapper.CarMapper;
import com.car.platform.repository.ICarRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class CarService {
    private ElasticsearchClient elasticsearchClient;
    private ICarRepository carRepository;
    private CarMapper carMapper;
    private ElasticsearchOperations operations;

    public CarService(ElasticsearchClient elasticsearchClient, ICarRepository carRepository, CarMapper carMapper, ElasticsearchOperations operations) {
        this.elasticsearchClient = elasticsearchClient;
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.operations = operations;
    }

    public void saveCars(List<CarRequestDto> request) {
        List<CarEntity> requestEntities
                = request.stream().map(req -> carMapper.toCarEntity(req)).collect(Collectors.toList());

        carRepository.saveAll(requestEntities);
        Collections.reverse(requestEntities);
    }

    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        List<CarEntity> cars = new ArrayList<>();
        carRepository.findAll().forEach(cars::add);
        List<CarResponseDto> carResponseDto
                = cars.stream().map(entity -> carMapper.toCarResponseDto(entity)).collect(Collectors.toList());
        return ResponseEntity.ok(carResponseDto);
    }

    public ResponseEntity<List<CarResponseDto>> getCarByBrand(SearchCarByBrandRequestDto requestDto) {
        SearchHits<CarEntity> cars = operations.search(
                new NativeSearchQueryBuilder()
                        .withQuery(matchPhrasePrefixQuery("carBrand", requestDto.getBrand()))
                        .build(),
                CarEntity.class);
        List<CarResponseDto> response = processResult(cars);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<CarResponseDto>> getAutocompleteResults(String query) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(multiMatchQuery(query)
                .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX)
                .fields(Map.of("searchName", 1F,
                        "searchName._2gram", 1F,
                        "searchName._3gram", 1F)).fuzziness(Fuzziness.AUTO));
        SearchHits<CarEntity> cars = operations.search(searchQueryBuilder.build(), CarEntity.class);
        List<CarResponseDto> response = processResult(cars);
        return ResponseEntity.ok(response);
    }

    private List<CarResponseDto> processResult(SearchHits<CarEntity> cars) {
        List<CarResponseDto> response = new ArrayList<>();
        cars.getSearchHits().stream()
                .map(entity -> carMapper.toCarResponseDto(entity.getContent()))
                .forEach(response::add);
        return response;
    }


}
