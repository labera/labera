package lab.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl  {

    private final String INSERT_SQL = "INSERT INTO user_master (user_id,user_name,password, branch_id) values(?,?,?,?)";
    private final String FETCH_SQL = "select user_id,user_name,password, branch_id from user_master";
    private final String FETCH_SQL_BY_ID = "select user_id,user_name,password, branch_id from user_master where user_id = ? ";
    private final String FETCH_SQL_BY_ID_PWD = "select user_id,user_name,password, branch_id from user_master where user_name = ? and password=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap create(final UserMaster user) {
        HashMap resp = new HashMap();

        List userNew= findUserById(user.getUser_id());
        if (userNew!=null&&userNew.size()>0) {
            resp.put("message", "User already exists");
            System.out.println("Inside if.........."+userNew.toString());
        }
        else{
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUser_id());
                ps.setString(2, user.getUser_name());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getBranch());
                return ps;
            }
        });
            System.out.println("Inside else.........."+user.toString());
            resp.put("message","User created successfully");
        }

        resp.put("user",user);
        return resp;
    }

    public List findAll() {
        return jdbcTemplate.query(FETCH_SQL, new UserMapper());
    }

    public List findUserById(String id) {
        return jdbcTemplate.query(FETCH_SQL_BY_ID, new Object[] { id}, new UserMapper());
    }
    public  List findUserByIdAndPassword(String id, String password) {
        return jdbcTemplate.query(FETCH_SQL_BY_ID_PWD, new Object[] { id, password}, new UserMapper());
    }
}





