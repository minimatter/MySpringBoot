package end.service;

import end.bean.Role;
import end.bean.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public User selectUser(Map<String,Object> map);

    public List<User> queryUser(int pageNum,int pageSize);

    public List<Role> queryRole();

    public int insertUser(User user);

    public int deleteUser(int id);

    public int updateUser(User user);
}
