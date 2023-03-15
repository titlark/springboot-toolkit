package com.lark.validation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Job {
    @Min(value = 1, groups = UserDTO.Update.class)
    private Long jobId;

    @NotNull(groups = {UserDTO.Save.class, UserDTO.Update.class})
    @Length(min = 2, max = 10, groups = {UserDTO.Save.class, UserDTO.Update.class})
    private String jobName;

    @NotNull(groups = {UserDTO.Save.class, UserDTO.Update.class})
    @Length(min = 2, max = 10, groups = {UserDTO.Save.class, UserDTO.Update.class})
    private String position;
}
