package com.es.phoneshop.model.product;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.ProductNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static volatile ProductDao INSTANCE;
    private static final String PRICE = "price";
    private static final String DESCRIPTION = "description";
    private static final String DESC = "desc";
    private List<Product> products;
    private Map<String, Comparator<Product>> comparators;

    private ArrayListProductDao()
    {
        products = new ArrayList<>();
        comparators = new HashMap<>();
        comparators.put(PRICE, Comparator.comparing(Product::getPrice));
        comparators.put(DESCRIPTION, Comparator.comparing(Product::getDescription));
    }

    public static ProductDao getInstance() {
        if(INSTANCE == null){
            synchronized (HttpSessionCartService.class){
                if(INSTANCE == null){
                    INSTANCE = new ArrayListProductDao();
                }
            }
        }
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
        if (newProduct != null){
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
        if (productField != null && !productField.isEmpty()) {
            return unsortedProducts.stream()
                    .sorted(getComparator(productField, order))
                    .collect(Collectors.toList());
        } else {
            return unsortedProducts;
        }
    }

    private Comparator<Product> getComparator(String productField, String order) {
        if (!comparators.containsKey(productField)) {
            throw new IllegalArgumentException("There is no comparator for sorting");
        }
        Comparator<Product> comparator = comparators.get(productField);
        if (DESC.equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

}
