package end.service;

import end.bean.Product;
import end.bean.Sort;

import java.util.List;
import java.util.Map;

public interface IProductService {
    public List<Product> queryProduct(int pageNum,int pageSize, Map<String, Object> map);

    public List<Sort>  querySort();

    public int insertProduct(Product product);

    public int updateProduct(Product product);

    public int deleteProduct(int id);
}
