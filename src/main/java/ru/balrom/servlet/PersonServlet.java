package ru.balrom.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.balrom.dto.PersonDto;
import ru.balrom.service.PersonService;
import ru.balrom.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "personServlet", value = "/api/v1/persons/*")
public class PersonServlet extends HttpServlet {
    private final Service<PersonDto> servicePerson = new PersonService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String path = request.getPathInfo();
        PrintWriter writer = response.getWriter();

        if (path == null || path.equals("/")) {
            List<PersonDto> list = servicePerson.getAll();
            String json = mapper.writeValueAsString(list);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(json);

        } else {
            int id = Integer.parseInt(path.substring(1));
            PersonDto person = servicePerson.get(id);
            try {
                if (person == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Person with id="+id+" is not found");
                    return;
                }
                String json = mapper.writeValueAsString(person);
                response.setStatus(HttpServletResponse.SC_OK);
                writer.write(json);
            } catch (NumberFormatException exception) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID must be only a number");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String path = request.getPathInfo();

        if (path != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path");
            return;
        }

        BufferedReader body = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        while (body.ready()) {
            stringBuilder.append(body.readLine());
        }
        PersonDto current = mapper.readValue(stringBuilder.toString(), PersonDto.class);
        servicePerson.save(current);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write(stringBuilder.toString());

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String path = request.getPathInfo();

        if (path != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path");
            return;
        }

        BufferedReader body = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        while (body.ready()) {
            stringBuilder.append(body.readLine());
        }
        PersonDto current = mapper.readValue(stringBuilder.toString(), PersonDto.class);
        servicePerson.update(current);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(stringBuilder.toString());


    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String path = request.getPathInfo();
        if (path != null && !path.equals("/")) {
            int id = Integer.parseInt(path.substring(1));
            servicePerson.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path");
        }
    }
}
