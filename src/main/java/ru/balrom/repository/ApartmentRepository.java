package ru.balrom.repository;

import ru.balrom.db.DBConnector;
import ru.balrom.model.Apartment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRepository implements Repository<Apartment> {
    private final static String SELECTID = "SELECT * FROM appartment WHERE id=?";
    private final static String SELECTALL = "SELECT * FROM appartment";
    private final static String INSERT ="INSERT INTO appartment (city, roomAmount, personId) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE appartment SET city=?, roomAmount=?, personId=? WHERE id=?";
    private final static String DELETE  = "DELETE FROM appartment WHERE id=?";

    private final DBConnector connector;

    public ApartmentRepository() {
        connector = new DBConnector();
    }

    @Override
    public Apartment get(int id) {
        Apartment apartment = null;

        try (Connection connection = connector.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECTID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                apartment = new Apartment();
                apartment.setId(resultSet.getInt("id"));
                apartment.setCity(resultSet.getString("city"));
                apartment.setRoomAmount(resultSet.getInt("roomAmount"));
                apartment.setPersonId(resultSet.getInt("personId"));
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return apartment;
    }

    @Override
    public List<Apartment> getAll() {
        List<Apartment> list = new ArrayList<>();
        try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECTALL)) {
            while (resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getInt("id"));
                apartment.setCity(resultSet.getString("city"));
                apartment.setRoomAmount(resultSet.getInt("roomAmount"));
                apartment.setPersonId(resultSet.getInt("personId"));
                list.add(apartment);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return list;
    }

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
