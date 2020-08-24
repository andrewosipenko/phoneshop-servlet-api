package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.sortEnums.SortField;
import com.es.phoneshop.model.product.sortEnums.SortOrder;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public enum ProductServiceImpl implements ProductService {

    INSTANCE;

    private final ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        try {
            return productDao.getItem(id).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Product with id " + id + "not found");
        }
    }


    @Override
    public Product getProduct(String pathInfo) throws NoSuchElementException {
        Product result;
        try {
            long longId;
            longId = Integer.parseInt(pathInfo.split("/")[1]);
            result = productDao.getItem(longId).get();
        } catch (NumberFormatException | NoSuchElementException e) {
            //could be created special IncorrectPathInfoException
            throw new NoSuchElementException(pathInfo.split("/")[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException(" ");
        }
        return result;
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


    @Override
    public List<Product> findProducts(String sort, String order, String query) {

        Comparator<Product> comparator = Comparator.comparing(product -> {
            //also could be used switch with enums
            if (String.valueOf(SortField.description).equals(sort)) {
                return (Comparable) product.getDescription();
            }
            if (String.valueOf(SortField.price).equals(sort)) {
                return ((Comparable) product.getPrice());
            }
            //default value
            return ((Comparable) Comparator.naturalOrder());
        });

        if (String.valueOf(SortOrder.desc).equals(order)) {
            comparator = comparator.reversed();
        }

        return productDao.find(query)
                .stream()
                .filter(this::isProductsPricePresent)
                .filter(this::isProductInStock)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private boolean isProductsPricePresent(Product product) {
        return product.getPrice() != null;
    }

    private boolean isProductInStock(Product product) {
        return product.getStock() > 0;
    }
}
