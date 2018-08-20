package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao{
    private static volatile ArrayListProductDao instance;
    private List<Product> productList;

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<Product> findProducts() {
        return productList
                .stream()
                .filter((p) -> p.getPrice().signum() == 1 && p.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProduct(Long id) {
        return productList
                .stream()
                .filter((product) -> product.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public void save(Product product) {
        if (productList.stream()
                .noneMatch((p) -> p.equals(product)))
            productList.add(product);
    }

    @Override
    public void remove(Long id) {
        productList.remove(getProduct(id));
    }
}

