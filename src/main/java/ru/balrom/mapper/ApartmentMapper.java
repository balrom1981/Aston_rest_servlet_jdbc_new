package ru.balrom.mapper;


import ru.balrom.dto.ApartmentDto;
import ru.balrom.model.Apartment;

/**
 * class ApartmentMapper предназначен для преобразования объекта типа Apartment в ApartmentDto
 * и объекта типа ApartmentDto в Apartment
 */

public class ApartmentMapper implements Mapper<Apartment, ApartmentDto> {
    @Override
    public Apartment fromDto(ApartmentDto apartmentDto) {
        return Apartment.builder().id(apartmentDto.getId()).city(apartmentDto.getCity())
                .roomAmount(apartmentDto.getRoomAmount()).personId(apartmentDto.getPersonId()).build();
    }

    @Override
    public ApartmentDto toDto(Apartment apartment) {
        if (apartment==null){
            return null;
        }
        return ApartmentDto.builder().id(apartment.getId()).city(apartment.getCity())
                .roomAmount(apartment.getRoomAmount()).personId(apartment.getPersonId()).build();
    }
}
