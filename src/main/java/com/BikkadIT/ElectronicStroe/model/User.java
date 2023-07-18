package com.BikkadIT.ElectronicStroe.model;

import lombok.*;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name = "user")
@Data
@Builder
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @Column(name = "user_Id")
   // @GeneratorType(ClassReloadingStrategy.Strategy=)
    private String  userId;

    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email",unique = true)
    private String email;
    @Column(name = "password",length = 10)
    private String password;
    @Column(name = "gender")
    private String gender;

    @Column(name = "about",length = 1000)
    private String about;

    @Column(name = "image_name")
    private String imageName;




}
