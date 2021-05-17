package end.service.Impl;

import com.github.pagehelper.PageHelper;
import end.bean.Product;
import end.bean.Sort;
import end.dao.ProductDao;
import end.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class  ProductServiceImpl implements IProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> queryProduct(int pageNum,int pageSize, Map<String, Object> map) {
        //将参数传给这个方法就可以实现物理分页
        PageHelper.startPage(pageNum, pageSize);

        return productDao.queryProduct(map) ;
    }

    @Override
    public List<Sort> querySort() {
        return productDao.querySort();
    }

    @Override
    public int insertProduct(Product product) {
        return productDao.insertProduct(product);
    }

    @Override
    public int updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public int deleteProduct(int id) {
        return productDao.deleteProduct(id);
    }
}
