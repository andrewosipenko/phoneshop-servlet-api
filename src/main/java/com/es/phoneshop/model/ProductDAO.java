package model;

import java.util.List;

public interface ProductDAO {
    Product getProduct(Long id);
    List<Product> findProducts();
    void save(Product product);
    void remove(Long id);
}
