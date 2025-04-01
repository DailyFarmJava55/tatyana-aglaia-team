package telran.dayli_farm.surprise_bag.controller;

import static telran.dayli_farm.api.ApiConstants.ADD_SURPRISE_BAG;
import static telran.dayli_farm.api.ApiConstants.DELETE_SURPRISE_BAG;
import static telran.dayli_farm.api.ApiConstants.GET_ALL_SURPRISE_BAGS;
import static telran.dayli_farm.api.ApiConstants.GET_ALL_SURPRISE_BAGS_FOR_FARMER;
import static telran.dayli_farm.api.ApiConstants.GET_SURPRISE_BAG_BY_ID;
import static telran.dayli_farm.api.ApiConstants.SURPRISE_BAG_DEC;
import static telran.dayli_farm.api.ApiConstants.SURPRISE_BAG_INC;
import static telran.dayli_farm.api.ApiConstants.UPDATE_SURPRISE_BAG;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.security.CustomUserDetails;
import telran.dayli_farm.surprise_bag.dto.QuantityUpdateRequest;
import telran.dayli_farm.surprise_bag.dto.SurprisebagDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagEditDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagResponseDto;
import telran.dayli_farm.surprise_bag.service.ISbService;

@RestController
@AllArgsConstructor
@Slf4j
public class SbController {

	private final ISbService sbService;

	@PostMapping(ADD_SURPRISE_BAG)
	@PreAuthorize("hasRole('FARMER')")
	public ResponseEntity<SurprisebagResponseDto> addSurpriseBag(@Valid @RequestBody SurprisebagDto surpriseBagDto,
			@AuthenticationPrincipal CustomUserDetails user) {
		UUID farmerId = user.getId();
		return sbService.addSurpriseBag(farmerId, surpriseBagDto);
	}
	
	

	@PutMapping(UPDATE_SURPRISE_BAG + "/{bagId}")
	@PreAuthorize("hasRole('ROLE_FARMER') AND @sbService.isFarmerOrAdmin(authentication.name, #bagId)")
	public ResponseEntity<SurprisebagResponseDto> updateSurpriseBag(
	        @PathVariable UUID bagId,
	        @Valid @RequestBody SurprisebagEditDto surpriseBagEditDto,
	        @AuthenticationPrincipal CustomUserDetails user) {
	    UUID farmerId = user.getId();
	    return sbService.updateSurpriseBag(farmerId, bagId, surpriseBagEditDto);
	}

	@GetMapping(GET_ALL_SURPRISE_BAGS_FOR_FARMER)
	@PreAuthorize("hasRole(ROLE_FARMER) ")
	public ResponseEntity<List<SurprisebagResponseDto>> getAvailableSurpriseBagForFarmer(
			@AuthenticationPrincipal CustomUserDetails user,
			@Parameter(description = "JWT токен", required = true) @RequestHeader("Authorization") String token) {
		return sbService.getAvailableSurpriseBagForFarmer(user.getId());
	}

	@GetMapping(GET_ALL_SURPRISE_BAGS)
	public ResponseEntity<List<SurprisebagResponseDto>> getAllSurpriseBags() {
		return sbService.getAllSurpriseBags();
	}

	@DeleteMapping(DELETE_SURPRISE_BAG + "/{bagId}")
	@PreAuthorize("hasRole('ROLE_FARMER') AND @sbService.isFarmerOrAdmin(authentication.name, #bagId)")
	public ResponseEntity<Void> deleteSurpriseBag(@PathVariable UUID bagId) {
	    sbService.deleteSurpriseBag(bagId);
	    return ResponseEntity.noContent().build();
	}
	
	//**************************************************
	@GetMapping(GET_SURPRISE_BAG_BY_ID)
	public ResponseEntity<SurprisebagResponseDto> getSurpriseBagById(@PathVariable UUID id) {
	    return ResponseEntity.ok(sbService.getById(id));
	}
	
	@PutMapping(SURPRISE_BAG_DEC)
	public ResponseEntity<Void> decrementAvailableCount(
	        @PathVariable UUID id,
	        @RequestBody @Valid QuantityUpdateRequest request) {
		sbService.decrementAvailableCount(id, request.quantity());
	    return ResponseEntity.noContent().build();
	}
	
	@PutMapping(SURPRISE_BAG_INC)
	public ResponseEntity<Void> incrementAvailableCount(@PathVariable UUID id,  @RequestBody @Valid QuantityUpdateRequest request) {
		sbService.incrementAvailableCount(id, request.quantity());
	    return ResponseEntity.noContent().build();
	}

}
