package com.crud_service.controllers;

import com.crud_service.controllers.dto.CategoryDto;
import com.crud_service.controllers.response.Response;
import com.crud_service.helper.CategoryHelper;
import com.crud_service.services.CategoryService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Api(tags = {"Category EndPoint"})
@CrossOrigin(origins = "*")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryHelper categoryHelper;

    @GetMapping
    @ApiOperation(value = "Retorna a lista de categorias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de categorias"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Filtro pelo nome", required = false, dataType = "string", paramType = "query", example = "Nome da categoria"),
    })
    public ResponseEntity<Response<List<CategoryDto>>> getCategories(
            @RequestParam(defaultValue = "") String name) {

        log.info("Retorna a lista de categorias!");
        Response<List<CategoryDto>> response = new Response<List<CategoryDto>>();
        List<CategoryDto> categories = categoryHelper.toModel(categoryService.getCategoriesFiltered(name));

        response.setData(categories);
        response.setMessage("Retornado a lista de categorias!");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna a categoria pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a categoria pelo id"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Response<CategoryDto>> getCategory(
            @ApiParam(value = "Id da categoria", example = "1", required = true)
            @PathVariable Long id) {

        if(!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Response<CategoryDto> response = new Response<CategoryDto>();

        log.info("Retorna a categoria pelo id: " + id);
        CategoryDto dto =  categoryHelper.toModel(categoryService.getCategory(id));

        response.setData(dto);
        response.setMessage("Retornado a categoria com id: " + id);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    @ApiOperation(value = "Cria uma nova categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Criado com sucesso a nova categoria"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Response<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                                BindingResult result) {
        log.info("Cria uma nova categoria!");
        Response<CategoryDto> response = new Response<CategoryDto>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        CategoryDto category = categoryHelper.toModel(categoryService.createCategory(categoryHelper.toEntity(categoryDto)));
        URI uri = getUri(category.getId());

        response.setData(category);
        response.setMessage("Criado com sucesso a nova categoria!");
        return ResponseEntity.created(uri).body(response);
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza uma categoria pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualizado com sucesso a nova categoria"),
            @ApiResponse(code = 404, message = "Categora não encontrada"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Response<CategoryDto>> updateCategory(
            @ApiParam(value = "Id para atualizar determinada categoria", required = true, example = "1")
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryDto categoryDto, BindingResult result) {

        if(!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Response<CategoryDto> response = new Response<CategoryDto>();

        log.info("Atualizando a categoria: " + categoryDto + " pelo ID: " + id);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            response.setMessage("Foram encontrados erros durante ao atualizar a categoria!");
            response.setData(categoryDto);

            return ResponseEntity.badRequest().body(response);
        }

        CategoryDto category = categoryHelper.toModel(categoryService.updateCategory(id, categoryHelper.toEntity(categoryDto)));
        response.setData(category);
        response.setMessage("Categora atualizada com sucesso!!");

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta uma categoria pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Deletado com sucesso a nova categoria"),
            @ApiResponse(code = 404, message = "Categora não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Response<CategoryDto>> deleteCategory(
            @ApiParam(value = "Id para deletar determinada categoria", required = true, example = "1")
            @PathVariable("id") Long id) {

        if(!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Response<CategoryDto> response = new Response<CategoryDto>();

        log.info("Deletando a categoria pelo ID: " + id);
        categoryService.deleteCategory(id);

        response.setMessage("Categoria deletada com sucesso!!");
        return ResponseEntity.ok(response);
    }


    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
