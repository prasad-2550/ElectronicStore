package com.BikkadIT.ElectronicStroe.controller;

import com.BikkadIT.ElectronicStroe.config.AppConstant;
import com.BikkadIT.ElectronicStroe.dtos.ApiResponseMessage;
import com.BikkadIT.ElectronicStroe.dtos.ImageResponse;
import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.dtos.UserDto;
import com.BikkadIT.ElectronicStroe.services.FileService;
import com.BikkadIT.ElectronicStroe.services.UserService;
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
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //CREATE

    /**
     * @author prasad pawawr
     * @apiNote createUser
     * @param userDto
     * @return user
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Initiating request to create user");
        UserDto user = userService.createUser(userDto);
        logger.info("Completed request to create user");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //update

    /**
     * @author prasad pawar
     * @apiNote updateUser
     * @param userId
     * @param userDto
     * @return updatedDto
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,@Valid @RequestBody UserDto userDto) {

        logger.info("Initiating request to update userID");
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        logger.info("Completed request of update user");
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }


    //delete

    /**
     * @author prasad pawar
     * @apiNote deleteUser
     * @param userId
     * @return delete user
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId){
        logger.info("Initiating  request to delete userId");
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is Deleted Successfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed request of delete userId");
        return new ResponseEntity( message, HttpStatus.OK);
    }

    // get All

    /**
     * @author prasad pawar
     * @apiNote getAllUser
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return getAllUser
     */
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ){
        logger.info("Initiating  request to  getAllUsers");
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);

    }

    //get single

    /**
     * @author prasad pawar
     * @apiNote getUser
     * @param userId
     * @return single category
     */
    @GetMapping("user/{userId}")
    public ResponseEntity<UserDto> getUsers(@PathVariable String userId){
        logger.info("Initiating request to getUsers");
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    // get By email

    /**
     * @author prasad pawar
     * @apiNote getUserByEmail
     * @param email
     * @return userByEmail
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Initiating request to get User by email");
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("Completed request of get User by email");
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }


    //search user

    /**
     * @author prasad pawar
     * @apiNote getUserByKeyword
     * @param keywords
     * @return useer
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> getUserByKeyword (@PathVariable String keywords) {
        logger.info("Initiating request to search by keyword");
        List<UserDto> userDtoByKeyword = userService.searchUser(keywords);
        logger.info("Completed request of search by keyword");

        return new ResponseEntity<>(userDtoByKeyword, HttpStatus.OK);
    }

    /**
     * @author prasad pawar
     * @apiNote uploadUserImage
     * @param image
     * @param userId
     * @return imageResponce
     * @throws IOException
     */
        //upload user image
        @PostMapping("/image/{userId}")
        public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image, @PathVariable String userId) throws IOException {
            logger.info("Initiating request to upload UserImage");
            String imageName = fileService.uploadFile(image, imageUploadPath);
            logger.info("Initiating request to get userId");
            UserDto user = userService.getUserById(userId);
            logger.info("Initiating request to set userId");
            user.setImageName(imageName);
            UserDto userDto = userService.updateUser(user, userId);
            ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image upload Successfully").success(true).status(HttpStatus.CREATED).build();
            logger.info("Completed request to uploadImage");
            return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

        }
    //serve user image


    /**
     * @author prasad pawar
     * @apiNote serveUserImage
     * @param userId
     * @param response
     * @throws IOException
     */
        @GetMapping("/image/{userId}")
        public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);
            logger.info("user image name : "+user.getImageName());
            InputStream resource = fileService.getResource(imageUploadPath,user.getImageName());

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());
        }

    }