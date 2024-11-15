package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品保存
 */
@Command
public class CProductSave extends BaseCommand {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DictItemService itemService;
    @Autowired
    private WarehouseService warehouseService;

    @Param(required = true)
    private Product product;

    @Override
    protected void init() throws Exception {
        Assert.notBlank(product.getCode(), "商品编号不能为空！");
        Assert.notBlank(product.getName(), "商品名称不能为空！");
    }

    @Override
    protected void doCommand() throws Exception {
        // 检测分类
        checkCategory();

        // 检测编码是否重复
        checkDuplicatedCode();

        // 检测首选仓库
        checkPrimaryWarehouse();

        // 检测计量单位
        checkUnit();

        Product persistedProduct;
        if (StrKit.isBlank(product.getId())) {
            persistedProduct = new Product();
            persistedProduct.setActive(true);

        } else {
            persistedProduct = productService.getById(product.getId());
            Assert.notNull(persistedProduct, "ID为【" + product.getId() + "】的商品不存在！");
        }

        persistedProduct.setCode(product.getCode());
        persistedProduct.setName(product.getName());
        persistedProduct.setBarcode(product.getBarcode());
        persistedProduct.setSpec(product.getSpec());
        persistedProduct.setCategoryId(product.getCategoryId());
        persistedProduct.setPrimaryWarehouseId(product.getPrimaryWarehouseId());
        persistedProduct.setUnitId(product.getUnitId());
        persistedProduct.setRetailPrice(product.getRetailPrice());
        persistedProduct.setWholesalePrice(product.getWholesalePrice());
        persistedProduct.setVipPrice(product.getVipPrice());
        persistedProduct.setDiscountRate1(product.getDiscountRate1());
        persistedProduct.setDiscountRate2(product.getDiscountRate2());
        persistedProduct.setEstimatedPurchasePrice(product.getEstimatedPurchasePrice());
        persistedProduct.setRemark(product.getRemark());
        persistedProduct.setMinimumStock(product.getMinimumStock());
        persistedProduct.setMaximumStock(product.getMaximumStock());

        productService.saveOrUpdate(persistedProduct);

        data.put("product", persistedProduct);
    }

    /**
     * 检测计量单位
     */
    private void checkUnit() {
        if (StrKit.isBlank(product.getUnitId())) return;

        DictItem unit = itemService.getById(product.getUnitId());
        if (unit == null || !Define.DICT_CODE_UNIT.equals(unit.getDictCode())) {
            throw new BizException("计量单位错误！");
        }
    }

    /**
     * 检测首选仓库
     */
    private void checkPrimaryWarehouse() {
        if (StrKit.isBlank(product.getPrimaryWarehouseId())) return;

        Warehouse warehouse = warehouseService.getById(product.getPrimaryWarehouseId());
        Assert.notNull(warehouse, "ID为【" + product.getPrimaryWarehouseId() + "】的仓库不存在！");
    }

    /**
     * 检测编码是否重复
     */
    private void checkDuplicatedCode() {
        Product productByCode = productService.findByCode(product.getCode());
        if (productByCode == null) return;

        if (StrKit.notBlank(product.getId()) && product.getId().equals(productByCode.getId())) return;

        throw new BizException("编码为【" + product.getCode() + "】的商品已经存在！");
    }

    /**
     * 检测分类
     */
    private void checkCategory() {
        if (StrKit.isBlank(product.getCategoryId())) return;

        Category category = categoryService.getById(product.getCategoryId());
        if (category == null || category.getType() != Define.CATEGORY_TYPE_PRODUCT) {
            throw new BizException("商品类别不正确！");
        }
    }
}
