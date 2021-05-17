package end.service;

import end.bean.Cart;
import end.bean.Orderitem;
import end.bean.Orders;

import java.util.List;

public interface IOrderService {



    List<Orderitem> findByOid(Integer oid);  //查询某订单的所有订单项


    public boolean saveOrder(int uid,String addr,String tel);

    public List<Orders> findOderByUid(Integer id);

}
