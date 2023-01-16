package com.shadoumaimall.boot.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import cn.hutool.core.annotation.Alias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shadoumaimall.boot.common.LDTConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * <p>
 *
 * </p>
 *
 * @author 啊啊啊啊不吵吵
 * @since 2023-01-16
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // id
    @ApiModelProperty("id")
    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 账号
    @ApiModelProperty("账号")
    @Alias("账号")
    private String account;
    // 昵称
    @ApiModelProperty("昵称")
    @Alias("昵称")
    private String name;
    // 密码
    @ApiModelProperty("密码")
    @Alias("密码")
    private String password;

    // 手机号
    @ApiModelProperty("手机号")
    @Alias("手机号")
    private String phone;

    // 邮箱
    @ApiModelProperty("邮箱")
    @Alias("邮箱")
    private String email;

    // 创建时间
    @ApiModelProperty("创建时间")
    @Alias("创建时间")
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss", timezone = "GMT+8")
            /*@TableField(fill = FieldFill.INSERT_UPDATE)
            @JsonDeserialize(using = LDTConfig.CmzLdtDeSerializer.class)
            @JsonSerialize(using = LDTConfig.CmzLdtSerializer.class)*/
    private LocalDateTime createtime;

    // 更新时间
    @ApiModelProperty("更新时间")
    @Alias("更新时间")
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss", timezone = "GMT+8")
            /*@TableField(fill = FieldFill.INSERT_UPDATE)
            @JsonDeserialize(using = LDTConfig.CmzLdtDeSerializer.class)
            @JsonSerialize(using = LDTConfig.CmzLdtSerializer.class)*/
    private LocalDateTime updatetime;

    // 用户唯一id
    @ApiModelProperty("用户唯一id")
    @Alias("用户唯一id")
    private String uid;
}