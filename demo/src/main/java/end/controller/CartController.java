package end.controller;

import end.bean.Cart;
import end.bean.User;
import end.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    @Autowired
    private ICartService cartService;

    @RequestMapping("/cartAdd")
    public String cartAdd(Cart addCart, HttpServletRequest request,Model model) {
        boolean flag = false;     //添加到购物车的商品是否是已添加过的即是否已经在session列表中    初始为为未添加过的，未session购物车列表中
        boolean loginFlag=false; //登录状态标记    初始未登录


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            loginFlag=true;
        }



        //不必登录在session中存放购物车列表
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        /**
         * 当用户登录且用户没有购物车记录时cartList已实例化，
         * 但size为0没有数据项，for循环不执行
         * 在已登录并且用户没有购物车记录时
         * 不能写为cartList==null
         */
        if (cartList==null) {
            cartList = new ArrayList<>();

            //如果以登录查询登录账号的购物车
            if (loginFlag) {
                Map<String,Object> map=new HashMap<>();
                map.put("uid",user.getId());
                cartList = cartService.queryCart(map);

            }

        }

        //添加的商品和购物车商品相同则数量加一不同则添加到购物车
        if(cartList.size()==0){//购物车为空时
            cartList.add(addCart);//添加到购物车
            if (loginFlag) {
                addCart.setUid(user.getId()+"");
                cartService.cartAdd(addCart);
            }
        }else {//购物车不为空时
            for (Cart cartItem : cartList) { //相同商品数量相加
                if (addCart.getPid() == cartItem.getPid()) {

                    cartItem.setNum(cartItem.getNum() + addCart.getNum());
                    //如果已登录同步更新到数据库购物车表中
                    if(loginFlag){
                        cartService.updateCart(cartItem);
                    }
                    flag=true;
                    break;
                }else {

                    //如果已登录则插入到数据库
                    if (loginFlag) {
                        addCart.setUid(user.getId()+"");
                        cartService.cartAdd(addCart);
                    }
                }

            }


            if (!flag)//未在session列表中
            {
                cartList.add(addCart);//添加到购物车session列表
            }


        }

        model.addAttribute("success","商品已成功加入购物车");
        session.setAttribute("cartList", cartList);

        return "myCart";
    }


    @RequestMapping("/toMyCart")
    public String myCart(HttpServletRequest request, Model model)
    {
        float sum;


        HttpSession session=request.getSession(false);
        if (session!=null&&session.getAttribute("user")!=null){

            User user=(User) session.getAttribute("user");
            List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");


            if (cartList == null) {
                // 没有cart属性，购物车属性不存在，为用户创建一个空的购物车
                cartList = new ArrayList<Cart>();
                // 将购物车保存到session对象中

                Map<String,Object> map=new HashMap<>();
                map.put("uid",user.getId());
                cartList= cartService.queryCart(map);  //在数据库购物车信息恢复到mycart中

                session.setAttribute("cartList", cartList);
            }
            //算总价
            sum=0;
            for (Cart cartItem:cartList)
                sum+= cartItem.getPrice()* cartItem.getNum();
            session.setAttribute("sum", sum);   //存到session的属性sum中


            model.addAttribute("state",200);//用户登录状态 200已登录，500未登录
        }else {

            model.addAttribute("state",500);//用户登录状态 200已登录，500未登录
        }


        return "myCart";
    }

    @GetMapping("/deleteCart")
    @ResponseBody
    public boolean deleteCart(int pid,HttpServletRequest request){
        HttpSession session=request.getSession(false);
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        User user=(User)session.getAttribute("user");

        for (Cart cartItem:cartList) {
            if(cartItem.getPid()==pid)         {
                cartList.remove(cartItem);  //删除购物车的条目 根据pid
                //已登录则同时删除购物车表记录
                if(user!=null){
                    cartService.deleteCart(user.getId(),pid);
                }

                break;
            }
        }
        session.setAttribute("cartList", cartList);
//        return orderService.deleteCart(id)>0?true:false;
        return true;
    }
}
