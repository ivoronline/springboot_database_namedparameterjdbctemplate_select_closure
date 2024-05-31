package com.ivoronline.springboot_database_namedparameterjdbctemplate_select_closure.service;

import com.ivoronline.springboot_database_namedparameterjdbctemplate_select_closure.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MyService {

  //PROPERTIES
  @Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  //=========================================================================================================
  // SELECT RECORD
  //=========================================================================================================
  public PersonDTO selectRecord(Integer id) {

    //CREATE SQL & PARAMETERS
    String sql = "SELECT * FROM PERSON WHERE ID = :personId";
    SqlParameterSource parameters = new MapSqlParameterSource()
      .addValue("personId" , id);

    //EXECUTE QUERY
    return namedParameterJdbcTemplate.query(
      sql,
      parameters,
      resultSet -> {
        resultSet.next();                         //Manually call
        PersonDTO personDTO = new PersonDTO(      //@AllArgsConstructor
          resultSet.getInt   ("ID"  ),
          resultSet.getString("NAME"),
          guardAgainstNullNumbers("AGE", resultSet)
        );
        return personDTO;
      }
    );

  }

  //=========================================================================================================
  // SELECT RECORDS
  //=========================================================================================================
  public List<PersonDTO> selectRecords(Integer id) {

    //CREATE SQL & PARAMETERS
    String sql = "SELECT * FROM PERSON WHERE ID >= :personId";
    SqlParameterSource parameters = new MapSqlParameterSource()
      .addValue("personId" , id);

    //EXECUTE QUERY
    return namedParameterJdbcTemplate.query(
      sql,
      parameters,
      (resultSet, rowNum) -> {                    //resultSet.next() is automatically called
        PersonDTO personDTO = new PersonDTO(      //@AllArgsConstructor
          resultSet.getInt   ("ID"  ),
          resultSet.getString("NAME"),
          guardAgainstNullNumbers("AGE", resultSet)
        );
        return personDTO;
      }
    );

  }

  //=========================================================================================================
  // GUARD AGAINST NULL NUMBERS
  //=========================================================================================================
  public Integer guardAgainstNullNumbers(String columnName, ResultSet resultSet) throws SQLException {
    Integer value = resultSet.getInt(columnName); //It will be 0 for null
    return resultSet.wasNull() ? null : value;
  }

}



