package com.ivoronline.springboot_database_namedparameterjdbctemplate_select_closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
  private Integer id;
  private String  name;
  private Integer age;
}
