package ru.balrom.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.balrom.dto.CarDto;
import ru.balrom.service.CarService;
import ru.balrom.service.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "carServlet", value = "/rest/v1/cars/*")
public class CarServlet extends HttpServlet {
    private final Service<CarDto> serviceCar = new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String path = request.getPathInfo();
        PrintWriter writer = response.getWriter();

        if (path == null || path.equals("/")) {
            List<CarDto> list = serviceCar.getAll();
            String json = mapper.writeValueAsString(list);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(json);

        } else {
            int id = Integer.parseInt(path.substring(1));
            CarDto carDTO = serviceCar.get(id);
            try {
                if (carDTO == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Car with id="+id+" is not found");
                    return;
                }
                String json = mapper.writeValueAsString(carDTO);
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
        CarDto current = mapper.readValue(stringBuilder.toString(), CarDto.class);
        serviceCar.save(current);

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
        CarDto current = mapper.readValue(stringBuilder.toString(), CarDto.class);
        serviceCar.update(current);

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
            serviceCar.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path");
        }
    }
}
