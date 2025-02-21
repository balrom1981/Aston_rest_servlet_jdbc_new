package ru.balrom.repository;



import ru.balrom.db.DBConnector;
import ru.balrom.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class CarRepository, предназначен для CRUD операций с объектом типа Car
 */
public class CarRepository implements Repository<Car>{
    private final static String SELECTID = "SELECT * FROM car WHERE id=?";
    private final static String SELECTALL = "SELECT * FROM car";
    private final static String INSERT = "INSERT INTO car (brand, colour, personId) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE car SET brand=?, colour=?, personId=? WHERE id=?";
    private final static String DELETE  = "DELETE FROM car WHERE id=?";;

    private final DBConnector connector;

    /**
     * Конструктор, создающий подключение в БД MySql
     */
    public CarRepository() {
        connector = new DBConnector();
    }


    /**
     * получает объект типа Car по  id
     * @param id - входной параметр id
     * @return - возвращает объект типа Car
     */
    @Override
    public Car get(int id) {
        Car car = null;

        try (Connection connection = connector.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECTID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setColour(resultSet.getString("colour"));
                car.setPersonId(resultSet.getInt("personId"));
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return car;
    }


    /**
     * @return возвращает List<Car>
     */
    @Override
    public List<Car> getAll() {
        List<Car> list = new ArrayList<>();
        try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECTALL)) {
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setColour(resultSet.getString("colour"));
                car.setPersonId(resultSet.getInt("personId"));
                list.add(car);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return list;
    }

    /**
     * сохраняет объект типа Car
     * @param car для сохранения в БД
     */
    @Override
    public void save(Car car) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getColour());
            preparedStatement.setInt(3, car.getPersonId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * изменяет объект типа Car
     * @param car для изменения
     */
    @Override
    public void update(Car car) {

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getColour());
            preparedStatement.setInt(3, car.getPersonId());
            preparedStatement.setInt(4, car.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * удаляет объект типа Car по  id
     * @param id входной параметр
     */
    @Override
    public void delete(int id) {

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
