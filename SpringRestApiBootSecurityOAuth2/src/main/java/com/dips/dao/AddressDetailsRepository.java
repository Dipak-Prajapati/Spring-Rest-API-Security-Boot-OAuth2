package com.dips.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dips.model.AddressModel;

@Repository
@Transactional
public interface AddressDetailsRepository extends CrudRepository<AddressModel, String>{
	
	@Query(value="select address_id from address where user_id =:userId", nativeQuery=true)
	List<AddressModel> getAddressId(@Param("userId") Integer userId);

	@Query(value="delete AddressModel where id =:addressId", nativeQuery=true)
	public void delete(@Param("addressId") String addressId);
	
	

}
