package end.dao;

import end.bean.Product;
import end.bean.Sort;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductDao {

    public List<Product> queryProduct(Map<String ,Object> map);

    public List<Sort>  querySort();

    public int insertProduct(Product product);

    public int updateProduct(Product product);

    public int deleteProduct(int id);


}
