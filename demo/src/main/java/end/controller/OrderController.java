package end.controller;


import end.bean.User;
import end.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/order")
    public String order(String addr, String tel, HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if(session!=null&&session.getAttribute("user")!=null){
            User user= (User)session.getAttribute("user");
            orderService.saveOrder(user.getId(),addr,tel);
            return "redirect:/toMyOrders";
        }else return "login";


    }

    @RequestMapping("/toMyOrders")        //显示我的订单页
    public ModelAndView myOrders(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        ModelAndView mv=new ModelAndView();
        if(user!=null) {
            mv.addObject("orders",orderService.findOderByUid(user.getId()));
            mv.setViewName("myOrders");
        }
        else mv.setViewName("login");

        return mv;
    }



    @RequestMapping("/toOrderItem")        //显示我的订单页
    public ModelAndView toOrderItem(String id){
        ModelAndView mv=new ModelAndView();
        mv.addObject("orderItems",orderService.findByOid(Integer.valueOf(id)));
        mv.setViewName("orderitemshow");
        return mv;
    }
}
