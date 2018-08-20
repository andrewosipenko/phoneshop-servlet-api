package model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDAO implements ProductDAO{
    private List<Product> products;
    private static ArrayListProductDAO instance = null;

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

        save(new Product(generateId(), "code 1", "description 1", new BigDecimal(100),
                Currency.getInstance(Locale.UK), 100));
        save(new Product(generateId(), "code 2", "description 2", new BigDecimal(200),
                Currency.getInstance(Locale.UK), 200));
        save(new Product(generateId(), "code 3", "description 3", new BigDecimal(300),
                Currency.getInstance(Locale.UK), 300));
        save(new Product(generateId(), "code 4", "description 4", new BigDecimal(400),
                Currency.getInstance(Locale.UK), 0));
        save(new Product(generateId(), "code 5", "description 5", null,
                Currency.getInstance(Locale.UK), 500));
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
