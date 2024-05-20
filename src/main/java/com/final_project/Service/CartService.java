package com.final_project.Service;

import com.final_project.entity.Board;
import com.final_project.entity.Cart;
import com.final_project.entity.Products;
import com.final_project.mapper.CartMapperInterface;
import com.final_project.mapper.ProductsMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapperInterface cmi;

    public List<Cart> Select(int userNo) {
        return cmi.Select(userNo);
    }

    public int Insert(Cart cart) {
        return cmi.Insert(cart);
    }

    public int Delete(Cart cart) {
        return cmi.Delete(cart);
    }
}
