package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Cart;
import com.nitsoft.ecommerce.database.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartAndWishListService {
    @Autowired

    public Cart getCartById(Long userId){
        return new Cart();
    }

    public Cart saveProductInCart(Long userId,Long productId){
        return new Cart();
    }

    public WishList getWishListById(Long userId){
        return new WishList();
    }

    public WishList saveProductInWishList(Long userId, Long productId){
        return new WishList();
    }
}
