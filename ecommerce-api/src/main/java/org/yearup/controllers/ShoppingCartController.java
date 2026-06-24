package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService)
    {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal)
    {
        User user = userService.getByUserName(principal.getName());
        return shoppingCartService.getByUserId(user.getId());
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<ShoppingCart> addProduct(
            @PathVariable int productId,
            Principal principal)
    {
        User user = userService.getByUserName(principal.getName());

        shoppingCartService.addToCart(user.getId(), productId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shoppingCartService.getByUserId(user.getId()));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ShoppingCart> updateProduct(
            @PathVariable int productId,
            @RequestBody ShoppingCartItem item,
            Principal principal)
    {
        User user = userService.getByUserName(principal.getName());

        shoppingCartService.updateQuantity(
                user.getId(),
                productId,
                item.getQuantity());

        return ResponseEntity.ok(
                shoppingCartService.getByUserId(user.getId()));
    }

    @DeleteMapping
    public ResponseEntity<ShoppingCart> clearCart(Principal principal)
    {
        User user = userService.getByUserName(principal.getName());

        shoppingCartService.clearCart(user.getId());

        return ResponseEntity.ok(
                shoppingCartService.getByUserId(user.getId()));
    }
}
