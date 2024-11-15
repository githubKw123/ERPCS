package net.kingborn.erp.wc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.wc.dao.StockDao;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.model.Stock;
import net.kingborn.erp.wc.model.StockRecord;
import net.kingborn.erp.wc.model.TransferProduct;
import net.kingborn.erp.wc.service.StockRecordService;
import net.kingborn.erp.wc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl extends ServiceImpl<StockDao, Stock> implements StockService {

    @Autowired
    private StockRecordService recordService;

    @Override
    public Stock findByProductAndWarehouse(String productId, String warehouseId) {
        return getOne(new QueryWrapper<Stock>().eq("productId", productId).eq("warehouseId", warehouseId));
    }

    @Override
    public Stock handleStock(IssueProduct issueProduct, String stockType) {
        Stock stock = findByProductAndWarehouse(issueProduct.getProductId(), issueProduct.getWarehouseId());
        if (stock == null) {
            stock = new Stock();
            stock.setProductId(issueProduct.getProductId());
            stock.setWarehouseId(issueProduct.getWarehouseId());
        }
        stock.setQuantity(Define.STOCK_TYPE_IN.equals(stockType) ?
                stock.getQuantity() + issueProduct.getQuantity() : stock.getQuantity() - issueProduct.getQuantity());
        stock.setPrice(issueProduct.getPrice());
        stock.setAmount(Define.STOCK_TYPE_IN.equals(stockType) ?
                stock.getAmount() + issueProduct.getAmount() : stock.getAmount() - issueProduct.getAmount());

        saveOrUpdate(stock);

        // 处理出入库记录
        StockRecord record = new StockRecord();
        record.setIssueDate(issueProduct.getIssueDate());
        record.setBusinessType(issueProduct.getBusinessType());
        record.setBusinessId(issueProduct.getBusinessId());
        record.setProductId(issueProduct.getProductId());
        record.setWarehouseId(issueProduct.getWarehouseId());
        record.setQuantity(issueProduct.getQuantity());
        record.setStockType(stockType);
        record.setCurrentQuantity(stock.getQuantity());
        recordService.save(record);

        return stock;
    }

    @Override
    public void handTransferStock(TransferProduct transferProduct) {
        Stock fromStock = findByProductAndWarehouse(transferProduct.getProductId(), transferProduct.getFromWarehouseId());
        if (fromStock == null) {
            fromStock = new Stock();
            fromStock.setProductId(transferProduct.getProductId());
            fromStock.setWarehouseId(transferProduct.getFromWarehouseId());
            fromStock.setPrice(0.0);
        }
        Stock toStock = findByProductAndWarehouse(transferProduct.getProductId(), transferProduct.getToWarehouseId());
        if (toStock == null) {
            toStock = new Stock();
            toStock.setProductId(transferProduct.getProductId());
            toStock.setWarehouseId(transferProduct.getToWarehouseId());
            toStock.setPrice(0.0);
        }
        // 成本
        Double productAmount = fromStock.getPrice() * transferProduct.getQuantity();

        fromStock.setQuantity(fromStock.getQuantity() - transferProduct.getQuantity());
        fromStock.setAmount(fromStock.getAmount() - productAmount);
        saveOrUpdate(fromStock);

        toStock.setQuantity(toStock.getQuantity() + transferProduct.getQuantity());
        toStock.setAmount(toStock.getAmount() + productAmount);
        saveOrUpdate(toStock);

        // 处理出入库记录
        StockRecord fromRecord = new StockRecord();
        fromRecord.setIssueDate(transferProduct.getIssueDate());
        fromRecord.setBusinessType(Define.BUSINESS_TYPE_TRANSFER_OUT);
        fromRecord.setBusinessId(transferProduct.getTransferId());
        fromRecord.setProductId(transferProduct.getProductId());
        fromRecord.setWarehouseId(transferProduct.getFromWarehouseId());
        fromRecord.setQuantity(transferProduct.getQuantity());
        fromRecord.setStockType(Define.STOCK_TYPE_OUT);
        fromRecord.setCurrentQuantity(fromStock.getQuantity());
        recordService.save(fromRecord);

        StockRecord toRecord = new StockRecord();
        toRecord.setIssueDate(transferProduct.getIssueDate());
        toRecord.setBusinessType(Define.BUSINESS_TYPE_TRANSFER_IN);
        toRecord.setBusinessId(transferProduct.getTransferId());
        toRecord.setProductId(transferProduct.getProductId());
        toRecord.setWarehouseId(transferProduct.getToWarehouseId());
        toRecord.setQuantity(transferProduct.getQuantity());
        toRecord.setStockType(Define.STOCK_TYPE_IN);
        toRecord.setCurrentQuantity(toRecord.getQuantity());
        recordService.save(toRecord);
    }

}
