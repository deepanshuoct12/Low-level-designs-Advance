package org.dynamik.dao;

import org.dynamik.model.Stock;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StockDao implements IBaseDao<Stock, String> {

    private static Map<String, Stock> stocks = new ConcurrentHashMap<>();

    @Override
    public Stock save(Stock stock) {
        return stocks.put(stock.getId(), stock);
    }

    @Override
    public Stock getById(String s) {
        return stocks.get(s);
    }

    @Override
    public void update(Stock stock) {
     stocks.put(stock.getId(), stock);
    }

    @Override
    public void delete(String id) {
        stocks.remove(id);
    }

    @Override
    public List<Stock> getAll() {
        return stocks.values().stream().toList();
    }

    public Boolean updateStockPrice(String stockId, Long newPrice) {
        return stocks.computeIfPresent(stockId, (id, stock) -> {
            stock.setPrice(newPrice);
            return stock;
        }) != null;
    }
}
