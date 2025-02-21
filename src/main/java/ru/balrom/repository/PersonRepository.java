package ru.balrom.repository;


import ru.balrom.db.DBConnector;
import ru.balrom.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class PersonRepository, предназначен для CRUD операций с объектом типа Person
 */

public class PersonRepository implements Repository<Person> {
    private final static String SELECTID = "SELECT * FROM person WHERE id=?";
    private final static String SELECTALL = "SELECT * FROM person";
    private final static String INSERT = "INSERT INTO person (name, surname, age) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE person SET name=?, surname=?, age=? WHERE id=?";
    private final static String DELETE = "DELETE FROM person WHERE id=?";

    private final DBConnector connector;

    /**
     * Конструктор, создающий подключение в БД MySql
     */
    public PersonRepository() {
        connector = new DBConnector();
    }

    /**
     * получает объект типа Person по  id
     * @param id - входной параметр id
     * @return - возвращает объект типа Person
     */
    @Override
    public Person get(int id) {
        Person person = null;

        try (Connection connection = connector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECTID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                person.setAge(resultSet.getInt("age"));

            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return person;
    }

    /**
     * @return возвращает List<Person>
     */
    @Override
    public List<Person> getAll() {
        List<Person> list = new ArrayList<>();
        try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECTALL)) {
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                person.setAge(resultSet.getInt("age"));
                list.add(person);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return list;
    }

    /**
     * сохраняет объект типа Person
     * @param person для сохранения в БД
     */
    @Override
    public void save(Person person) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * изменяет объект типа Person
     * @param person для изменения
     */
    @Override
    public void update(Person person) {

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setInt(4, person.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * удаляет объект типа Person по  id
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
