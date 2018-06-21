package com.iemr.mmu.data.quickConsultation;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.anc.BenAdherence;

@Entity
@Table(name = "t_benchiefcomplaint")
public class BenChiefComplaint {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long benChiefComplaintID;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	
	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;

	@Expose
	@Column(name = "ChiefComplaintID")
	private Integer chiefComplaintID;
	@Expose
	@Column(name = "ChiefComplaint")
	private String chiefComplaint;
	@Expose
	@Column(name = "Duration")
	private Integer duration;
	@Expose
	@Column(name = "UnitOfDuration")
	private String unitOfDuration;
	@Expose
	@Column(name = "Description")
	private String description;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;
	
	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;
	
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	
	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;
	
	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;
	
	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;
	
	public BenChiefComplaint() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public BenChiefComplaint(Long benChiefComplaintID, Long beneficiaryRegID, Long benVisitID,
			Integer providerServiceMapID, Integer chiefComplaintID, String chiefComplaint, Integer duration,
			String unitOfDuration, String description, Long visitCode) {
		super();
		this.benChiefComplaintID = benChiefComplaintID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.visitCode = visitCode;
		this.providerServiceMapID = providerServiceMapID;
		this.chiefComplaintID = chiefComplaintID;
		this.chiefComplaint = chiefComplaint;
		this.duration = duration;
		this.unitOfDuration = unitOfDuration;
		this.description = description;
	}


	public static ArrayList<BenChiefComplaint> getBenChiefComplaints(ArrayList<Object[]> resList) {
		ArrayList<BenChiefComplaint> resArray = new ArrayList<BenChiefComplaint>();
		BenChiefComplaint cOBJ = null;
		for(Object[] obj:resList){		
			cOBJ = new BenChiefComplaint((Long)obj[0], (Long)obj[1], (Long)obj[2], (Integer)obj[3], (Integer)obj[4],
					(String)obj[5], (Integer)obj[6], (String)obj[7], (String)obj[8], (Long)obj[9]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getChiefComplaintID() {
		return chiefComplaintID;
	}

	public void setChiefComplaintID(Integer chiefComplaintID) {
		this.chiefComplaintID = chiefComplaintID;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getUnitOfDuration() {
		return unitOfDuration;
	}

	public void setUnitOfDuration(String unitOfDuration) {
		this.unitOfDuration = unitOfDuration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public Long getBenChiefComplaintID() {
		return benChiefComplaintID;
	}

	public Long getVanSerialNo() {
		return vanSerialNo;
	}


	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}


	public String getVehicalNo() {
		return vehicalNo;
	}


	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}


	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}


	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}


	public String getSyncedBy() {
		return syncedBy;
	}


	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}


	public Timestamp getSyncedDate() {
		return syncedDate;
	}


	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}


	public String getReservedForChange() {
		return reservedForChange;
	}


	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}


	public void setBenChiefComplaintID(Long benChiefComplaintID)
	{
		this.benChiefComplaintID = benChiefComplaintID;
	}


	public static ArrayList<BenChiefComplaint> getBenChiefComplaintList(JsonObject emrgCasesheet) {
		ArrayList<BenChiefComplaint> resArray = new ArrayList<>();
		BenChiefComplaint benChiefComplaint = null;
		//System.out.println("ello");
		if (emrgCasesheet.has("benChiefComplaint") && !emrgCasesheet.get("benChiefComplaint").isJsonNull()
				&& emrgCasesheet.get("benChiefComplaint").isJsonArray()) {
			for (JsonElement csobj : emrgCasesheet.getAsJsonArray("benChiefComplaint")) {
				benChiefComplaint = new BenChiefComplaint();

				if (emrgCasesheet.has("benVisitID") && !emrgCasesheet.get("benVisitID").isJsonNull())
					benChiefComplaint.setBenVisitID(emrgCasesheet.get("benVisitID").getAsLong());
				
				if (emrgCasesheet.has("visitCode") && !emrgCasesheet.get("visitCode").isJsonNull())
					benChiefComplaint.setVisitCode(emrgCasesheet.get("visitCode").getAsLong());

				if (emrgCasesheet.has("beneficiaryRegID") && !emrgCasesheet.get("beneficiaryRegID").isJsonNull())
					benChiefComplaint.setBeneficiaryRegID(emrgCasesheet.get("beneficiaryRegID").getAsLong());

				if (emrgCasesheet.has("providerServiceMapID")
						&& !emrgCasesheet.get("providerServiceMapID").isJsonNull())
					benChiefComplaint.setProviderServiceMapID(emrgCasesheet.get("providerServiceMapID").getAsInt());

				JsonObject obj = csobj.getAsJsonObject();

				if (obj.has("chiefComplaintID") && !obj.get("chiefComplaintID").isJsonNull())
					benChiefComplaint.setChiefComplaintID(obj.get("chiefComplaintID").getAsInt());

				if (obj.has("chiefComplaint") && !obj.get("chiefComplaint").isJsonNull())
					benChiefComplaint.setChiefComplaint(obj.get("chiefComplaint").getAsString());

				if (obj.has("duration") && !obj.get("duration").isJsonNull())
					benChiefComplaint.setDuration(obj.get("duration").getAsInt());

				if (obj.has("unitOfDuration") && !obj.get("unitOfDuration").isJsonNull())
					benChiefComplaint.setUnitOfDuration(obj.get("unitOfDuration").getAsString());

				if (emrgCasesheet.has("description") && !emrgCasesheet.get("description").isJsonNull())
					benChiefComplaint.setDescription(emrgCasesheet.get("description").getAsString());

				if (emrgCasesheet.has("createdBy") && !emrgCasesheet.get("createdBy").isJsonNull())
					benChiefComplaint.setCreatedBy(emrgCasesheet.get("createdBy").getAsString());

				resArray.add(benChiefComplaint);
			}
		}

		return resArray;
	}


	public Long getVisitCode() {
		return visitCode;
	}


	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
