package telran.dayli_farm.farmer.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;

public interface FarmerCredentialRepository extends JpaRepository<FarmerCredential, UUID> {

	FarmerCredential findByFarmer(Farmer farmer);
	
	 Optional<FarmerCredential>  findByFarmerEmail(String email);

	 Optional<FarmerCredential> findByFarmerId(UUID id);

}
