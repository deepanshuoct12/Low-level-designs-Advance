package org.dynamik.service;


import org.dynamik.dao.StockDao;
import org.dynamik.model.Stock;
import java.util.List;



public class StockService {
    
    private final StockDao stockDao;

    public StockService() {
        this.stockDao = new StockDao();
    }

    public Stock save(Stock stock) {
        return stockDao.save(stock);
    }

    public Stock findById(String id) {
        return stockDao.getById(id);
    }

    public List<Stock> findAll() {
        return stockDao.getAll();
    }

    public void deleteById(String id) {
        stockDao.delete(id);
    }

    public Stock update(Stock stock) {
        return stockDao.save(stock);
    }
    
//    public List<Stock> findAvailableStocks() {
//        return stockDao.findByIsAvailableTrue();
   // }
    
    public boolean updateStockPrice(String stockId, Long newPrice) {
        return stockDao.updateStockPrice(stockId, newPrice);
    }

    
//    public List<Stock> searchStocks(String query) {
//        return stockDao.findByNameContainingIgnoreCase(query);
//    }
}
