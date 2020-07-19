package com.es.phoneshop.model.product;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    //don't know what to return in nonpresent case to provide 404 code :)
    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        return productDao.get(id).get();
    }


    @Override
    public Product getProduct(String pathInfo) throws NoSuchElementException {
        long longId;
        Product result;
        try {
            longId = Integer.parseInt(pathInfo.split("/")[1]);
            result = productDao.get(longId).get();
        } catch (NumberFormatException | NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            //could be created special IncorrectPathInfoException
            throw new NoSuchElementException(pathInfo.split("/")[1]);
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


    //Andrei wanted to use comparators chain but i think it would be overkill
    @Override
    public List<Product> findProduct(String sort, String order, String query) {

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
