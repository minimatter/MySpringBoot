package end.service.Impl;

import end.bean.Cart;
import end.dao.CartDao;
import end.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartDao cartDao;

    @Override
    public int cartAdd(Cart cart) {
        return cartDao.cartAdd(cart);
    }


    @Override
    public int deleteCart(int uid,int pid) {
        return cartDao.deleteCart(uid,pid);
    }

    @Override
    public List<Cart> queryCart(Map<String,Object> map) {
        return cartDao.queryCart(map);
    }

    @Override
    public int updateCart(Cart cart) {
        return cartDao.updateCart(cart);
    }
}
