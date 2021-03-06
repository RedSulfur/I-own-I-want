package com.iowniwant.dao.implementation;

import com.iowniwant.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fills the PreparedStatement with given
 * @see User entity fields.
 */
public class UserDao extends AbstractDaoImpl<User> {
    private static UserDao instance;
    private UserDao() {}

    /**
     * Provides UserDao instance.
     * @return the same UserDao object each time its invoked.
     */
    public static UserDao getInstance (){
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    /**
     * Fills the PreparedStatement with given User entity fields
     * to persist User in the DataBase.
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity user to be persisted.
     */
    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, User entity) {
        try {
            prepStatement.setString(1, entity.getFirstName());
            prepStatement.setString(2, entity.getLastName());
            prepStatement.setString(3, entity.getUserName());
            prepStatement.setString(4, entity.getPassword());
            prepStatement.setString(5, entity.getEmail());
            prepStatement.setDouble(6, entity.getMonthSalary());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the PreparedStatement with given User entity fields
     * to update User in the DataBase.
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity user to be updated.
     */
    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, User entity) {
        try {
            prepStatement.setString(1, entity.getFirstName());
            prepStatement.setString(2, entity.getLastName());
            prepStatement.setString(3, entity.getUserName());
            prepStatement.setString(4, entity.getPassword());
            prepStatement.setString(5, entity.getEmail());
            prepStatement.setDouble(6, entity.getMonthSalary());
            prepStatement.setInt(7, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates User entity by providing resultSet to
     * @see User class constructor.
     * @param resultSet a table of data representing a database result set.
     * @return User entity.
     */
    @Override
    public User getEntity(ResultSet resultSet) {
        try {
            return new User(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns persistent object with given identifier.
     * @param nickname object identifier.
     * @return persistent User object with the given identifier or null if
     * there is no such persistent object.
     */
    public User getByNick(String nickname) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            String query = getGetByNickQuery();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, nickname);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                return getEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null)  try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }

        return null;
    }

    /**
     * @return query to insert User into the DataBase.
     */
    @Override
    public String getCreateQuery() {
        return dbManager.getQuery("create.user");
    }

    /**
     * @return query to delete User from the DataBase.
     */
    @Override
    public String getDeleteQuery() {
        return dbManager.getQuery("delete.user.by.id");
    }

    /**
     * @return query to update User in the DataBase.
     */
    @Override
    public String getUpdateQuery() {
        return dbManager.getQuery("update.user");
    }

    /**
     * @return query to retrieve User from the DataBase using User's ID.
     */
    @Override
    public String getGetByIdQuery() {
        return dbManager.getQuery("get.user.by.id");
    }

    /**
     * @return query to retrieve all Users from the DataBase.
     */
    @Override
    public String getGetAllQuery() {
        return dbManager.getQuery("get.all.user");
    }

    /**
     * @return query to retrieve User from the DataBase using User's NickName.
     */
    private String getGetByNickQuery() {
        return dbManager.getQuery("get.user.by.nick");
    }
}
