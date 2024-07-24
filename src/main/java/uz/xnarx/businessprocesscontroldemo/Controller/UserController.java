package uz.xnarx.businessprocesscontroldemo.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.xnarx.businessprocesscontroldemo.Configuration.JwtService;
import uz.xnarx.businessprocesscontroldemo.constants.ProjectEndpoints;
import uz.xnarx.businessprocesscontroldemo.payload.*;
import uz.xnarx.businessprocesscontroldemo.service.UserService;
import uz.xnarx.businessprocesscontroldemo.utils.ApplicationConstants;


import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;

    private final UserService userService;

    @Operation(summary = "Register user/ Only User with ADMIN Role can register new user",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @PostMapping(value = ProjectEndpoints.USER_REGISTER)
    public HttpEntity<?> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @Operation(summary = "Authentication with email and Password",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AuthenticationRequest.class)))))
    @PostMapping(value = ProjectEndpoints.USER_AUTH)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @Operation(summary = "refresh token",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @PostMapping(value = ProjectEndpoints.USER_TOKEN)
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }


    @Operation(summary = "Get all users",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @GetMapping(value = ProjectEndpoints.USERS)
    public HttpEntity<?> getAllUser(@RequestParam(value = "page",
            defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size",
                                            defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(userService.getAllUser(page, size));
    }

    @Operation(summary = "get one user by Id",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @GetMapping(value = ProjectEndpoints.USER_ID)
    HttpEntity<?> getUserById(@PathVariable(value = "id") Long id) {
        ApiResponse apiResponse=userService.getByUserId(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @Operation(summary = "get one user by jwt token in header",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @GetMapping(value = ProjectEndpoints.USER_INFO)
    public ResponseEntity<UserDto> getUserByJwt(){
        UserDto userDto=userService.getUserByToken();
        return ResponseEntity.ok(userDto);
    }


    @Operation(summary = "enable user/ it used when Admin restrict the user ",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @PutMapping(value = ProjectEndpoints.USER_ENABLE)
    public ResponseEntity<UserDto> enableUser(@PathVariable Long userId) {
        UserDto enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(enabledUser);
    }

    @Operation(summary = "enable user/ it used when Admin restrict the user ",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDto.class)))))
    @PutMapping(value = ProjectEndpoints.USER_DISABLE)
    public ResponseEntity<UserDto> disableUser(@PathVariable Long userId) {
        UserDto disabledUser = userService.disableUser(userId);
        return ResponseEntity.ok(disabledUser);
    }



    //todo profile method to get user's details by its token

}
