package com.BikkadIT.ElectronicStroe.dtos;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private  String userId;

    @Size(min=4,max=10,message = "!! Invalid Name !!")
    private String name;

//    @Pattern(regexp = "",message = "!!Invalid User Email ")
    @Email(message = "!! Invalid Email !!")
    private  String email;
    @NotBlank(message = "!! Password Required!!")
    private String password;
    @Size(min = 4,max = 6,message = "!! InvalidGender!!")
    private String gender;

    @NotBlank(message = "!!Write something about Yourself !!")
    private  String about;


    private String imageName;
}


