package end.dao;

import end.bean.Orderitem;
import end.bean.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderDao {

    List<Orders> findOderByUid(Integer uid);  //查询某人的所有订单

    Integer insertOrder(Orders orders);  //插入订单

    Integer findLastId();  //找到刚才插入的order记录id

    Integer delCart(Integer id);  //删除订单


    List<Orderitem> findByOid(Integer oid);  //查询某订单的所有订单项

    Integer insertOrderitem(Orderitem orderitem);  //插入订单

    //Integer delCart(Integer id);  //删除订单

}
