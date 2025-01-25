package com.example.webapp.utils;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.dto.ProductDto;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@UtilityClass
public class EditProductUtils {

    public void editProduct(ProductDto product, ProductDto existingProduct, List<MultipartFile> files) throws IOException {

        existingProduct.setAmount(product.getAmount());
        existingProduct.setCoast(product.getCoast());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());

        if (files == null) {
            existingProduct.getImageList().clear();
        } else {
            List<ImageDto> existingImages = existingProduct.getImageList();
            if (files.size() < existingProduct.getImageList().size()) {
                existingImages.removeIf(image -> existingImages.indexOf(image) >= files.size());
            }
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (!file.isEmpty()) {
                    byte[] fileBytes = file.getBytes();
                    if (i < existingImages.size()) {
                        ImageDto existingImage = existingImages.get(i);
                        existingImage.setContentType(file.getContentType());
                        existingImage.setBytes(fileBytes);
                    } else {
                        ImageDto newImage = ImageDto.builder()
                                .contentType(file.getContentType())
                                .bytes(fileBytes)
                                .build();
                        existingProduct.addImageToList(newImage);
                    }
                }
            }
        }

    }

}
