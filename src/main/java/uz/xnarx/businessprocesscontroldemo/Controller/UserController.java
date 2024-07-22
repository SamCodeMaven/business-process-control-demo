package uz.xnarx.businessprocesscontroldemo.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.xnarx.businessprocesscontroldemo.payload.ApiResponse;
import uz.xnarx.businessprocesscontroldemo.payload.AuthenticationRequest;
import uz.xnarx.businessprocesscontroldemo.payload.AuthenticationResponse;
import uz.xnarx.businessprocesscontroldemo.payload.UserDto;
import uz.xnarx.businessprocesscontroldemo.service.UserService;
import uz.xnarx.businessprocesscontroldemo.utils.ApplicationConstants;


import java.io.IOException;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }

    @GetMapping("/getAll")
    public HttpEntity<?> getAllUser(@RequestParam(value = "page",
            defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size",
                                            defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(userService.getAllUser(page, size));
    }

    @GetMapping("/getById/{id}")
    HttpEntity<?> getUserById(@PathVariable(value = "id") Long id) {
        ApiResponse apiResponse=userService.getByUserId(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PutMapping("/enable/{userId}")
    public ResponseEntity<UserDto> enableUser(@PathVariable Long userId) {
        UserDto enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(enabledUser);
    }

    @PutMapping("/disable/{userId}")
    public ResponseEntity<UserDto> disableUser(@PathVariable Long userId) {
        UserDto disabledUser = userService.disableUser(userId);
        return ResponseEntity.ok(disabledUser);
    }
}
