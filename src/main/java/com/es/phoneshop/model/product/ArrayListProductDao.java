package com.es.phoneshop.model.product;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> productList = new ArrayList<>();

    List<Product> getProductList() {
        return productList;
    }

    @Override
    public Product getProduct(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Such object doesn't exsist"));
    }

    @Override
    public List<Product> findProducts() {
        return productList.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        Optional<Product> saveProduct = productList.stream()
                .filter(prod -> prod.getId().equals(product.getId())).findAny();
        if (saveProduct.isPresent()) {
            throw new IllegalArgumentException("Object's id isn't unic");
        } else {
            productList.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        Product delProd = this.getProduct(id);
        productList.remove(delProd);
    }
}
