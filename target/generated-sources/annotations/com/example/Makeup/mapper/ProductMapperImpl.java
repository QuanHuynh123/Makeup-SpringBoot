package com.example.Makeup.mapper;

import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T16:42:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( product.getId() );
        productDTO.setNameProduct( product.getNameProduct() );
        productDTO.setSize( product.getSize() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setStatus( product.isStatus() );
        productDTO.setImage( product.getImage() );

        return productDTO;
    }

    @Override
    public Product toProductEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDTO.getId() );
        product.setNameProduct( productDTO.getNameProduct() );
        product.setSize( productDTO.getSize() );
        product.setPrice( productDTO.getPrice() );
        product.setStatus( productDTO.isStatus() );
        product.setImage( productDTO.getImage() );

        return product;
    }
}
