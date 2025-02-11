package com.iemr.mmu.repo.nurse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;

@Repository
@RestResource(exported = false)
public interface BenVisitDetailRepo extends CrudRepository<BeneficiaryVisitDetail, Long> {

	@Transactional
	@Modifying
	@Query("update BeneficiaryVisitDetail set visitReasonID=:visitReasonID, visitReason=:visitReason, visitCategoryID=:visitCategoryID, "
			+ " visitCategory=:visitCategory, pregnancyStatus=:pregnancyStatus, "
			+ "rCHID=:rCHID, healthFacilityType=:healthFacilityType, healthFacilityLocation=:healthFacilityLocation "
			+ ", modifiedBy=:modifiedBy where benVisitID=:benVisitID")
	public int updateBeneficiaryVisitDetail(@Param("visitReasonID") Short visitReasonID,
			@Param("visitReason") String visitReason, @Param("visitCategoryID") Integer visitCategoryID,
			@Param("visitCategory") String visitCategory, @Param("pregnancyStatus") String pregnancyStatus,
			@Param("rCHID") String rCHID, @Param("healthFacilityType") String healthFacilityType,
			@Param("healthFacilityLocation") String healthFacilityLocation,
			// @Param("reportFilePath") String reportFilePath,
			@Param("modifiedBy") String modifiedBy, @Param("benVisitID") Long benVisitID);

	@Query(" SELECT bvd from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitCode = :visitCode")
	public BeneficiaryVisitDetail getVisitDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT bvd from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID and DATE(CreatedDate)<curdate()")
	public List<BeneficiaryVisitDetail> getBeneficiaryVisitHistory(@Param("benRegID") Long benRegID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set visitFlowStatusFlag = :visitFlowStatusFlag, lastModDate = curdate() where benVisitID = :benVisitID ")
	public Integer updateBenFlowStatus(@Param("visitFlowStatusFlag") String visitFlowStatusFlag,
			@Param("benVisitID") Long benVisitID);

	@Query(" SELECT bvd.benVisitID, bvd.beneficiaryRegID, bvd.providerServiceMapID, bvd.visitDateTime, bvd.visitNo, bvd.visitReasonID, bvd.visitReason, "
			+ "bvd.visitCategoryID, bvd.visitCategory, bvd.pregnancyStatus, bvd.rCHID, bvd.healthFacilityType, bvd.healthFacilityLocation, "
			+ "bvd.reportFilePath,sp.serviceProviderName from BeneficiaryVisitDetail bvd "
			+ "INNER JOIN bvd.providerServiceMapping p " + "INNER JOIN p.serviceProvider sp "
			+ "WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitCode = :visitCode ")
	public List<Objects[]> getBeneficiaryVisitDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query(" SELECT COUNT(benVisitID) FROM BeneficiaryVisitDetail WHERE beneficiaryRegID = :benRegID GROUP BY beneficiaryRegID ")
	public Short getVisitCountForBeneficiary(@Param("benRegID") Long benRegID);

	@Query(nativeQuery = true, value = " SELECT v.benVisitID, v.visitCategory, v.visitCode FROM t_benvisitdetail v "
			+ " WHERE v.beneficiaryRegID = :benRegID "
			+ " AND v.visitCategory IS NOT NULL ORDER BY v.createdDate DESC limit 6 ")
	public ArrayList<Object[]> getLastSixVisitDetailsForBeneficiary(@Param("benRegID") Long benRegID);

	// updating record with visitcode.
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set visitCode = :visitCode where benVisitID = :benVisitID ")
	public Integer updateVisitCode(@Param("visitCode") Long visitCode, @Param("benVisitID") Long benVisitID);

	// get file uuid from file id
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set reportFilePath = concat(IFNULL(reportFilePath, ''),IFNULL(:fileIDs, '')) "
			+ " WHERE beneficiaryRegID = :regID AND visitCode = :visitCode ")
	public int updateFileID(@Param("fileIDs") String fileIDs, @Param("regID") Long regID,
			@Param("visitCode") Long visitCode);
	
	@Query("SELECT MAX(bvd.createdDate) from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitReason = :visitreason AND bvd.visitCategory = :visitcategory ")
    public String getMaxCreatedDate(@Param("benRegID") Long benRegID, @Param("visitreason") String visitreason,@Param("visitcategory") String visitcategory);

}
