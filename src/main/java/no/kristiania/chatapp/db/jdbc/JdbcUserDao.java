package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.User;
import no.kristiania.chatapp.db.UserDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User retrieveUser(int id) throws SQLException {
        try (var conn = dataSource.getConnection()){
            try (var stmt = conn.prepareStatement("select * from [user] where id = ?")){
                stmt.setInt(1, id);
                return collectSingleResult(stmt, JdbcUserDao::readUser);
            }
        }
    }

    private static User readUser(ResultSet rs) throws SQLException {
        var user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;

    }

    @Override
    public void save(User user) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var sql = "insert into [user] (username, password) values (?, ?)";
            try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.executeUpdate();

                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
}
