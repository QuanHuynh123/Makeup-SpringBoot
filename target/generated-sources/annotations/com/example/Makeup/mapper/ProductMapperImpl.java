package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.entity.Product;
import com.example.Makeup.entity.SubCategory;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T21:04:50+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        int subCategoryId = 0;
        String describe = null;
        LocalDateTime createdAt = null;
        int currentQuantity = 0;
        String image = null;
        String nameProduct = null;
        double price = 0.0d;
        int rentalCount = 0;
        String size = null;
        boolean status = false;
        LocalDateTime updatedAt = null;
        UUID id = null;

        subCategoryId = productSubCategoryId( product );
        describe = product.getDescription();
        createdAt = product.getCreatedAt();
        currentQuantity = product.getCurrentQuantity();
        image = product.getImage();
        nameProduct = product.getNameProduct();
        price = product.getPrice();
        rentalCount = product.getRentalCount();
        size = product.getSize();
        status = product.isStatus();
        updatedAt = product.getUpdatedAt();
        id = product.getId();

        String firstImage = null;

        ProductDTO productDTO = new ProductDTO( id, nameProduct, describe, size, price, status, image, subCategoryId, currentQuantity, rentalCount, firstImage, createdAt, updatedAt );

        mapFirstImage( productDTO, product );

        return productDTO;
    }

    @Override
    public Product toProductEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setSubCategory( productDTOToSubCategory( productDTO ) );
        product.setDescription( productDTO.getDescribe() );
        product.setCreatedAt( productDTO.getCreatedAt() );
        product.setUpdatedAt( productDTO.getUpdatedAt() );
        product.setCurrentQuantity( productDTO.getCurrentQuantity() );
        product.setId( productDTO.getId() );
        product.setImage( productDTO.getImage() );
        product.setNameProduct( productDTO.getNameProduct() );
        product.setPrice( productDTO.getPrice() );
        product.setRentalCount( productDTO.getRentalCount() );
        product.setSize( productDTO.getSize() );
        product.setStatus( productDTO.isStatus() );

        return product;
    }

    private int productSubCategoryId(Product product) {
        if ( product == null ) {
            return 0;
        }
        SubCategory subCategory = product.getSubCategory();
        if ( subCategory == null ) {
            return 0;
        }
        int id = subCategory.getId();
        return id;
    }

    protected SubCategory productDTOToSubCategory(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        SubCategory subCategory = new SubCategory();

        subCategory.setId( productDTO.getSubCategoryId() );

        return subCategory;
    }
}
