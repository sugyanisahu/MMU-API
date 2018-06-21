package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;

@Repository
public interface PrescriptionDetailRepo extends CrudRepository<PrescriptionDetail, Long> {

	@Query(" SELECT prescriptionID, beneficiaryRegID, benVisitID, providerServiceMapID, diagnosisProvided, "
			+ " instruction, externalInvestigation, visitCode "
			+ "from PrescriptionDetail ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
	public ArrayList<Object[]> getBenPrescription(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT prescriptionID, beneficiaryRegID, benVisitID, providerServiceMapID, diagnosisProvided, instruction, "
			+ " visitCode "
			+ " from PrescriptionDetail ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
	public ArrayList<Object[]> getGeneralOPDDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from PrescriptionDetail where prescriptionID = :prescriptionID AND "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getGeneralOPDDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update PrescriptionDetail set diagnosisProvided=:diagnosisProvided, instruction=:instruction, "
			+ "modifiedBy=:modifiedBy, processed=:processed "
			+ "where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID AND prescriptionID=:prescriptionID")
	public int updateGeneralOPDDiagnosis(@Param("diagnosisProvided") String diagnosisProvided,
			@Param("instruction") String instruction, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("prescriptionID") Long prescriptionID);

}
