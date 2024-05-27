package com.final_project.Service;

import com.final_project.dto.CartDTO;
import com.final_project.mapper.CartMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapperInterface cmi;

    public List<CartDTO> Select(int userNo) {
        return cmi.Select(userNo);
    }

    public int Insert(CartDTO cart) {
        return cmi.Insert(cart);
    }

    public int Delete(CartDTO cart) {
        return cmi.Delete(cart);
    }
}
