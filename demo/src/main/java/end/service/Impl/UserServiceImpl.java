package end.service.Impl;

import com.github.pagehelper.PageHelper;
import end.bean.Role;
import end.bean.User;
import end.dao.UserDao;
import end.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao UserDao;

    @Override
    public User selectUser(Map<String, Object> map) {
        return UserDao.selectUser(map);
    }

    @Override
    public List<User> queryUser(int pageNum,int pageSize) {

        //将参数传给这个方法就可以实现物理分页
        PageHelper.startPage(pageNum, pageSize);
        return UserDao.queryUser();
    }

    @Override
    public List<Role> queryRole() {
        return UserDao.queryRole();
    }

    @Override
    public int insertUser(User user) {
        return UserDao.insertUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return UserDao.deleteUser(id);
    }

    @Override
    public int updateUser(User user) {
        return UserDao.updateUser(user);
    }
}
