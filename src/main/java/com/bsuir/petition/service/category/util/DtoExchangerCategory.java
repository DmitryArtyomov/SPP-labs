package com.bsuir.petition.service.category.util;

import com.bsuir.petition.bean.dto.category.CategoryListDTO;
import com.bsuir.petition.bean.entity.Category;

import java.util.List;

public interface DtoExchangerCategory {
    CategoryListDTO getCategoryListDTO(List<Category> categories);
}
