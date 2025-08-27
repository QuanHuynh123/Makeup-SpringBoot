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
    date = "2025-08-27T21:03:30+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
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
        String nameProduct = null;
        String size = null;
        double price = 0.0d;
        boolean status = false;
        String image = null;
        int currentQuantity = 0;
        int rentalCount = 0;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        subCategoryId = productSubCategoryId( product );
        describe = product.getDescription();
        nameProduct = product.getNameProduct();
        size = product.getSize();
        price = product.getPrice();
        status = product.isStatus();
        image = product.getImage();
        currentQuantity = product.getCurrentQuantity();
        rentalCount = product.getRentalCount();
        createdAt = product.getCreatedAt();
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
        product.setId( productDTO.getId() );
        product.setNameProduct( productDTO.getNameProduct() );
        product.setSize( productDTO.getSize() );
        product.setPrice( productDTO.getPrice() );
        product.setStatus( productDTO.isStatus() );
        product.setImage( productDTO.getImage() );
        product.setCurrentQuantity( productDTO.getCurrentQuantity() );
        product.setRentalCount( productDTO.getRentalCount() );

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
