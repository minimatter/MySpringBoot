package end.service;

import end.bean.Cart;

import java.util.List;
import java.util.Map;

public interface ICartService {
    //加入购物车
    public int cartAdd(Cart cart);


    //移出购物车
    public int deleteCart(int uid,int pid);

    public List<Cart> queryCart(Map<String ,Object> map);

    //更新购物车商品数量
    public int updateCart(Cart cart);

}
