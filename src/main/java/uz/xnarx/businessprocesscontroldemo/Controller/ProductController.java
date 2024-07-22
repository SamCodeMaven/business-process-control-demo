package uz.xnarx.businessprocesscontroldemo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.xnarx.businessprocesscontroldemo.constants.ProjectEndpoints;
import uz.xnarx.businessprocesscontroldemo.payload.ProductDto;
import uz.xnarx.businessprocesscontroldemo.payload.SoldProductDto;
import uz.xnarx.businessprocesscontroldemo.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @Operation(summary = "save product", responses = @ApiResponse(responseCode = "200"))
    @PostMapping(value = ProjectEndpoints.PRODUCT_SAVE)
    private HttpEntity<?> saveProduct(@Valid @RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "get all products",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductDto.class)))))
    @GetMapping(value = ProjectEndpoints.PRODUCTS)
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
    @Operation(summary = "get one product by Id",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductDto.class)))))
    @GetMapping(value = ProjectEndpoints.PRODUCT_DETAILS)
    public ResponseEntity<ProductDto> getProduct(@Valid @PathVariable(value = "id", required = false) Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @Operation(summary = "restock product by Id",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductDto.class)))))
    @PostMapping(value = ProjectEndpoints.PRODUCT_RESTOCK)
    public ResponseEntity<ProductDto> restoreProduct(@Valid @PathVariable(value = "id", required = false) Long id, @Valid @RequestParam Double quantity) {
        productService.restockProduct(id, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "decrease product quantities",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SoldProductDto.class)))))
    @PostMapping(value = ProjectEndpoints.PRODUCT_SOLD)
    public ResponseEntity<?> soldProduct(@Valid @RequestBody List<SoldProductDto> productQuantities) {
        productService.decreaseProductQuantities(productQuantities);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
