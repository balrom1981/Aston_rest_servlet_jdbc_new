package ru.balrom.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.db.DBConnector;
import ru.balrom.model.Person;

import java.sql.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {
    private final static String SELECTID = "SELECT * FROM person WHERE id=?";
    private final static String INSERT = "INSERT INTO person (name, surname, age) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE person SET name=?, surname=?, age=? WHERE id=?";
    private final static String DELETE = "DELETE FROM person WHERE id=?";
    private final static int ID = 1;
    private final static String NAME = "Ivan";
    private final static String SURNAME = "Smirnov";
    private final static int AGE = 18;

    @Mock
    private DBConnector connector;
    @Mock
    Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Repository<Person> repository;
    private Person personExpected;

    @BeforeEach
    public void set() {
        repository = new PersonRepository(connector);
        personExpected = Person.builder().id(ID).name(NAME).surname(SURNAME).age(AGE).build();
    }

    @Test
    public void getByIdTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(SELECTID)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(ID);
        when(resultSet.getString(2)).thenReturn(NAME);
        when(resultSet.getString(3)).thenReturn(SURNAME);
        when(resultSet.getInt(4)).thenReturn(AGE);

        Person personActual = repository.get(ID);

        Assertions.assertNotNull(personActual);
        Assertions.assertEquals(personExpected.getId(), personActual.getId());
        Assertions.assertEquals(personExpected.getName(), personActual.getName());
        Assertions.assertEquals(personExpected.getSurname(), personActual.getSurname());
        Assertions.assertEquals(personExpected.getAge(), personActual.getAge());
    }

    @Test
    public void creatPersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(INSERT)).thenReturn(preparedStatement);

        repository.save(personExpected);

        verify(connection).prepareStatement(INSERT);
        verify(preparedStatement).setString(1, personExpected.getName());
        verify(preparedStatement).setString(2, personExpected.getSurname());
        verify(preparedStatement).setInt(3, personExpected.getAge());
        verify(preparedStatement).executeUpdate();

    }

    @Test
    public void updatePersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(UPDATE)).thenReturn(preparedStatement);

        repository.update(personExpected);

        verify(connection).prepareStatement(UPDATE);
        verify(preparedStatement).setString(1, personExpected.getName());
        verify(preparedStatement).setString(2, personExpected.getSurname());
        verify(preparedStatement).setInt(3, personExpected.getAge());
        verify(preparedStatement).executeUpdate();



    }

    @Test
    public void deletePersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(DELETE)).thenReturn(preparedStatement);

        repository.delete(ID);

        verify(connection).prepareStatement(DELETE);
        verify(preparedStatement).executeUpdate();

    }

}