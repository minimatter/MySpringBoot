package end.controller;


import com.github.pagehelper.PageInfo;
import end.bean.Cart;
import end.bean.Product;
import end.bean.Role;
import end.bean.User;
import end.service.ICartService;
import end.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {


/*
* 没有添加@Autowired注解时报错
* This application has no explicit mapping for
使用@Autowired时
1.Spring先去容器中寻找NewsSevice类型的bean（先不扫描newsService字段）

2.若找不到一个bean，会抛出异常

3.若找到一个NewsSevice类型的bean，自动匹配，并把bean装配到newsService中

4.若NewsService类型的bean有多个，则扫描后面newsService字段进行名字匹配，匹配成功后将bean装配到newsService中
* */
    @Autowired
    private IUserService userService;

    @Autowired
    private ICartService orderService;

    @RequestMapping("/toLogin")
    public String login() {
        return "login";
    }


    @RequestMapping("/toUserList")
    public String productList(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                      int pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "5")
                                      int pageSize,
                              @RequestParam Map<String,Object> queryMap,
                              Model model,
                              HttpServletRequest request){
        HttpSession session=request.getSession(false);

        if(session!=null&&session.getAttribute("user")!=null){
            //String usernameStr=(String)session.getAttribute("userName");
            //String userRid=(String)session.getAttribute("userRid");
            List<User> userList= userService.queryUser(pageNum, pageSize);


            //向前台传递分页信息
            model.addAttribute("pageInfo",new PageInfo<>(userList));


            model.addAttribute("userList",userList);
            //model.addAttribute("usernameStr",usernameStr);
            //model.addAttribute("userRid",userRid);

            return "userList";
        }
        else return "login";


    }



    @RequestMapping("/toRegister")
    public String toRegister(){
        return "userRegister";
    }




    @RequestMapping("/toUserEdit")
    public String toEdit(HttpServletRequest request, Model model,int id){


        HttpSession session=request.getSession(false);
        if(session!=null&&session.getAttribute("user")!=null){

                    Map<String ,Object> map=new HashMap<>();
                    map.put("id",id);
                    User alterUser=userService.selectUser(map);
                    model.addAttribute("alterUser",alterUser);

                    User user=(User)session.getAttribute("user");
            if (user.getId() == id) {
                return "userEdit";
            }else return "alterUser";

        }else return "login";
    }



    @PostMapping("/edit")
    @ResponseBody
    public boolean edit(HttpServletRequest request, User user,String agePwd){
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> adminMap=new HashMap<>();

        map.put("id",user.getId());

        //return userService.updateUser(user)>0?true:false;

        String pwd=userService.selectUser(map).getPassWord();

        HttpSession session=request.getSession(false);
        User handler=(User) session.getAttribute("user");
//        if(DigestUtils.md5DigestAsHex(agePwd.getBytes()).equals(pwd)){
//            return userService.updateUser(user)>0?true:false;
//        }else return false;

        if(handler.getRid().equals("1"))
        {
            return userService.updateUser(user)>0?true:false;
        }else if (DigestUtils.md5DigestAsHex(agePwd.getBytes()).equals(pwd)){//把旧密码agePwd通过md5加密后与数据库密码匹配
            return userService.updateUser(user)>0?true:false;
        }
        else return false;
    }





    @PostMapping("/signIn")
    @ResponseBody
    public boolean singIn(String userName, String passWord, HttpServletRequest request){
        Map<String ,Object> map=new HashMap<>();
        Map<String,Object> cartMap=new HashMap<>();
        Map<String,Object> cartQueryMap=new HashMap<>();

        map.put("userName",userName);
        map.put("passWord",passWord);

        User user=userService.selectUser(map);

        //登录成功
        if(user!=null) {

            HttpSession session=request.getSession();
            session.setAttribute("user",user);

            //如果在未登录的情况下加入了购物车，登录后同步到登录用户的购物车，有相同购物车商品则数量相加，无则添加新纪录
            List<Cart> cartList=(List<Cart>)session.getAttribute("cartList");
            if (cartList!=null&&cartList.size()!=0){

                for (Cart cartItem:cartList){
                    cartItem.setUid(user.getId()+"");

                    cartMap.put("uid",user.getId());
                    cartMap.put("pid",cartItem.getPid());


                    if (orderService.queryCart(cartMap).size()!=0){
                        Cart cart=orderService.queryCart(cartMap).get(0);//List 的size是0时,get(0) 会报IndexOutOfBoundsException

                        //用户购物车中已有没登录时加入购物车的商品则数量加一
                        cartItem.setNum(cartItem.getNum()+cart.getNum()) ;
                        orderService.updateCart(cartItem);

                    }else {
                        orderService.cartAdd(cartItem);
                    }
                }
            }

            cartQueryMap.put("uid",user.getId());
            cartList=orderService.queryCart(cartQueryMap);
            session.setAttribute("cartList",cartList);
            return true;
        }
        else return false;
    }

    @RequestMapping("/quitUser")
    public String quitUser(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        session.invalidate();
        return "index";
    }

    @PostMapping("/register")
    @ResponseBody
    public boolean register(User user){
        return   userService.insertUser(user)>0?true:false;
    }


    @GetMapping("/getRoleList")
    @ResponseBody
    public List<Role> getRoleList(){
        return userService.queryRole();
    }



    @GetMapping("/userDelete")
    @ResponseBody
    public boolean userDelete(int id){
        return userService.deleteUser(id)>0?true:false;
    }

//    @ResponseBody
//    @RequestMapping("/signIn")
//    public String  signIn(String userName,String userPassWord){
//        Map<String,Object> map=new HashMap<>();
//        map.put("userName",userName);
//        map.put("userPassWord",userPassWord);
///*
//* 因为JAVA中String并非基本数据类型而是一个类, 变量名实际代表地址,
//*==运算符只能够确定两个字符串是否放在同一个地址
//* */
//        if(userService.selectUser(map).getUserName()!=null) {
//            if (userPassWord.equals(userService.selectUser(map).getPassWord()))
//                return "2000";
//            else return "5000";
//        }
//        else return "5000";
//
//    }
}
