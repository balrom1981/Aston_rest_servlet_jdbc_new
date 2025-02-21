package ru.balrom.Aston_rest_servlet_jdbc.mapper;

import ru.balrom.Aston_rest_servlet_jdbc.dto.ApartmentDto;
import ru.balrom.Aston_rest_servlet_jdbc.entity.Apartment;

public class ApartmentMapper implements Mapper<Apartment, ApartmentDto> {
    @Override
    public Apartment fromDto(ApartmentDto apartmentDto) {
        return Apartment.builder().id(apartmentDto.getId()).numberRooms(apartmentDto.getNumberRooms())
                .idCity(apartmentDto.getIdCity()).build();
    }

    @Override
    public ApartmentDto toDto(Apartment apartment) {
        if (apartment==null){
            return null;
        }
        return ApartmentDto.builder().id(apartment.getId()).numberRooms(apartment.getNumberRooms())
                .idCity(apartment.getIdCity()).build();
    }
}
