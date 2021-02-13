package com.steamnonesteam.configuration;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.steamnonesteam.model.Game;
import com.steamnonesteam.model.User;
import com.steamnonesteam.model.UserGame;
import org.omg.CORBA.portable.ApplicationException;

import java.sql.SQLException;

public class JdbcDatabaseConfiguration implements DatabaseConfiguration{
    private final ConnectionSource connectionSource;

    public JdbcDatabaseConfiguration(String jdbcConnectionString) throws SQLException {
            connectionSource = new JdbcConnectionSource(jdbcConnectionString);
    }

    @Override
    public ConnectionSource connectionSource() {
        return connectionSource;
    }
}
