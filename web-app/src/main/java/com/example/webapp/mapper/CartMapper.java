package com.example.webapp.mapper;

import com.example.webapp.dto.CartDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.model.Cart;

import com.example.webapp.model.Product;
import com.example.webapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "user",target = "userId")
    CartDto toDTO(Cart cart);

    @Mapping(source = "userId",target = "user")
    Cart toEntity(CartDto cartDto);

    default Product mapProductDtoToProduct(ProductDto productDto){
        return productMapper.toEntity(productDto);
    }

    default ProductDto mapProductToProductDto(Product product){
        return productMapper.toDTO(product);
    }

    default Long mapUserToCartDtoFromCart(User user){
        return user.getId();
    }

    default User mapUserToCartFromCartDto(Long value){
        return User.builder().id(value).build();
    }

}
