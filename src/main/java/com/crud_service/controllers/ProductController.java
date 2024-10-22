package com.crud_service.controllers;

import com.crud_service.controllers.dto.ProductDto;
import com.crud_service.controllers.response.Response;
import com.crud_service.helper.ProductHelper;
import com.crud_service.services.ProductService;
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
@RequestMapping("/products")
@Api(tags = {"Product EndPoint"})
@CrossOrigin(origins = "*")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductHelper productHelper;

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna o produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto pelo id"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Response<ProductDto>> getProduct(
            @ApiParam(value = "Id do produto", example = "1", required = true)
            @PathVariable Long id) {

        if(!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Response<ProductDto> response = new Response<ProductDto>();

        log.info("Retorna o produto pelo id: " + id);
        ProductDto dto =  productHelper.toModel(productService.getProduct(id));

        response.setData(dto);
        response.setMessage("Retornado o produto com id: " + id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ApiOperation(value = "Retorna a lista de produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Filtro pelo nome", required = false, dataType = "string", paramType = "query", example = "Nome do Produto"),
    })
    public ResponseEntity<Response<List<ProductDto>>> getProducts(
            @RequestParam(defaultValue = "") String name) {

        log.info("Retorna a lista de produtos!");

        Response<List<ProductDto>> response = new Response<List<ProductDto>>();
        List<ProductDto> productDtos = productHelper.toModel(productService.getProductsFiltered(name));

        response.setData(productDtos);
        response.setMessage("Retornou a lista de produtos!");

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    @ApiOperation(value = "Adiciona um novo Produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Criado com sucesso o novo Produto"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")})
    public ResponseEntity<Response<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto,
                                                            BindingResult result) {

        Response<ProductDto> response = new Response<ProductDto>();

        log.info("Criando novo Produto: " + productDto);
        if (result.hasErrors()) {

            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            response.setMessage("Foram encontrados erros durante ao adicionar o novo Produto!");
            response.setData(productDto);

            return ResponseEntity.badRequest().body(response);
        }

        ProductDto product = productHelper.toModel(productService.createProduct(productHelper.toEntity(productDto)));
        URI uri = getUri(product.getId());

        response.setData(product);
        response.setMessage("Pessoa criada com sucesso!!");

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um Produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualizado com sucesso o novo Produto"),
            @ApiResponse(code = 404, message = "Produto não encontrada"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")})
    public ResponseEntity<Response<ProductDto>> updateProduct(
            @ApiParam(value = "Id para atualizar determinado Produto", required = true, example = "1")
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductDto productDto, BindingResult result) {

        if(!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Response<ProductDto> response = new Response<ProductDto>();

        log.info("Atualizando o Produto: " + productDto + " pelo ID: " + id);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            response.setMessage("Foram encontrados erros durante ao atualizar o Produto!");
            response.setData(productDto);

            return ResponseEntity.badRequest().body(response);
        }

        ProductDto product = productHelper.toModel(productService.updateProduct(id, productHelper.toEntity(productDto)));

        response.setData(product);
        response.setMessage("Produto atualizado com sucesso!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um Produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Deletado com sucesso o novo Produto"),
            @ApiResponse(code = 404, message = "Produto não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")})
    public ResponseEntity<Response<String>> deleteProduct(
            @ApiParam(value = "Id para deletar determinado Produto", required = true, example = "1")
            @PathVariable("id") Long id) {

        if(!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Response<String> response = new Response<String>();

        log.info("Deletando o Produto pelo ID: " + id);
        productService.deleteProduct(id);

        response.setMessage("Produto deletado com sucesso!!");

        return ResponseEntity.ok(response);
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
