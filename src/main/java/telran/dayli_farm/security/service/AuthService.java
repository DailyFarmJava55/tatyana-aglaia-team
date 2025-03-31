package telran.dayli_farm.security.service;

import static telran.dayli_farm.api.message.ErrorMessages.INVALID_TOKEN;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.api.dto.token.RefreshTokenResponseDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.entity.CustomerCredential;
import telran.dayli_farm.farmer.dao.FarmerCredentialRepository;
import telran.dayli_farm.farmer.dao.FarmerRepository;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;
import telran.dayli_farm.security.CustomUserDetails;
import telran.dayli_farm.security.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthService {
	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final CustomerRepository customerRepo;
    private final CustomerCredentialRepository customerCredentialRepo;

    private final FarmerRepository farmerRepo;
    private final FarmerCredentialRepository farmerCredentialRepo;

    @Transactional
    public TokenResponseDto authenticateCustomer(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UUID userId = userDetails.getId();
        LocalDateTime now = LocalDateTime.now();

        CustomerCredential credential = customerCredentialRepo.findByCustomerEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found: " + email));
        
      
        String refreshToken = jwtService.generateRefreshToken(userId, email);
        String accessToken = jwtService.generateAccessToken(userId, email, "CUSTOMER");

        credential.setRefreshToken(refreshToken);
        credential.setLastLogin(now);
        customerCredentialRepo.save(credential);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponseDto authenticateFarmer(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UUID userId = userDetails.getId();
        LocalDateTime now = LocalDateTime.now();

        FarmerCredential credential = farmerCredentialRepo.findByFarmerEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Farmer not found: " + email));
        
        String refreshToken = jwtService.generateRefreshToken(userId, email);
        String accessToken = jwtService.generateAccessToken(userId, email, "FARMER");

        credential.setRefreshToken(refreshToken);
        credential.setLastLogin(now);
        farmerCredentialRepo.save(credential);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    public ResponseEntity<RefreshTokenResponseDto> refreshCustomerAccessToken(String refreshToken) {
        log.info("AuthService.refreshCustomerAccessToken - Refreshing access token for Customer");

        UUID id = jwtService.extractUserId(refreshToken);
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with id: " + id));

        CustomerCredential credential = customerCredentialRepo.findByCustomer(customer);

        if (refreshToken.equals(credential.getRefreshToken()) && !jwtService.isTokenExpired(refreshToken)) {
            String newAccessToken = jwtService.generateAccessToken(id, customer.getEmail(), "CUSTOMER");
            return ResponseEntity.ok(new RefreshTokenResponseDto(newAccessToken));
        }

        throw new BadCredentialsException(INVALID_TOKEN);
    }

    public ResponseEntity<RefreshTokenResponseDto> refreshFarmerAccessToken(String refreshToken) {
        log.info("AuthService.refreshFarmerAccessToken - Refreshing access token for Farmer");

        UUID id = jwtService.extractUserId(refreshToken);
        Farmer farmer = farmerRepo.findByid(id)
                .orElseThrow(() -> new UsernameNotFoundException("Farmer not found with id: " + id));

        FarmerCredential credential = farmerCredentialRepo.findByFarmer(farmer);

        if (refreshToken.equals(credential.getRefreshToken()) && !jwtService.isTokenExpired(refreshToken)) {
            String newAccessToken = jwtService.generateAccessToken(id, farmer.getEmail(), "FARMER");
            return ResponseEntity.ok(new RefreshTokenResponseDto(newAccessToken));
        }

        throw new BadCredentialsException(INVALID_TOKEN);
    }
}