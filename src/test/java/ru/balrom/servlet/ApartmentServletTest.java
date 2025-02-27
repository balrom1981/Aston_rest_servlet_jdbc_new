package ru.balrom.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.dto.ApartmentDto;
import ru.balrom.service.ApartmentService;


import java.io.*;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServletTest {
    private final static int ID = 1;
    private final static String CITY = "Moscow";
    private final static int ROOM_NUMBER = 1;
    private final static int PERSON_ID = 1;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ApartmentService service;
    private ApartmentServlet servlet;
    private final StringWriter stringWriter = new StringWriter();
    private List<ApartmentDto> expectedList;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void set(){
        servlet = new ApartmentServlet(service, mapper);
        expectedList = List.of(ApartmentDto.builder().id(ID).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build(),
                ApartmentDto.builder().id(5).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build(),
                ApartmentDto.builder().id(10).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,5,10})
    void doGetWithIdTest(int id) throws ServletException, IOException {
        ApartmentDto expectedApartmentDto = ApartmentDto.builder().id(ID).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build();

        when(request.getPathInfo()).thenReturn("/" + id);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(service.get(id)).thenReturn(expectedApartmentDto);

        servlet.doGet(request, response);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(service, times(1)).get(id);
    }

    @Test
    void doGetAllItems() throws IOException, ServletException {
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(service.getAll()).thenReturn(expectedList);

        servlet.doGet(request, response);

        verify(response, times(1)).setStatus(SC_OK);
        verify(service, times(1)).getAll();
    }

    @Test
    void doPostTest() throws IOException, ServletException {
        ApartmentDto apartmentDto = ApartmentDto.builder().id(ID).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build();

        String personDtoJson = mapper.writeValueAsString(apartmentDto);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(personDtoJson)));
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doPost(request, response);

        verify(response, times(1)).setStatus(SC_CREATED);
        verify(service, times(1)).save(apartmentDto);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    void doDeleteTest(int id) throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + id);

        servlet.doDelete(request, response);

        verify(response, times(1)).setStatus(SC_OK);
        verify(service, times(1)).delete(id);
    }

}