package end.dao;

import end.bean.Role;
import end.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {
    public User selectUser(Map<String,Object> map);

    public List<User> queryUser();

    public List<Role> queryRole();

    public int insertUser(User user);

    public int deleteUser(int id);

    public int updateUser(User user);
}
