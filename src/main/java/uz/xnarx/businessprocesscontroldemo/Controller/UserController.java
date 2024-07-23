package uz.xnarx.businessprocesscontroldemo.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.xnarx.businessprocesscontroldemo.constants.ProjectEndpoints;
import uz.xnarx.businessprocesscontroldemo.payload.ApiResponse;
import uz.xnarx.businessprocesscontroldemo.payload.AuthenticationRequest;
import uz.xnarx.businessprocesscontroldemo.payload.AuthenticationResponse;
import uz.xnarx.businessprocesscontroldemo.payload.UserDto;
import uz.xnarx.businessprocesscontroldemo.service.UserService;
import uz.xnarx.businessprocesscontroldemo.utils.ApplicationConstants;


import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = ProjectEndpoints.USER_REGISTER)
    public HttpEntity<?> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PostMapping(value = ProjectEndpoints.USER_AUTH)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping(value = ProjectEndpoints.USER_TOKEN)
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }

    @GetMapping(value = ProjectEndpoints.USERS)
    public HttpEntity<?> getAllUser(@RequestParam(value = "page",
            defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size",
                                            defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(userService.getAllUser(page, size));
    }

    @GetMapping(value = ProjectEndpoints.USER_ID)
    HttpEntity<?> getUserById(@PathVariable(value = "id") Long id) {
        ApiResponse apiResponse=userService.getByUserId(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PutMapping(value = ProjectEndpoints.USER_ENABLE)
    public ResponseEntity<UserDto> enableUser(@PathVariable Long userId) {
        UserDto enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(enabledUser);
    }

    @PutMapping(value = ProjectEndpoints.USER_DISABLE)
    public ResponseEntity<UserDto> disableUser(@PathVariable Long userId) {
        UserDto disabledUser = userService.disableUser(userId);
        return ResponseEntity.ok(disabledUser);
    }

    //todo profile method to get user's details by its token

}
