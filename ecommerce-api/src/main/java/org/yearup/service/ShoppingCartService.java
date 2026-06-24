package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart cart = new ShoppingCart();

        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        for (CartItem cartItem : cartItems)
        {
            Product product = productService.getById(cartItem.getProductId());

            if (product != null)
            {
                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(cartItem.getQuantity());

                cart.add(item);
            }
        }

        return cart;
    }

    @Transactional
    public void addToCart(int userId, int productId)
    {
        CartItem existing =
                shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing == null)
        {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);

            shoppingCartRepository.save(cartItem);
        }
        else
        {
            existing.setQuantity(existing.getQuantity() + 1);
            shoppingCartRepository.save(existing);
        }
    }

    @Transactional
    public void updateQuantity(int userId, int productId, int quantity)
    {
        CartItem existing =
                shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing != null)
        {
            existing.setQuantity(quantity);
            shoppingCartRepository.save(existing);
        }
    }

    @Transactional
    public void clearCart(int userId)
    {
        shoppingCartRepository.deleteByUserId(userId);
    }
}
