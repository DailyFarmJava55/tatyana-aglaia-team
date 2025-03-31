package telran.dayli_farm.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.entity.CustomerCredential;
import telran.dayli_farm.farmer.dao.FarmerCredentialRepository;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;
import telran.dayli_farm.security.login.LoginRoleContext;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final CustomerCredentialRepository customerCredentialRepo;
	private final FarmerCredentialRepository farmerCredentialRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    String role = LoginRoleContext.getRole();

	    if ("CUSTOMER".equals(role)) {
	              
	        CustomerCredential credential = customerCredentialRepo.findByCustomerEmail(email) 
	        		.orElseThrow(() -> new UsernameNotFoundException("Customer not found: " + email));
	        Customer customer = credential.getCustomer();
	        return new CustomUserDetails(
	                customer.getEmail(),
	                credential.getHashedPassword(),
	                List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")),
	                customer.getId()
	        );
	    }

	    if ("FARMER".equals(role)) {
	        FarmerCredential credential = farmerCredentialRepo.findByFarmerEmail(email) 
	        		.orElseThrow(() -> new UsernameNotFoundException("Farmer not found: " + email));
	        Farmer farmer = credential.getFarmer();
	        return new CustomUserDetails(
	                farmer.getEmail(),
	                credential.getHashedPassword(),
	                List.of(new SimpleGrantedAuthority("ROLE_FARMER")),
	                farmer.getId()
	        );
	    }

	    throw new UsernameNotFoundException("Unknown login role or user not found: " + email);
	}
}
