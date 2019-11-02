package com.es.phoneshop.model.product;
import com.es.phoneshop.model.exception.ProductNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static final ProductDao INSTANCE = new ArrayListProductDao();
    private List<Product> products;

    private ArrayListProductDao(){
        products = new ArrayList<>();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow( () -> new ProductNotFoundException( "Product is not found"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product newProduct) {
        if (products.stream().anyMatch( product -> product.getId().equals(newProduct.getId()))){
            throw new IllegalArgumentException("Product with such id is already exists");
        }
        if (newProduct != null && newProduct.getId() != null){
            products.add(newProduct);
        }
        else {
            throw new IllegalArgumentException("Product or id can not be null");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .map(product -> products.remove(id.intValue() - 1))
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public synchronized List<Product> findProductsByDescription(String query){
    List<String> keywords = Arrays.asList(query.split("\\s"));
    List<Product> allProducts = findProducts();
    Map<Product, Integer> mapOfMatches = allProducts.stream()
            .collect(Collectors.toMap(Function.identity(), product -> (int) keywords.stream()
                    .filter(product.getDescription()::contains)
                    .count()));
       return mapOfMatches.keySet().stream()
            .filter(product -> mapOfMatches.get(product) > 0)
            .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> sort(List<Product> unsortedProducts, String productField, String order){
        return new ArrayList<>();
    }

}
