package com.phaiecobyte.possystem.service.impl;

import com.phaiecobyte.possystem.model.Item;
import com.phaiecobyte.possystem.model.Order;
import com.phaiecobyte.possystem.model.OrderDetail;
import com.phaiecobyte.possystem.payload.req.OrderReq;
import com.phaiecobyte.possystem.repository.ItemRepository;
import com.phaiecobyte.possystem.repository.OrderRepository;
import com.phaiecobyte.possystem.service.BaseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements BaseService<Order, OrderReq> {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> getById(long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order create(OrderReq req) {
        Order order = new Order();
        order.setStatus(req.status());

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if (req.orderDetails() != null) {
            for (var d : req.orderDetails()) {
                Item item = itemRepository.findById(d.itemId())
                        .orElseThrow(() -> new EntityNotFoundException("Item not found: " + d.itemId()));

                if (item.getStock() < d.qty()) {
                    throw new IllegalStateException("Insufficient stock for item id: " + d.itemId());
                }

                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setItem(item);
                detail.setQuantity(d.qty());
                detail.setPrice(d.price());
                double sub = d.price() * d.qty();
                detail.setSubTotal(sub);
                details.add(detail);

                // update stock
                item.setStock(item.getStock() - d.qty());

                total = total.add(BigDecimal.valueOf(sub));
            }
        }

        order.setTotalAmount(total);
        order.setOrderDetail(details);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order update(Order req) {
        Order existing = orderRepository.findById(req.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        existing.setStatus(req.getStatus());
        // For simplicity, we do not update details here to keep change minimal
        return orderRepository.save(existing);
    }

    @Override
    public void remove(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public long count() {
        return orderRepository.count();
    }
}