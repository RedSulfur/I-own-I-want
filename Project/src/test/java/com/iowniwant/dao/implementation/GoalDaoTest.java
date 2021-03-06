package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import com.iowniwant.util.InitialContextFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GoalDaoTest extends Mockito {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Goal goal;

    private GoalDao goalDao = GoalDao.getInstance();
    private int id = 99;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws SQLException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(goal.getUser()).thenReturn(new User());
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setDate(anyInt(), any(Date.class));
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void GetGoalsByUserIdTest() throws Exception {

        goalDao.getGoalsByUserId(id);

        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setInt(eq(1), anyInt());
        verify(preparedStatement, times(2)).setInt(eq(1), anyInt());
        verify(preparedStatement, times(2)).executeQuery();
    }

    @Test
    public void GoalDaoCreateTest() throws SQLException {

        goalDao.create(goal);

        verify(connection, atLeastOnce()).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, atLeastOnce()).executeUpdate();

        InOrder inOrder = inOrder(connection, preparedStatement);
        inOrder.verify(connection).prepareStatement(anyString());
        inOrder.verify(preparedStatement).setInt(anyInt(), anyInt());
        inOrder.verify(preparedStatement).executeQuery();
    }

    @Test
    public void GoalDaoGetByIdTest() throws SQLException {

        goalDao.getById(id);

        InOrder inOrder = inOrder(connection, preparedStatement);
        inOrder.verify(connection).prepareStatement(anyString());
        inOrder.verify(preparedStatement).setInt(anyInt(),anyInt());
        inOrder.verify(preparedStatement).executeQuery();
    }

    @Test
    public void UpdateGoalTest() throws SQLException {

        goalDao.update(goal);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());

        verify(preparedStatement, times(3)).setString(anyInt(),anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setDate(anyInt(), any(Date.class));
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();

        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void GoalDaoDeleteTest() throws SQLException {

        goalDao.delete(id);

        verify(dataSource, times(1)).getConnection();
        verify(connection, atLeastOnce()).prepareStatement(anyString());
        verify(preparedStatement, atLeastOnce()).setInt(eq(1), anyInt());
        verify(preparedStatement, atLeastOnce()).execute();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void GoalDaoGetAllTest() throws SQLException {

        goalDao.getAll();

        verify(dataSource, times(2)).getConnection();
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, atLeastOnce()).executeQuery();
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(resultSet, times(3)).next();
        verify(connection, times(2)).close();
        verifyNoMoreInteractions(connection);
    }
}
