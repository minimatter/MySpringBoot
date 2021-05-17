package end.service.Impl;

import end.bean.Cart;
import end.bean.Orderitem;
import end.bean.Orders;
import end.dao.CartDao;
import end.dao.OrderDao;
import end.dao.ProductDao;
import end.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class  OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CartDao cartDao;


    /**
     *
     * @param id    订单id在orderItem中查询出订单中的商品
     * @return      返回添加商品名称的订单中的商品，
     */

    public List<Orderitem> findByOid(Integer id){


        Map<String ,Object> map=new HashMap<>();


        List<Orderitem> orderitems= orderDao.findByOid(id);
        for(Orderitem orderitem:orderitems){
            map.put("id",orderitem.getPid());
            orderitem.setProName((productDao.queryProduct(map)).get(0).getProName());
        }


        return  orderitems;

    }






    public float getPrice(List<Cart> cart){  //使用数据库中最新的价格（price）数据进行计算总价
        Map<String ,Object> map=new HashMap<>();
        float total=0;
        for(Cart cartitem:cart) {
            map.put("pid",cartitem.getPid());
            cartitem.setPrice(productDao.queryProduct(map).get(0).getPrice());
            //   cartitem.setPro_name(productMapper.findById(cartitem.getPid()).getPro_name());
            total=total+cartitem.getNum()*cartitem.getPrice();
        }
        return total;

    }
    @Transactional    //事务注解，下面这个函数涉及两个表的关联操作，如果中途有异常，则全部放弃而不会只做一半
    public boolean saveOrder(int uid,String addr,String tel)  {
        Map<String ,Object> map=new HashMap<>();
        map.put("uid",uid);
        List<Cart> cart= cartDao.queryCart(map);  //根据uid在数据库购物车表里把内容取出填充到cart列表中  也可以在控制器里通过session获取购物车列表，然后作为参数传递过来
        String lid;

        Orders orders=new Orders();
        orders.setAddr(addr);
        orders.setUid(uid);
        orders.setTel(tel);
        orders.setTotal(getPrice(cart));



        orderDao.insertOrder(orders);  //初始化一个订单项，注意total总价重新算了一遍，避免购物车里的数据更新不够及时（只有添加购物车选项和从数据库里转载购物车时候才会更新price）
        System.out.println(orders.getId());
        lid= orders.getId()+"";  //插入成功后把刚才订单的id号取出，下面创建订单子项的时候 所有子项都属于这个订单

        for(Cart cartitem:cart) {   //把购物车子项一个个取出来，初始化一个个订单子项，然后一个个插入表orderitem
            map.put("id",cartitem.getPid());
            Orderitem orderitem=new Orderitem();

            orderitem.setOid(lid);
            orderitem.setPid(cartitem.getPid());
            orderitem.setPrice(productDao.queryProduct(map).get(0).getPrice());
            orderitem.setNum(cartitem.getNum());

            orderDao.insertOrderitem(orderitem);
            cartDao.deleteCart(uid,cartitem.getPid());  //买完了就在购物车表中删除此项
        }
        return true;
    }






    public List<Orders> findOderByUid(Integer id){
        return orderDao.findOderByUid(id);}






}
