package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.common.model.SortingOrder;
import com.es.phoneshop.domain.product.model.Product;
import com.es.phoneshop.domain.product.model.ProductPrice;
import com.es.phoneshop.domain.product.model.ProductRequest;
import com.es.phoneshop.domain.product.model.ProductSortingCriteria;
import com.es.phoneshop.utils.LongIdGenerator;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayListProductDao implements ProductDao {

    private final List<Product> products;

    private final List<ProductPrice> pricesHistory;

    private final Clock clock;

    private final LongIdGenerator idGenerator;

    private final ReadWriteLock lock;

    public ArrayListProductDao(LongIdGenerator idGenerator, Clock clock) {
        this.products = new ArrayList<>();
        this.pricesHistory = new ArrayList<>();
        this.clock = clock;
        this.idGenerator = idGenerator;
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public Optional<Product> getById(Long id) {
        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(it -> id.equals(it.getId()))
                    .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> getAllByRequest(ProductRequest productRequest) {

        Comparator<Product> comparator;
        if (productRequest.getSortingCriteria() == ProductSortingCriteria.PRICE) {
            comparator = Comparator.comparing(Product::getPrice);
        } else {
            comparator = Comparator.comparing(Product::getDescription);
        }

        if (productRequest.getSortingOrder() == SortingOrder.DESC) {
            comparator = comparator.reversed();
        }

        List<String> searchWords = productRequest.getQuery() != null ? Arrays.asList(productRequest.getQuery().split(" ")) : Collections.emptyList();

        lock.readLock().lock();
        try {
            List<Product> products = this.products.stream()
                    .filter(it -> it.getStock() >= (productRequest.getMinStockInclusive()))
                    .filter(it -> it.getPrice() != null)
                    .collect(Collectors.toList());

            if ((productRequest.getQuery() != null) && !productRequest.getQuery().equals("")) {
                Map<Product, Long> productToQueryMatchCount = products.stream().collect(Collectors.toMap(
                        it -> it,
                        it -> searchWords.stream().filter(word -> it.getDescription().contains(word)).count()));

                products = productToQueryMatchCount.entrySet().stream()
                        .filter(it -> it.getValue() > 0)
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }

            if (productRequest.getSortingCriteria() != null) {
                products.sort(comparator);
            }

            return products;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> getAll() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(products);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Long save(Product product) {
        lock.writeLock().lock();
        try {
            if (product.getId() != null) {

                Product productToUpdate = products.stream()
                        .filter(it -> it.getId().equals(product.getId()))
                        .findFirst().orElseThrow(ProductPresistenceException::new);

                int insertingPosition = IntStream.range(0, products.size())
                        .filter(i -> product.getId().equals(products.get(i).getId()))
                        .findFirst().orElseThrow(ProductPresistenceException::new);

                if (!productToUpdate.getPrice().equals(product.getPrice()) || !productToUpdate.getCurrency().equals(product.getCurrency())) {
                    pricesHistory.add(new ProductPrice(product.getId(), LocalDateTime.now(clock), product.getPrice(), product.getCurrency()));
                }

                products.set(insertingPosition, product);
            } else {
                Long productId = idGenerator.getId();
                product.setId(productId);
                products.add(product);
                pricesHistory.add(new ProductPrice(productId, LocalDateTime.now(clock), product.getPrice(), product.getCurrency()));
            }
        } finally {
            lock.writeLock().unlock();
        }
        return product.getId();
    }

    @Override
    public void delete(Long id) {
        lock.writeLock().lock();
        try {
            int foundIndex = IntStream.range(0, products.size())
                    .filter(i -> id.equals(products.get(i).getId()))
                    .findFirst().orElseThrow(ProductPresistenceException::new);
            products.remove(foundIndex);
            int historySize = pricesHistory.size();
            IntStream.range(0, historySize)
                    .map(i -> historySize - 1 - i)
                    .filter(i -> id.equals(pricesHistory.get(i).getProductId()))
                    .forEach(pricesHistory::remove);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<ProductPrice> getPricesHistoryByProductId(Long id) {
        lock.readLock().lock();
        try {
            return pricesHistory.stream()
                    .filter(it -> it.getProductId().equals(id))
                    .sorted(Comparator.comparing(ProductPrice::getFrom))
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
