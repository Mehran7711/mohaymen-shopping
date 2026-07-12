package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.*;
import com.mohaymen.store.repository.CartRepository;
import com.mohaymen.store.repository.ProductRepository;
import com.mohaymen.store.service.ICartService;
import com.mohaymen.store.web.ApplicationProperties;
import com.mohaymen.store.web.mapper.CartMapper;
import com.mohaymen.store.web.request.CartItemRequest;
import com.mohaymen.store.web.response.CartItemResponse;
import com.mohaymen.store.web.response.CartResponse;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@Slf4j
@AllArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private ProductRepository productRepository;
    private final ApplicationProperties properties;
    private final CartMapper cartMapper;

    @Override
    public CartResponse addProduct(CartItemRequest request) {

        Cart cart = getOrCreateCart(request.getCustomerId());

        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setUnitPrice(product.getPrice());
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }

        item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        recalculate(cart);
        cartRepository.save(cart);
        return map(cart);
    }

    @Override
    public CartResponse updateQuantity(Long productId, CartItemRequest request) {

        Cart cart = getCartEntity(request.getCustomerId());

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow();

        item.setQuantity(request.getQuantity());

        item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

        recalculate(cart);

        return map(cartRepository.save(cart));
    }

    @Override
    public void removeProduct(Long customerId, Long productId) {

        Cart cart = getCartEntity(customerId);

        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));

        recalculate(cart);

        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long customerId) {

        return map(getCartEntity(customerId));
    }

    private Cart getOrCreateCart(Long customerId) {

        return cartRepository.findByCustomerId(customerId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setCustomerId(customerId);
            cart.setTotalAmount(BigDecimal.ZERO);

            return cartRepository.save(cart);
        });
    }

    private Cart getCartEntity(Long customerId) {

        return cartRepository.findByCustomerId(customerId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    private void recalculate(Cart cart) {
        BigDecimal total = cart.getItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }

    private CartResponse map(Cart cart) {

        List<CartItemResponse> items = cart.getItems().stream()
                .map(i -> new CartItemResponse(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getTotalPrice()))
                .toList();

        return new CartResponse(cart.getId(), cart.getTotalAmount(), items);
    }

    //    @Transactional
    //    public Cart addProduct(Long customerId, Long productId, int quantity) {
    //
    //        Cart cart = getOrCreateCart(customerId);
    //
    //        Product product = productRepository.findById(productId)
    //                .orElseThrow();
    //
    //        CartItem item = cart.findItem(productId);
    //
    //        if (item == null) {
    //
    //            item = new CartItem();
    //
    //            item.setProduct(product);
    //            item.setQuantity(quantity);
    //            item.setUnitPrice(product.getPrice());
    //
    //            cart.addItem(item);
    //
    //        } else {
    //
    //            item.setQuantity(item.getQuantity() + quantity);
    //
    //        }
    //
    //        item.setTotalPrice(
    //                item.getUnitPrice()
    //                        .multiply(BigDecimal.valueOf(item.getQuantity()))
    //        );
    //
    //        recalculateCart(cart);
    //
    //        return cartRepository.save(cart);
    //    }
    //
    //    @Transactional
    //    public Cart saveCart(SaveCartRequest request) {
    //
    //        Cart cart = cartRepository.findByCustomerId(request.getCustomerId())
    //                .orElseGet(() -> {
    //                    Cart c = new Cart();
    //                    c.setCustomerId(request.getCustomerId());
    //                    c.setCreatedAt(LocalDateTime.now());
    //                    return c;
    //                });
    //
    //        cart.getItems().clear();
    //
    //        BigDecimal total = BigDecimal.ZERO;
    //
    //        for (CartItemRequest itemRequest : request.getItems()) {
    //
    //            Product product = productRepository.findById(itemRequest.getProductId())
    //                    .orElseThrow(() -> new RuntimeException("Product not found"));
    //
    //            CartItem item = new CartItem();
    //
    //            item.setCart(cart);
    //            item.setProduct(product);
    //            item.setQuantity(itemRequest.getQuantity());
    //            item.setUnitPrice(product.getPrice());
    //
    //            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
    //
    //            item.setTotalPrice(lineTotal);
    //
    //            cart.getItems().add(item);
    //
    //            total = total.add(lineTotal);
    //        }
    //
    //        cart.setTotalAmount(total);
    //        cart.setUpdatedAt(LocalDateTime.now());
    //
    //        return cartRepository.save(cart);
    //    }
    //
    //    @Transactional
    //    public Order checkout(Long customerId) {
    //
    //        Cart cart = cartRepository.findByCustomerId(customerId)
    //                .orElseThrow();
    //
    //        for (CartItem item : cart.getItems()) {
    //
    //            ProductInventory inventory =
    //                    inventoryRepository.findByProductId(item.getProduct().getId());
    //
    //            long available =
    //                    inventory.getQuantity() - inventory.getReservedQuantity();
    //
    //            if (available < item.getQuantity()) {
    //                throw new BusinessException("Not enough stock");
    //            }
    //
    //            inventory.setReservedQuantity(
    //                    inventory.getReservedQuantity() + item.getQuantity());
    //        }
    //
    //        Order order = createOrder(cart);
    //
    //        orderRepository.save(order);
    //
    //        cart.getItems().clear();
    //
    //        cart.setTotalAmount(BigDecimal.ZERO);
    //
    //        return order;
    //    }

}
