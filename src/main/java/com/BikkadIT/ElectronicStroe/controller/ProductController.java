package com.BikkadIT.ElectronicStroe.controller;

import com.BikkadIT.ElectronicStroe.config.AppConstant;
import com.BikkadIT.ElectronicStroe.dtos.*;
import com.BikkadIT.ElectronicStroe.services.FileService;
import com.BikkadIT.ElectronicStroe.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    //create

    /**
     * @author prasad pawar
     * @apiNote createProduct
     * @param productDto
     * @return ProductDto1
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        logger.info("Initiating request to CreateProduct");
        ProductDto productDto1 = productService.create(productDto);
        logger.info("Completed Request Of CreateProduct");
        return new ResponseEntity<ProductDto>(productDto1, HttpStatus.CREATED);
    }

    //update

    /**
     * @author prasad pawar
     * @apiNote updateProduct
     * @param productId
     * @param productDto
     * @return updated
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct( @PathVariable String productId ,@RequestBody ProductDto productDto){
        logger.info("Initiating request to updateProduct"+productId);
        ProductDto updated = productService.update(productDto,productId);
        logger.info("Completed request Of updateProduct"+productId);
        return  new ResponseEntity<ProductDto>(updated,HttpStatus.OK);
    }

    //delete

    /**
     * @author prasad pawar
     * @apiNote deleteProduct
     * @param productId
     * @return null
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        logger.info("Initiating request to deleteProduct"+productId);
        productService.delete(productId);
        ApiResponseMessage deleted = ApiResponseMessage
                .builder()
                .message("Product Deleted Successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed Request of deleteProduct"+productId);
        return new ResponseEntity<ApiResponseMessage>(deleted,HttpStatus.OK);
    }

    //get single

    /**
     * @author prasad pawar
     * @apiNote getSingleProduct
     * @param productId
     * @return single
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        logger.info("Initiating request to getSingleProduct"+productId);
        ProductDto single = productService.getSingle(productId);
        logger.info("Completed Request Of getSingleProduct"+productId);
        return new ResponseEntity<ProductDto>(single,HttpStatus.OK);
    }

    //get all

    /**
     * @author prasad pawar
     * @apiNote getAllProduct
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return pageNumber, pageSize,sortBy,sortDir
     */
    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
        @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue =  AppConstant.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ){
            logger.info("Initiating  request to  getAllProduct");
            return new ResponseEntity<>(productService.getAll(pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);
        }



    //get all live

    /**
     * @author prasad pawar
     * @apiNote get All Live
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue =  AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ){
        logger.info("Initiating  request to  getAllLiveProduct");
        return new ResponseEntity<>(productService.getAllLive(pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);
    }


    /**
     * @author prasad pawar
     * @apiNote search
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    // search
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue =  AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ){
        logger.info("Initiating  request to  searchProduct");
        return new ResponseEntity<>(productService.searchByTitle(query,pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);
    }

    //uploadImage

    /**
     * @author prasad pawar
     * @apiNote uploadImage
     * @param productId
     * @param image
     * @return
     * @throws IOException
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam ("productImage")MultipartFile image

    )throws IOException {
        logger.info("Initiating  request to  uploadProductImage");

        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.getSingle(productId);
        productDto.setProductImage(fileName);
        ProductDto updated = productService.update(productDto, productId);

        ImageResponse response = ImageResponse.builder().imageName(updated.getProductImage())
                .message("product Image Uploaded!!")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();
        logger.info("Completed  request to  uploadProductImage");
        return new ResponseEntity<ImageResponse>(response, HttpStatus.CREATED);
    }
        //serve productImage

    /**
     * @author prasad pawar
     * @apiNote serveImage
     * @param productId
     * @param response
     * @throws IOException
     */
        @GetMapping("/image/{productId}")
        public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
            logger.info("Initiating  request to  uploadProductImage");
            ProductDto productDto = productService.getSingle(productId);
            logger.info("user image name : "+productDto.getProductImage());
            InputStream resource = fileService.getResource(imagePath,productDto.getProductImage());

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());
            logger.info("Completed request of serveImage");
        }

    }


