package com.final_project.Service;

import com.final_project.entity.Products;
import com.final_project.mapper.ProductsMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsMapperInterface pmi;

    public List<Products> SelectAll(String userNo) {
        return pmi.SelectAll(userNo);
    }
}
