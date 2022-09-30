package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.exception.ResourceNotFoundException;
import com.localweb.storeapp.payload.ProductDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDTO create(ProductDTO productDTO){
        //convert DTO to entity
        Product product = maptoEntity(productDTO);
        Product newProduct = productRepository.save(product);

        //convert entity to DTO
        return mapToDTO(newProduct);
    }

    public Response<ProductDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);

        List<Product> productList = products.getContent();

        List<ProductDTO> content= productList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        Response<ProductDTO> productResponse = new Response<>();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    private ProductDTO mapToDTO(Product newProduct){
        /*ProductDTO productResponse = new ProductDTO();
        productResponse.setId(newProduct.getId());
        productResponse.setName(newProduct.getName());
        productResponse.setPrice(newProduct.getPrice());
        productResponse.setStock(newProduct.getStock());
        productResponse.setOrders(newProduct.getOrders());
        productResponse.setDateCreated(newProduct.getDateCreated());
        productResponse.setDateUpdated(newProduct.getDateUpdated());*/
        return modelMapper.map(newProduct, ProductDTO.class);
    }

    private Product maptoEntity(ProductDTO productDTO){
        /*Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDateCreated(productDTO.getDateCreated());
        product.setDateUpdated(productDTO.getDateUpdated());
        product.setOrders(productDTO.getOrders());*/
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO getById(int id) {
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", id));
        return mapToDTO(product);
    }

    public ProductDTO update(ProductDTO productDTO, int id) {
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", id));
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDateUpdated(LocalDate.now());

        Product updatedProduct = productRepository.save(product);

        return mapToDTO(updatedProduct);
    }

    public Product findProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
    }
}
