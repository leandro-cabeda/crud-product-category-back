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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Classe que representa o dto do produto")
public class ProductDto {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(notes = "ID do produto", name = "id", required = false, example = "1")
    private Long id;

    @JsonProperty("name")
    @ApiModelProperty(notes = "Nome do produto", name = "name", required = true, example = "Notebook")
    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(notes = "Descrição do produto", name = "description", required = false, example = "Notebook gamer")
    private String description;

    @JsonProperty("price")
    @ApiModelProperty(notes = "Preço do produto", name = "price", required = true, example = "1000.00")
    @NotNull(message = "Preço não pode ser nulo")
    @Min(value = 0, message = "O valor informado no preço não pode ser abaixo de {min}")
    private Double price;

    @JsonProperty("quantity")
    @ApiModelProperty(notes = "Quantidade do produto", name = "quantity", required = true, example = "10")
    @NotNull(message = "Quantidade não pode ser nulo")
    @Min(value = 0, message = "O valor informado na quantidade não pode ser abaixo de {min}")
    private Integer quantity;

    @JsonProperty("created_at")
    @ApiModelProperty(notes = "Data de criação do produto", name = "createdAt", required = false, example = "2022-01-01T00:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

    @JsonProperty("updated_at")
    @ApiModelProperty(notes = "Data de atualização do produto", name = "updatedAt", required = false, example = "2022-01-01T00:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;

    @JsonProperty("category")
    @ApiModelProperty(notes = "Categoria do produto", name = "category", required = true)
    @NotNull(message = "Categoria não pode ser nulo")
    private CategoryDto category;

}
