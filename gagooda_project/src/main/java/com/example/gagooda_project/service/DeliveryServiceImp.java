package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import com.example.gagooda_project.mapper.DeliveryMapper;
import com.example.gagooda_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryServiceImp implements DeliveryService{
    private DeliveryMapper deliveryMapper;
    private OrderMapper orderMapper;
    private CommonCodeMapper commonCodeMapper;

    public DeliveryServiceImp(DeliveryMapper deliveryMapper, OrderMapper orderMapper, CommonCodeMapper commonCodeMapper) {
        this.deliveryMapper = deliveryMapper;
        this.orderMapper = orderMapper;
        this.commonCodeMapper = commonCodeMapper;
    }

    @Override
    public int insertOne(DeliveryDto delivery) {
        return deliveryMapper.insertOne(delivery);
    }

    @Override
    public int modifyOne(DeliveryDto delivery) {
        return deliveryMapper.updateOne(delivery);
    }

    @Override
    public DeliveryDto selectByOrderId(String orderId) {
        return deliveryMapper.findByOrderId(orderId);
    }

    @Override
    public List<DeliveryDto> showDeliveryList(Map<String, Object> searchFilter) {
        if(!searchFilter.get("dDet").equals("")) {
            String dDet = searchFilter.get("dDet").toString();
            List<String> dList = new ArrayList<>(Arrays.asList(dDet.split(",")));
            String dDetF = "'"+String.join("','",dList) + "'";
            searchFilter.put("dDet",dDetF);
        }
        if(searchFilter.get("searchDiv").equals("all")) {
            String allCol = "order_id OR user_name OR invoice";
            searchFilter.put("searchDiv",allCol);
        }
        if(!searchFilter.get("searchWord").equals("")) {
            String keyword = "%" + searchFilter.get("searchWord") + "%";
            searchFilter.put("searchWord",keyword);
        }
        if(searchFilter.get("startDate").equals(searchFilter.get("endDate"))) {
            String equalDate = searchFilter.get("startDate").toString() + "%";
            searchFilter.put("startDate", equalDate);
            searchFilter.put("endDate", equalDate);
        }
        return deliveryMapper.listAll(searchFilter);
    }

    @Override
    public int removeOne(String orderId) {
        return deliveryMapper.deleteOne(orderId);
    }

}
