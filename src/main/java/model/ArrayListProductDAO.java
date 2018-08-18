package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArrayListProductDAO implements ProductDAO{
    private static List<Product> products;

    public static List<Product> getInstance(){
        if(products == null){
            products = new ArrayList<Product>();
        }
        return products;
    }

    public ArrayListProductDAO() {
        products = getInstance();
        Product product = new Product();
        product.setCode("code 1");
        product.setDescription("description 1");
        product.setPrice(new BigDecimal(100));
        product.setStock(100);
        save(product);

        product = new Product();
        product.setCode("code 2");
        product.setDescription("description 2");
        product.setPrice(new BigDecimal(200));
        product.setStock(200);
        save(product);

        product = new Product();
        product.setCode("code 3");
        product.setDescription("description 3");
        product.setPrice(new BigDecimal(300));
        product.setStock(300);
        save(product);

        product = new Product();
        product.setCode("code 4");
        product.setDescription("description 4");
        product.setPrice(new BigDecimal(400));
        product.setStock(0);
        save(product);

        product = new Product();
        product.setCode("code 5");
        product.setDescription("description 5");
        product.setPrice(new BigDecimal(-500));
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
        List<Product> productList = products.stream().filter(a -> a.getPrice().signum() == 1 && a.getStock() > 0).
                collect(Collectors.toList());
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
