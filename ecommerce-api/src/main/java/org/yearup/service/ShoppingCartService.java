package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
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

        // Get all cart rows for this user
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        // Build the shopping cart by looking up each product
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

    // Add a product to the cart
    public void addToCart(int userId, int productId)
    {
        CartItem existing =
                shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing == null)
        {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(1);

            shoppingCartRepository.save(item);
        }
        else
        {
            existing.setQuantity(existing.getQuantity() + 1);
            shoppingCartRepository.save(existing);
        }
    }

    // Update the quantity of a product already in the cart
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

    // Remove all items from the user's cart
    public void clearCart(int userId)
    {
        shoppingCartRepository.deleteByUserId(userId);
    }
}
