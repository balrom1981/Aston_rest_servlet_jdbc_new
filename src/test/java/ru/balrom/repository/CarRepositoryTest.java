package ru.balrom.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.db.DBConnector;
import ru.balrom.model.Car;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {
    private final static String SELECTID = "SELECT * FROM car WHERE id=?";
    private final static String SELECTALL = "SELECT * FROM car";
    private final static String INSERT = "INSERT INTO car (brand, colour, personId) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE car SET brand=?, colour=?, personId=? WHERE id=?";
    private final static String DELETE  = "DELETE FROM car WHERE id=?";
    private final static int ID = 1;
    private final static String BRAND = "LADA";
    private final static String COLOUR = "Black";
    private final static int PERSON_ID = 1;

    @Mock
    private DBConnector connector;
    @Mock
    Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Repository<Car> repository;
    private Car carExpected;

    @BeforeEach
    public void set() {
        repository = new CarRepository(connector);
        carExpected = Car.builder().id(ID).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build();
    }

    @Test
    public void getByIdTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(SELECTID)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(ID);
        when(resultSet.getString(2)).thenReturn(BRAND);
        when(resultSet.getString(3)).thenReturn(COLOUR);
        when(resultSet.getInt(4)).thenReturn(PERSON_ID);

        Car carActual = repository.get(ID);

        Assertions.assertNotNull(carActual);
        Assertions.assertEquals(carExpected.getId(), carActual.getId());
        Assertions.assertEquals(carExpected.getBrand(), carActual.getBrand());
        Assertions.assertEquals(carExpected.getColour(), carActual.getColour());
        Assertions.assertEquals(carExpected.getPersonId(), carActual.getPersonId());
    }

    @Test
    public void creatPersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(INSERT)).thenReturn(preparedStatement);

        repository.save(carExpected);

        verify(connection).prepareStatement(INSERT);
        verify(preparedStatement).setString(1, carExpected.getBrand());
        verify(preparedStatement).setString(2, carExpected.getColour());
        verify(preparedStatement).setInt(3, carExpected.getPersonId());
        verify(preparedStatement).executeUpdate();

    }

    @Test
    public void updatePersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(UPDATE)).thenReturn(preparedStatement);

        repository.update(carExpected);

        verify(connection).prepareStatement(UPDATE);
        verify(preparedStatement).setString(1, carExpected.getBrand());
        verify(preparedStatement).setString(2, carExpected.getColour());
        verify(preparedStatement).setInt(3, carExpected.getPersonId());
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