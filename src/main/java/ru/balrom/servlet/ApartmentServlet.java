package ru.balrom.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.balrom.dto.ApartmentDto;
import ru.balrom.service.ApartmentService;
import ru.balrom.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "apartmentServlet", value = "/api/v1/apartments/*")
public class ApartmentServlet extends HttpServlet {
    private final Service<ApartmentDto> serviceApartment = new ApartmentService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String path = request.getPathInfo();
        PrintWriter writer = response.getWriter();

        if (path == null || path.equals("/")) {
            List<ApartmentDto> list = serviceApartment.getAll();
            String json = mapper.writeValueAsString(list);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(json);

        } else {
            int id = Integer.parseInt(path.substring(1));
            ApartmentDto apartment = serviceApartment.get(id);
            try {
                if (apartment == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Apartment with id="+id+" is not found");
                    return;
                }
                String json = mapper.writeValueAsString(apartment);
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
        ApartmentDto current = mapper.readValue(stringBuilder.toString(), ApartmentDto.class);
        serviceApartment.save(current);

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
        ApartmentDto current = mapper.readValue(stringBuilder.toString(), ApartmentDto.class);
        serviceApartment.update(current);

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
            serviceApartment.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path");
        }
    }
}
