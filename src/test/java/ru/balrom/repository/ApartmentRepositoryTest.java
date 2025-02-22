package ru.balrom.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.db.DBConnector;
import ru.balrom.model.Apartment;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ApartmentRepositoryTest {
    private final static String SELECTID = "SELECT * FROM appartment WHERE id=?";
    private final static String SELECTALL = "SELECT * FROM appartment";
    private final static String INSERT ="INSERT INTO appartment (city, roomAmount, personId) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE appartment SET city=?, roomAmount=?, personId=? WHERE id=?";
    private final static String DELETE  = "DELETE FROM appartment WHERE id=?";
    private final static int ID = 1;
    private final static String CITY = "Moscow";
    private final static int ROOM_AMOUNT = 3;
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
    private Repository<Apartment> repository;
    private Apartment apartmentExpected;

    @BeforeEach
    public void set() {
        repository = new ApartmentRepository(connector);
        apartmentExpected = Apartment.builder().id(ID).city(CITY).roomAmount(ROOM_AMOUNT).personId(PERSON_ID).build();
    }

    @Test
    public void getByIdTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(SELECTID)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(ID);
        when(resultSet.getString(2)).thenReturn(CITY);
        when(resultSet.getInt(3)).thenReturn(ROOM_AMOUNT);
        when(resultSet.getInt(4)).thenReturn(PERSON_ID);

        Apartment apartmentActual = repository.get(ID);

        Assertions.assertNotNull(apartmentActual);
        Assertions.assertEquals(apartmentExpected.getId(), apartmentActual.getId());
        Assertions.assertEquals(apartmentExpected.getCity(), apartmentActual.getCity());
        Assertions.assertEquals(apartmentExpected.getRoomAmount(), apartmentActual.getRoomAmount());
        Assertions.assertEquals(apartmentExpected.getPersonId(), apartmentActual.getPersonId());
    }

    @Test
    public void creatPersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(INSERT)).thenReturn(preparedStatement);

        repository.save(apartmentExpected);

        verify(connection).prepareStatement(INSERT);
        verify(preparedStatement).setString(1, apartmentExpected.getCity());
        verify(preparedStatement).setInt(2, apartmentExpected.getRoomAmount());
        verify(preparedStatement).setInt(3, apartmentExpected.getPersonId());
        verify(preparedStatement).executeUpdate();

    }

    @Test
    public void updatePersonTest() throws SQLException, ClassNotFoundException {
        when(connector.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(UPDATE)).thenReturn(preparedStatement);

        repository.update(apartmentExpected);

        verify(connection).prepareStatement(UPDATE);
        verify(preparedStatement).setString(1, apartmentExpected.getCity());
        verify(preparedStatement).setInt(2, apartmentExpected.getRoomAmount());
        verify(preparedStatement).setInt(3, apartmentExpected.getPersonId());
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