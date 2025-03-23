package telran.dayli_farm.farmer.controller;
import static telran.dayli_farm.api.ApiConstants.*;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.dayli_farm.api.dto.ChangePasswordRequestDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.RefreshTokenResponseDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.dto.CustomerDto;
import telran.dayli_farm.customer.dto.CustomerEditDto;
import telran.dayli_farm.customer.dto.CustomerRegisterDto;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.service.ICustomerService;
import telran.dayli_farm.farmer.dto.FarmerDto;
import telran.dayli_farm.farmer.dto.FarmerEditDto;
import telran.dayli_farm.farmer.dto.FarmerRegisterDto;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.service.IFarmerService;
import telran.dayli_farm.security.CustomUserDetailService;
import telran.dayli_farm.security.service.AuthService;

@Tag(name = "Farmer API", description = "Methods for farmer")
@RestController
@RequiredArgsConstructor
public class FarmerController {
	private final IFarmerService farmerService;
	private final AuthService authService;
	
	@PostMapping(FARMER_REGISTER)
	public ResponseEntity<FarmerDto> registerFarmer(@Valid @RequestBody FarmerRegisterDto farmerRegisterDto) {
		return farmerService.registerFarmer(farmerRegisterDto);
	}

	@PostMapping(FARMER_LOGIN)
	public ResponseEntity<TokenResponseDto> loginFarmer(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		return farmerService.loginFarmer(loginRequestDto);
	}
	
	@PostMapping(FARMER_REFRESH_TOKEN )
    public ResponseEntity<RefreshTokenResponseDto> refreshAccessToken(@RequestHeader("x-refresh-token") String refreshToken) {
        return authService.refreshFarmerAccessToken(refreshToken);
    }

	@GetMapping(FARMER_CURRENT)
	public ResponseEntity<Farmer> getCurrentFarmer(Principal principal) {
	return farmerService.getFarmerByEmail(principal.getName());
	}
	
	@PutMapping(FARMER_EDIT)
    @PreAuthorize("hasRole(ROLE_CUSTOMER)")
    public ResponseEntity<FarmerDto> updateFarmer(@Valid @RequestBody FarmerEditDto farmerEditDto,@AuthenticationPrincipal CustomUserDetailService user) {
        return farmerService.updateFarmer(user.getId(), farmerEditDto);
    }
	
	@PutMapping(FARMER_CHANGE_PASSWORD)
    @PreAuthorize("hasRole(ROLE_CUSTOMER)")
    public ResponseEntity<TokenResponseDto> farmerUpdatePassword(
            @Valid @RequestBody ChangePasswordRequestDto changePasswordDto,
            @AuthenticationPrincipal CustomUserDetailService user) {
        return farmerService.updatePassword(user.getId(), changePasswordDto);
    }

	@DeleteMapping(FARMER_REMOVE)
    @PreAuthorize("hasRole(ROLE_CUSTOMER)")
    public ResponseEntity<String> removeFarmer(@AuthenticationPrincipal CustomUserDetailService user) {
        return farmerService.removeFarmerById(user.getId());
    }
	
	@DeleteMapping(FARMER_LOGOUT)
    public ResponseEntity<String> logoutFarmer(@AuthenticationPrincipal CustomUserDetailService user, @RequestHeader("Authorization") String token) {
        return farmerService.logoutFarmer(user.getId(), token);
    }
}
