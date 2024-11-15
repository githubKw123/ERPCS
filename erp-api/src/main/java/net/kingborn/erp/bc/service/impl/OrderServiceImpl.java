package net.kingborn.erp.bc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.bc.dao.OrderDao;
import net.kingborn.erp.bc.model.Order;
import net.kingborn.erp.bc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Page<Order> pageSearch(long current, long size, JSONObject query) {
        return orderDao.queryPage(new Page<>(current, size), query);
    }
}
