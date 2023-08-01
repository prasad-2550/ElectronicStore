package com.BikkadIT.ElectronicStroe.services.impl;

import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.dtos.ProductDto;
import com.BikkadIT.ElectronicStroe.exception.ResourceNotFoundException;
import com.BikkadIT.ElectronicStroe.helper.Helper;
import com.BikkadIT.ElectronicStroe.model.Category;
import com.BikkadIT.ElectronicStroe.model.Product;
import com.BikkadIT.ElectronicStroe.repository.CategoryRepository;
import com.BikkadIT.ElectronicStroe.repository.ProductRepository;
import com.BikkadIT.ElectronicStroe.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

   private static final Logger logger = LoggerFactory.getLogger(ProductImpl.class);

    @Override
    public ProductDto create(ProductDto productDto) {
        logger.info("Inside createProduct()");
        Product product = modelMapper.map(productDto, Product.class);
        // for productId
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);
        logger.info("Generated unique Id: "+ productId);
        product.setAddedDate(new Date());
        Product saved = productRepository.save(product);
        logger.info("Product Created Successfully"+saved);
        return modelMapper.map(saved,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        logger.info("Inside UpdateProduct()"+productId);
        // get single entity
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id"));
       //fetch the product
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setProductImage(productDto.getProductImage());
        // save the entity
        Product updatedProduct = productRepository.save(product);
        logger.info("Updated Product is "+updatedProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }


    @Override
    public void delete(String productId) {
        logger.info("Inside DeleteProduct()"+productId);
        // get single entity
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id"));
        //delete
        productRepository.delete(product);
        logger.info("Product deleted successfully"+product);
    }

    @Override
    public ProductDto getSingle(String productId){
        logger.info("Inside getSingleProduct()"+productId);
        // get single entity
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id"));
        logger.info(" getSingleProduct with Id"+product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir ) {
        logger.info("Inside getAllProduct()");
        Sort sort =(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        logger.info("List of all product"+page);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Inside getAllProductLive()");
        Sort sort =(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        logger.info("List of AllLiveProduct"+page);
        return Helper.getPageableResponse(page, ProductDto.class);
    }



    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle , int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Inside searchByTitle()");
        Sort sort =(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        logger.info("searchedTitle is "+page);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        //fetch the Category From DB
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given id Not found"));

        Product product = modelMapper.map(productDto, Product.class);
        // for productId
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);
        logger.info("Generated unique Id: "+ productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product saved = productRepository.save(product);
        logger.info("Product Created Successfully"+saved);
        return modelMapper.map(saved,ProductDto.class);

    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        // fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product with given id not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given Id not found"));
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category with given Id not found"));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(category, pageable);
        logger.info("searchedTitle is " + page);
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}