package org.zr.restaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.zr.restaurant.domain.dtos.PhotoDto;
import org.zr.restaurant.domain.entities.Photo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface PhotoMapper {
    PhotoDto toDto(Photo photo);
}
