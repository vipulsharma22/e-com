package com.nitsoft.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.nitsoft.ecommerce.database.model.UserChoices;
import com.nitsoft.ecommerce.database.model.CartWishListData;
import com.nitsoft.ecommerce.repository.CartRepository;
import com.nitsoft.ecommerce.repository.WishListRepository;
import com.nitsoft.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartAndWishListService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WishListRepository wishListRepository;

    public List<CartWishListData> getCartById(Long userId){
        return getProducts(cartRepository.findByUserIdAndType(userId, Constant.TYPE.CART.name()));
    }

    public UserChoices saveProductInCart(Long userId,Long productId,Integer quantity){
        UserChoices cart = cartRepository.findByUserIdAndType(userId,Constant.TYPE.CART.name());
        if(cart == null){
            cart = new UserChoices();
            cart.setUserId(userId);
            cart.setType(Constant.TYPE.CART);
        }
        return getUpdatedCart(cart,productId,quantity);
    }

    public List<CartWishListData> getWishListById(Long userId){
        return getProducts(cartRepository.findByUserIdAndType(userId,Constant.TYPE.WISHLIST.name()));
    }

    public UserChoices saveProductInWishList(Long userId, Long productId){
        UserChoices wishList = cartRepository.findByUserIdAndType(userId,Constant.TYPE.WISHLIST.name());
        if(wishList == null){
            wishList = new UserChoices();
            wishList.setUserId(userId);
            wishList.setType(Constant.TYPE.WISHLIST);
        }
        return getUpdatedCart(wishList,productId,null);
    }

    private UserChoices getUpdatedCart(UserChoices userChoices,Long productId,Integer quantity){
        List<CartWishListData> list = Lists.newArrayList();
        try{
            list = Lists.newArrayList(objectMapper.readValue(userChoices.getData(), CartWishListData[].class));
        }catch (Exception ex){
            log.error("Exception while parsing",ex);
        }
        boolean found = false;
        for(CartWishListData cartWishListData : list){
            if(cartWishListData.getProductId().equals(productId)){
                cartWishListData.setQuantity(quantity);
                found = true;
            }
        }
        if(!found){
            CartWishListData cartWishListData = CartWishListData.builder().productId(productId).build();
            if(userChoices.getType().equals(Constant.TYPE.CART)){
                cartWishListData.setQuantity(1);
            }
            list.add(cartWishListData);
        }
        try{
            userChoices.setData(objectMapper.writeValueAsString(list));
        }catch (Exception ex){
            log.error("Exception while parsing",ex);
        }
        return userChoices;
    }

    private List<CartWishListData> getProducts(UserChoices userChoices){
        List<CartWishListData> list = Lists.newArrayList();
        try{
            list = Lists.newArrayList(objectMapper.readValue(userChoices.getData(), CartWishListData[].class));
        }catch (Exception ex){
            log.error("Exception while parsing",ex);
        }
        for(CartWishListData cartWishListData : list){
            if(cartWishListData.getQuantity() != null){
                cartWishListData.setQuantity(1);
            }
            cartWishListData.getProductDetails().setListPrice(cartWishListData.getQuantity() * cartWishListData.getProductDetails().getListPrice());
            cartWishListData.getProductDetails().setSalePrice(cartWishListData.getQuantity() * cartWishListData.getProductDetails().getSalePrice());
        }
        return list;
    }
}
