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
import ru.balrom.dto.CarDto;
import ru.balrom.service.CarService;

import java.io.*;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServletTest {
    private final static int ID = 1;
    private final static String BRAND = "LADA";
    private final static String COLOUR = "Black";
    private final static int PERSON_ID = 1;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CarService service;
    @Mock
    private CarServlet servlet;
    private final StringWriter stringWriter = new StringWriter();
    private List<CarDto> expectedList;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void set(){
        servlet = new CarServlet(service, mapper);
        expectedList = List.of(CarDto.builder().id(ID).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build(),
                CarDto.builder().id(5).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build(),
                CarDto.builder().id(10).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,5,10})
    void doGetWithIdTest(int id) throws ServletException, IOException {
        CarDto expectedCarDto = CarDto.builder().id(ID).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build();

        when(request.getPathInfo()).thenReturn("/" + id);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(service.get(id)).thenReturn(expectedCarDto);

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
        CarDto carDto = CarDto.builder().id(ID).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build();

        String personDtoJson = mapper.writeValueAsString(carDto);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(personDtoJson)));
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doPost(request, response);

        verify(response, times(1)).setStatus(SC_CREATED);
        verify(service, times(1)).save(carDto);
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