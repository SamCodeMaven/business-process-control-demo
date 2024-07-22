package uz.xnarx.businessprocesscontroldemo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.xnarx.businessprocesscontroldemo.constants.ProjectEndpoints;
import uz.xnarx.businessprocesscontroldemo.payload.BillDto;
import uz.xnarx.businessprocesscontroldemo.service.BillService;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @Operation(summary = "save and edit Bill",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = BillDto.class)))))
    @PostMapping(value = ProjectEndpoints.BILLING)
    public ResponseEntity<BillDto> saveOrEditBill(@Valid @RequestBody BillDto billDto) {
        return ResponseEntity.ok(billService.saveOrModifyBill(billDto));
    }

    @Operation(summary = "get all bills",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = BillDto.class)))))
    @GetMapping(value = ProjectEndpoints.BILLINGS)
    public ResponseEntity<List<BillDto>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @Operation(summary = "get bills by workerId",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = BillDto.class)))))
    @GetMapping(value = ProjectEndpoints.BILL_WORKER)
    public ResponseEntity<List<BillDto>> getAllBillsByWorkerId(@Valid @PathVariable Long workerId) {
        return ResponseEntity.ok(billService.getBillsByWorkerId(workerId));
    }





}
