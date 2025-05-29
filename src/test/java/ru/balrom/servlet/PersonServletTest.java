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
import ru.balrom.dto.PersonDto;
import ru.balrom.service.PersonService;

import java.io.*;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServletTest {
    private final static int ID = 1;
    private final static String NAME = "Ivan";
    private final static String SURNAME = "Smirnov";
    private final static int AGE = 18;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PersonService service;
    private PersonServlet servlet;
    private final StringWriter stringWriter = new StringWriter();
    private List<PersonDto> expectedList;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void set(){
        servlet = new PersonServlet(service, mapper);
//        expectedList = List.of(PersonDto.builder().id(ID).name(NAME).surname(SURNAME).age(AGE).build(),
//                PersonDto.builder().id(5).name(NAME).surname(SURNAME).age(AGE).build(),
//                PersonDto.builder().id(10).name(NAME).surname(SURNAME).age(AGE).build());
        expectedList.add(PersonDto.builder().id(ID).name(NAME).surname(SURNAME).age(AGE).build());
        expectedList.add(PersonDto.builder().id(5).name(NAME).surname(SURNAME).age(AGE).build());
        expectedList.add(PersonDto.builder().id(10).name(NAME).surname(SURNAME).age(AGE).build());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,5,10})
    void doGetWithIdTest(int id) throws ServletException, IOException {
        PersonDto expectedPersonDto = PersonDto.builder().id(id).name(NAME).surname(SURNAME).age(AGE).build();

        when(request.getPathInfo()).thenReturn("/" + id);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(service.get(id)).thenReturn(expectedPersonDto);

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
        PersonDto personDto = PersonDto.builder().id(ID).name(NAME).surname(SURNAME).age(AGE).build();

        String personDtoJson = mapper.writeValueAsString(personDto);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(personDtoJson)));
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doPost(request, response);

        verify(response, times(1)).setStatus(SC_CREATED);
        verify(service, times(1)).save(personDto);
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