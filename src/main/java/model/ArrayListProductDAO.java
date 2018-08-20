package model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDAO implements ProductDAO{
    private static List<Product> products;

    private static Long counter = 1L;

    public static synchronized Long generateId(){
        return counter++;
    }

    public static List<Product> getInstance(){
        if(products == null){
            products = new ArrayList<Product>();
        }
        return products;
    }

    public ArrayListProductDAO() {
        products = getInstance();
        Product product = new Product();
        product.setId(generateId());
        product.setCode("code 1");
        product.setDescription("description 1");
        product.setPrice(new BigDecimal(100));
        product.setCurrency(Currency.getInstance(Locale.UK));
        product.setStock(100);
        save(product);

        product = new Product();
        product.setId(generateId());
        product.setCode("code 2");
        product.setDescription("description 2");
        product.setPrice(new BigDecimal(200));
        product.setCurrency(Currency.getInstance(Locale.UK));
        product.setStock(200);
        save(product);

        product = new Product();
        product.setId(generateId());
        product.setCode("code 3");
        product.setDescription("description 3");
        product.setPrice(new BigDecimal(300));
        product.setCurrency(Currency.getInstance(Locale.UK));
        product.setStock(300);
        save(product);

        product = new Product();
        product.setId(generateId());
        product.setCode("code 4");
        product.setDescription("description 4");
        product.setPrice(new BigDecimal(400));
        product.setCurrency(Currency.getInstance(Locale.UK));
        product.setStock(0);
        save(product);

        product = new Product();
        product.setId(generateId());
        product.setCode("code 5");
        product.setDescription("description 5");
        product.setPrice(null);
        product.setCurrency(Currency.getInstance(Locale.UK));
        product.setStock(500);
        save(product);
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
        for(Product product : products){
            if(product.getId().equals(id)){
                products.remove(product);
            }
        }
    }
}
