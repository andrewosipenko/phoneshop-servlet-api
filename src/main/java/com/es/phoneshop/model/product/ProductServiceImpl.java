package com.es.phoneshop.model.product;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService{
    private final ProductDao productDao;

    public ProductServiceImpl() {
        this.productDao = new ArrayListProductDao();
    }

    //don't know what to return in nonpresent case to provide 404 code :)
    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        return productDao.get(id).get();
    }

    @Override
    public List<Product> findProducts() {
        return productDao.getAll().stream()
                .filter(this::isProductsPricePresent)
                .filter(this::isProductInStock)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }

    //todo search with or clause

    @Override
    public List<Product> findProduct(String q) {
        return productDao.find(q);
    }

    private boolean isProductsPricePresent(Product product){
        return product.getPrice() != null;
    }

    private boolean isProductInStock(Product product){
        return product.getStock() > 0;
    }
}
