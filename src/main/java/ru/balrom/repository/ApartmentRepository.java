package ru.balrom.repository;

import ru.balrom.db.DBConnector;
import ru.balrom.model.Apartment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class ApartmentRepository, предназначен для CRUD операций с объектом типа Apartment
 */
public class ApartmentRepository implements Repository<Apartment> {
    private final static String SELECTID = "SELECT * FROM appartment WHERE id=?";
    private final static String SELECTALL = "SELECT * FROM appartment";
    private final static String SELECTALLPERSONID = "SELECT * FROM appartment WHERE personId=?";
    private final static String INSERT ="INSERT INTO appartment (city, roomAmount, personId) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE appartment SET city=?, roomAmount=?, personId=? WHERE id=?";
    private final static String DELETE  = "DELETE FROM appartment WHERE id=?";

    private final DBConnector connector;

    /**
     * Конструктор, создающий подключение в БД MySql
     */
    public ApartmentRepository() {
        connector = new DBConnector();
    }

    public ApartmentRepository(DBConnector connector) {
        this.connector = connector;
    }

    /**
     * получает объект типа Apartment по  id
     * @param id - входной параметр id
     * @return - возвращает объект типа Apartment
     */
    @Override
    public Apartment get(int id) {
        Apartment apartment = null;

        try (Connection connection = connector.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECTID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                apartment = new Apartment();
                apartment.setId(resultSet.getInt(1));
                apartment.setCity(resultSet.getString(2));
                apartment.setRoomAmount(resultSet.getInt(3));
                apartment.setPersonId(resultSet.getInt(4));
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return apartment;
    }
    @Override
    public List<Apartment> getAllByPersonId(int personId) {
        List<Apartment> listWithPersonId = new ArrayList<>();

        try(Connection connection = connector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECTALLPERSONID);
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getInt(1));
                apartment.setCity(resultSet.getString(2));
                apartment.setRoomAmount(resultSet.getInt(3));
                apartment.setPersonId(resultSet.getInt(4));
                listWithPersonId.add(apartment);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return listWithPersonId;
    }

    /**
     * @return возвращает List<Apartment>
     */
    @Override
    public List<Apartment> getAll() {
        List<Apartment> list = new ArrayList<>();
        try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECTALL)) {
            while (resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getInt(1));
                apartment.setCity(resultSet.getString(2));
                apartment.setRoomAmount(resultSet.getInt(3));
                apartment.setPersonId(resultSet.getInt(4));
                list.add(apartment);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return list;
    }


    /**
     * сохраняет объект типа Apartment
     * @param apartment для сохранения в БД
     */
    @Override
    public void save(Apartment apartment) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, apartment.getCity());
            preparedStatement.setInt(2, apartment.getRoomAmount());
            preparedStatement.setInt(3, apartment.getPersonId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * изменяет объект типа Apartment
     * @param apartment для изменения
     */
    @Override
    public void update(Apartment apartment) {

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, apartment.getCity());
            statement.setInt(2, apartment.getRoomAmount());
            statement.setInt(3, apartment.getPersonId());
            statement.setInt(4, apartment.getId());
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * удаляет объект типа Apartment по  id
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
