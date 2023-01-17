package com.car.platform.mapper;

import com.car.platform.dto.request.CarRequestDto;
import com.car.platform.dto.response.CarResponseDto;
import com.car.platform.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    CarEntity toCarEntity(CarRequestDto dto);
    CarResponseDto toCarResponseDto(CarEntity entity);
}
