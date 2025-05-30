package telran.dayli_farm.customer.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.entity.CustomerCredential;

@Repository
public interface CustomerCredentialRepository extends JpaRepository<CustomerCredential, UUID> {

	CustomerCredential findByCustomer(Customer customer);
	
	Optional<CustomerCredential> findByCustomerEmail(@Param("email") String email);

	Optional<CustomerCredential> findByCustomerId(UUID id);
	


}
