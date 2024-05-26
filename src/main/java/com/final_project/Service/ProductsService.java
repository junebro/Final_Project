package com.final_project.Service;

import com.final_project.dto.ProductsDTO;
import com.final_project.mapper.ProductsMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsMapperInterface pmi;

    public List<ProductsDTO> SelectAll(String userNo, int protp) {
        return pmi.SelectAll(userNo, protp);
    }
}