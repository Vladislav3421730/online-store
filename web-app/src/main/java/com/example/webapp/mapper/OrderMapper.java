package com.example.webapp.mapper;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.model.Image;
import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {

    @Mapping(source = "userId",target = "user")
    @Mapping(source = "orderItems",target = "orderItems")
    Order toEntity(OrderDto orderDto);

    @Mapping(source = "user",target = "userId")
    @Mapping(source = "orderItems",target = "orderItems")
    OrderDto toDTO(Order Order);

    List<Image> mapImagesToOrderDtos(List<Long> value);
    Image  mapImageToOrderDto(Long value);

    default Long mapToOrderDtoFromOrder(User user){
        return user.getId();
    }

    default User mapToOrderFromOrderDto(Long userId){
        return User.builder().id(userId).build();
    }

    default String mapStatusToOrderDtoFromOrder(Status status){
        return status.getDisplayName();
    }

    default List<Long> mapImageListToIds(List<Image> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }
}
