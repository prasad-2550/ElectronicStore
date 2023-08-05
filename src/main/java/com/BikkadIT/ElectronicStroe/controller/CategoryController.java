package com.BikkadIT.ElectronicStroe.controller;
import com.BikkadIT.ElectronicStroe.config.AppConstant;
import com.BikkadIT.ElectronicStroe.dtos.ApiResponseMessage;
import com.BikkadIT.ElectronicStroe.dtos.CategoryDto;
import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.dtos.ProductDto;
import com.BikkadIT.ElectronicStroe.services.CategoryService;
import com.BikkadIT.ElectronicStroe.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //create

    /**
     * @author prasad pawar
     * @apiNote createCategory
     * @param categoryDto
     * @return categoryDto1
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        logger.info("Initiating request for create category");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Completed request to create Category");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

                               //update
    /**
     * @author prasad pawar
     * @apiNote updateCategory
     * @param categoryDto
     * @param categoryId
     * @return updatedCategory
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable String categoryId){
        logger.info("Initiating request to update CategoryId"+categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        logger.info("Completed request of update Category");
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

                                           //delete
    /**
     * @author prasad pawar
     * @apiNote deleteCategory
     * @param categoryId
     * @return null
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        logger.info("Initiating  request to delete CategoryId"+categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage categoryIsDeleted = ApiResponseMessage.builder().message("Category Is Deleted ").status(HttpStatus.OK).success(true).build();
        logger.info("Completed request of delete CategoryId");
        return  new ResponseEntity<>(categoryIsDeleted,HttpStatus.OK);

    }

    /**
     * @author prasad pawar
     * @apiNote getSingleCategory
     * @param categoryId
     * @return categoryDto1
     */
                            //get single category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId){
        logger.info("Initiating request to get single category"+categoryId);
        CategoryDto categoryDto1 = categoryService.get(categoryId);
        logger.info("completed request of get single category");
        return new ResponseEntity(categoryDto1,HttpStatus.OK);
    }

    /**
     * @author prasad pawar
     * @apiNote getAll
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param sortDir
     * @return pageableResponse
     */
                                   //get All category
    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
                @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int    pageSize,
                @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int    pageNumber,
                @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String    sortBy,
                @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String    sortDir
    ){
        logger.info("Initiating request to get all Category ");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed request of get all category");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
                     // create product with category
    /**
     *
     * @param categoryId
     * @param productDto
     * @return
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
    ){
        ProductDto withCategory = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(withCategory,HttpStatus.CREATED);
    }
                      // update Category of product
    /**
     * @author prasad pawar
     * @param categoryId
     * @param productId
     * @return updatedCategory
     */
       @PutMapping("/{categoryId}/products/{productId}")
       public ResponseEntity<ProductDto> updateCategory(
               @PathVariable String categoryId,
               @PathVariable String productId
       ){
           ProductDto productDto = productService.updateCategory(categoryId, productId);
           return new ResponseEntity<>(productDto,HttpStatus.CREATED);
       }

                    // get product of category
    /**
     *
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
        @GetMapping("/{categoryId}/products")
        public ResponseEntity<PageableResponse<ProductDto>> getAllOfCategory(
                @PathVariable String categoryId,
                @RequestParam ( value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int pageSize,
                @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
                @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir
        ){
            PageableResponse<ProductDto> allOfCategory = productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy,sortDir);
            return new ResponseEntity<>(allOfCategory,HttpStatus.OK);

        }

}
