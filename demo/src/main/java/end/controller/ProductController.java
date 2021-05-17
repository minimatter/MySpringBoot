package end.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import end.bean.Product;
import end.bean.Sort;
import end.service.IProductService;
import end.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/toProductDetail")
    public String detail(int id,Model model){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        Product product=productService.queryProduct(1,1,map).get(0);
        model.addAttribute("product",product);
        return "productDetail";

    }

    @RequestMapping("/getPicture")
    public String getPicture(Model model){
        Map<String,Object> map=new HashMap<>();
        map.put("id",74);
        String fileName=productService.queryProduct(1,1,map).get(0).getFileName();
        String filename = "/images/" + fileName;
        System.out.println(fileName);
        model.addAttribute("filename", filename);
        return "file";
    }


    @RequestMapping("/toProductList")
    public String productList(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                          int pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "5")
                                          int pageSize,
                              @RequestParam Map<String,Object> queryMap,
                              Model model){

        List<Product> productList= productService.queryProduct(pageNum, pageSize,queryMap);


        //向前台传递分页信息
        model.addAttribute("pageInfo",new PageInfo<>(productList));


        model.addAttribute("queryMap",queryMap);
        model.addAttribute("productList",productList);
        return "productList";

    }



    @RequestMapping("/toProductAdd")
    public String toProductAdd(){
        return "productAdd";
    }

    @Value("${upload.uploadPath}")
    private String uploadPath;


    @PostMapping("/productAdd")
    @ResponseBody
    public boolean productAdd(Product product){
        //把字符串转换成日期格式
        ObjectMapper mapper=new ObjectMapper();
        mapper.getDateFormat();
        product.setTime(new Date());

        MultipartFile file=product.getProductPicture();
        String fileName= FileUtil.uploadFileSingle(file,uploadPath);
        System.out.println(fileName);
        product.setFileName( "/images/"+fileName);

        return productService.insertProduct(product)>0?true:false;
    }




    @GetMapping("/getSortList")
    @ResponseBody
    public List<Sort> getSortList(){
        return productService.querySort();
    }

}
