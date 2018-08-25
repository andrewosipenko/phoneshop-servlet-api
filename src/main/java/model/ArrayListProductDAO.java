package model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDAO implements ProductDAO{
    private List<Product> products;
    private static volatile ArrayListProductDAO instance = null;

    private static Long counter = 1L;

    public static synchronized Long generateId(){
        return counter++;
    }

    public static ArrayListProductDAO getInstance(){
        if (instance == null){
            synchronized (ArrayListProductDAO.class){
                if (instance == null)
                    instance = new ArrayListProductDAO();
            }
        }
        return instance;
    }

    private ArrayListProductDAO() {
        products = new ArrayList<>();
    }

    public Product getProduct(Long id) {
        for(Product product : products){
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

    public List<Product> findProducts() {
        List<Product> productList = products.stream()
                .filter(a -> a.getPrice() != null && a.getStock() > 0)
                .collect(Collectors.toList());
        return productList;
    }

    public void save(Product product) {
        products.add(product);
    }

    public void remove(Long id) {
        /*for(Product product : products){
            if(product.getId().equals(id)){
                products.remove(product);
            }
        }*/
        products.remove(getProduct(id));
    }
}
