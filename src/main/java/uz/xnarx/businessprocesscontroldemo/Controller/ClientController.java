package uz.xnarx.businessprocesscontroldemo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.xnarx.businessprocesscontroldemo.constants.ProjectEndpoints;
import uz.xnarx.businessprocesscontroldemo.payload.ClientDto;
import uz.xnarx.businessprocesscontroldemo.service.ClientService;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;


    @Operation(summary = "save or edit client details",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ClientDto.class)))))
    @PostMapping(value = ProjectEndpoints.CLIENT_REGISTER)
    public ResponseEntity<ClientDto> saveOrUpdateClient(@Valid @RequestBody ClientDto clientDto) {
        clientService.saveOrUpdateClient(clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "get client details",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ClientDto.class)))))
    @GetMapping(value = ProjectEndpoints.CLIENTS)
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

//    @Operation(summary = "get client details",
//            responses = @ApiResponse(responseCode = "200",
//                    content = @Content(
//                            array = @ArraySchema(
//                                    schema = @Schema(implementation = ClientDto.class)))))
//    @GetMapping(value = ProjectEndpoints.CLIENTS_MANAGER)
//    public ResponseEntity<List<ClientDto>> getAllClients(@PathVariable(value = "manager_id") Long manager_id) {
//        List<ClientDto> clients = clientService.getAllClientsByManagerId(manager_id);
//        return ResponseEntity.ok(clients);
//    }

    @GetMapping(value = ProjectEndpoints.CLIENT_NAME)
    public ResponseEntity<List<ClientDto>> searchClientByName(@RequestHeader@RequestParam(value = "name") String name) {
        List<ClientDto> clients = clientService.searchClientByName(name);
        return ResponseEntity.ok(clients);
    }


}
