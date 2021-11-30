package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IContractRepository extends JpaRepository<ContractEntity, Integer> {
    @Query(value = "INSERT INTO contract(rented_date,expired_date,is_canceled,rented_area,company_id,floor_id) " +
            "VALUES(:#{#contract.rentedDate},:#{#contract.expiredDate}," +
            ":#{#contract.isCanceled},:#{#contract.rentedArea},:company_id,:floor_id) ", nativeQuery = true)
    public ContractEntity saveContractByCompanyAndFloor(@Param("contract") ContractEntity contractEntity,
                                                        @Param("company_id") int companyId,
                                                        @Param("floor_id") int floorId);

}