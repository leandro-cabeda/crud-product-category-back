package com.crud_service.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Classe que representa o dto da categoria")
public class CategoryDto {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(notes = "ID da categoria", name = "id", required = false, example = "1")
    private Long id;

    @JsonProperty("name")
    @ApiModelProperty(notes = "Nome da categoria", name = "name", required = true, example = "Lenovo")
    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @JsonProperty("created_at")
    @ApiModelProperty(notes = "Data de criação da categoria", name = "createdAt", required = false, example = "2022-01-01T00:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

    @JsonProperty("updated_at")
    @ApiModelProperty(notes = "Data de atualização da categoria", name = "updatedAt", required = false, example = "2022-01-01T00:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;
}
