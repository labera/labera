package lab.user;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserMapper implements RowMapper {

    @Override
    public UserMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMaster user = new UserMaster();
        user.setUser_id(rs.getString("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setPassword(rs.getString("password"));
        user.setBranch(rs.getString("branch_id"));
        return user;
    }
}

