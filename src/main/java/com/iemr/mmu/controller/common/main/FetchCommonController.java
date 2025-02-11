package com.iemr.mmu.controller.common.main;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.utils.MediaTypeUtils;
import com.iemr.mmu.utils.AESEncryption.AESEncryptionDecryption;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/common", headers = "Authorization")
public class FetchCommonController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonServiceImpl commonServiceImpl;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private AESEncryptionDecryption aESEncryptionDecryption;

	@Autowired
	public void setCommonServiceImpl(CommonServiceImpl commonServiceImpl) {
		this.commonServiceImpl = commonServiceImpl;
	}

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Deprecated
	@CrossOrigin()
	@ApiOperation(value = "provides doctor worklist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDocWorklist" }, method = { RequestMethod.GET })
	public String getDocWorkList() {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonDoctorServiceImpl.getDocWorkList();
			response.setResponse(s);
		} catch (Exception e) {
			logger.error("Error in getDocWorkList:" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// doc worklist new
	@CrossOrigin()
	@ApiOperation(value = "provides doctor worklist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDocWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getDocWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && serviceID != null) {
				String s = commonDoctorServiceImpl.getDocWorkListNew(providerServiceMapID, serviceID, vanID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or ServiceID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + serviceID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or ServiceID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getDocWorkList:" + e);
			response.setError(5000, "Error while getting doctor worklist");
		}
		return response.toString();
	}

	// doc worklist new (TM future scheduled beneficiary)
	@CrossOrigin()
	@ApiOperation(value = "provides doctor worklist future scheduled for TM", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDocWorkListNewFutureScheduledForTM/{providerServiceMapID}/{serviceID}" }, method = {
			RequestMethod.GET })
	public String getDocWorkListNewFutureScheduledForTM(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && serviceID != null) {
				String s = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(providerServiceMapID,
						serviceID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or ServiceID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + serviceID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or ServiceID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getDocWorkListFutureScheduledbeneficiary:" + e);
			response.setError(5000, "Error while getting doctor worklist for future scheduled beneficiay");
		}
		return response.toString();
	}

	@Deprecated
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorklist" }, method = { RequestMethod.GET })
	public String getNurseWorkList(@PathVariable("visitCategoryID") Integer visitCategoryID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkList();
			response.setResponse(s);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// nurse worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getNurseWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting nurse worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(5000, "Error while getting nurse worklist");
		}
		return response.toString();
	}

	/**
	 * @author SH20094090
	 * @param providerServiceMapID
	 * @param vanID
	 * @return
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist TM referred", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorklistTMreferred/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getNurseWorklistTMreferred(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkListTMReferred(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting nurse worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(5000, "Error while getting nurse worklist");
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Doctor Entered Previous significant Findings", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDoctorPreviousSignificantFindings" }, method = { RequestMethod.POST })
	public String getDoctorPreviousSignificantFindings(
			@ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj != null && obj.has("beneficiaryRegID") && obj.get("beneficiaryRegID") != null) {
				Long beneficiaryRegID = obj.getLong("beneficiaryRegID");
				String s = commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(beneficiaryRegID);
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error while getting previous significant findings");
			} else {
				response.setError(5000, "Invalid data!");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error while fetching previous significant findings" + e);
			response.setError(5000, "Error while getting previous significant findings");
		}
		return response.toString();
	}

	// Get Lab technician worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get Lab technician worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getLabWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getLabWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getLabWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting lab technician worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting lab technician worklist");
		}
		return response.toString();
	}

	// Get radiologist worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get radiologist worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getRadiologist-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getRadiologistWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getRadiologistWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting radiologist worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting radiologist worklist");
		}
		return response.toString();
	}

	// Get oncologist worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get oncologist worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getOncologist-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getOncologistWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getOncologistWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting oncologist worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting oncologist worklist");
		}
		return response.toString();
	}

	// Get pharma worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get pharma worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getPharma-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getPharmaWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getPharmaWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting pharma worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting pharma worklist");
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "get case-sheet print data for beneficiary.", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/Case-sheet/printData" }, method = { RequestMethod.POST })
	public String getCasesheetPrintData(@RequestBody String comingReq,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			if (comingReq != null) {
				BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(comingReq, BeneficiaryFlowStatus.class);
				String casesheetData = commonServiceImpl.getCaseSheetPrintDataForBeneficiary(obj, Authorization);
				response.setResponse(casesheetData);
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("" + e);
		}

		return response.toString();
	}

	// Start of Fetch Previous Medical History...
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Past History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPastHistory" }, method = { RequestMethod.POST })
	public String getBenPastHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPastHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPastHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPastHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting illness and surgery history");
			logger.error("Error in getBenPastHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Tobacco History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenTobaccoHistory" }, method = { RequestMethod.POST })
	public String getBenTobaccoHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenTobaccoHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getPersonalTobaccoHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenTobaccoHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting tobacco history");
			logger.error("Error in getBenTobaccoHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Alcohol History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenAlcoholHistory" }, method = { RequestMethod.POST })
	public String getBenAlcoholHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenAlcoholHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getPersonalAlcoholHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenAlcoholHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting alcohol history");
			logger.error("Error in getBenAlcoholHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Allergy History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenAllergyHistory" }, method = { RequestMethod.POST })
	public String getBenANCAllergyHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenAllergyHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getPersonalAllergyHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenAllergyHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting allergy history");
			logger.error("Error in getBenAllergyHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Medication History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenMedicationHistory" }, method = { RequestMethod.POST })
	public String getBenMedicationHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenMedicationHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getMedicationHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenMedicationHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting medication history");
			logger.error("Error in getBenMedicationHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Family History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFamilyHistory" }, method = { RequestMethod.POST })
	public String getBenFamilyHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenFamilyHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getFamilyHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenFamilyHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting family history");
			logger.error("Error in getBenFamilyHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Menstrual History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenMenstrualHistory" }, method = { RequestMethod.POST })
	public String getBenMenstrualHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenMenstrualHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getMenstrualHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenMenstrualHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting menstrual history");
			logger.error("Error in getBenMenstrualHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary past Obstetric History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPastObstetricHistory" }, method = { RequestMethod.POST })
	public String getBenPastObstetricHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPastObstetricHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getObstetricHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPastObstetricHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting obstetric history");
			logger.error("Error in getBenPastObstetricHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Comorbidity Condition Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenComorbidityConditionHistory" }, method = { RequestMethod.POST })
	public String getBenANCComorbidityConditionHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenComorbidityConditionHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getComorbidHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenComorbidityConditionHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting comodbidity history");
			logger.error("Error in getBenComorbidityConditionHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Optional Vaccine Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenOptionalVaccineHistory" }, method = { RequestMethod.POST })
	public String getBenOptionalVaccineHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenOptionalVaccineHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getChildVaccineHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenOptionalVaccineHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting optional vaccination history");
			logger.error("Error in getBenOptionalVaccineHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Child Vaccine(Immunization) Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenChildVaccineHistory" }, method = { RequestMethod.POST })
	public String getBenImmunizationHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenImmunizationHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getImmunizationHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenImmunizationHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting child vaccine(immunization) history");
			logger.error("Error in getBenImmunizationHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Perinatal History Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPerinatalHistory" }, method = { RequestMethod.POST })
	public String getBenPerinatalHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPerinatalHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPerinatalHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPerinatalHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting perinatal history");
			logger.error("Error in getBenPerinatalHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Child Feeding History Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFeedingHistory" }, method = { RequestMethod.POST })
	public String getBenFeedingHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenFeedingHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenFeedingHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenFeedingHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting child feeding history");
			logger.error("Error in getBenFeedingHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Child Development History Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDevelopmentHistory" }, method = { RequestMethod.POST })
	public String getBenDevelopmentHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenDevelopmentHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenDevelopmentHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenDevelopmentHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting child development history");
			logger.error("Error in getBenDevelopmentHistory:" + e);
		}
		return response.toString();
	}

	// End of Fetch Previous History...

	/***
	 * fetch ben previous visit details for history case-record(Platform).
	 * 08-08-2018
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get casesheet History of Beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBeneficiaryCaseSheetHistory" }, method = { RequestMethod.POST })
	public String getBeneficiaryCaseSheetHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for fetching beneficiary previous visit history :" + comingRequest);
		try {
			String responseData = commonServiceImpl.getBenPreviousVisitDataForCaseRecord(comingRequest);
			if (responseData != null)
				response.setResponse(responseData);
			else
				response.setError(5000, "Error while fetching beneficiary previous visit history details");
		} catch (Exception e) {
			response.setError(5000, "Error while fetching beneficiary previous visit history details");
			logger.error("Error while fetching beneficiary previous visit history :" + e);
		}
		return response.toString();
	}

	// TC specialist worklist new
	@CrossOrigin()
	@ApiOperation(value = "TCSpecialist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getTCSpecialistWorklist/{providerServiceMapID}/{serviceID}/{userID}" }, method = {
			RequestMethod.GET })
	public String getTCSpecialistWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("userID") Integer userID, @PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && userID != null) {
				String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(providerServiceMapID, userID,
						serviceID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + userID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getTC_SpecialistWorkList:" + e);
			response.setError(5000, "Error while getting TC specialist worklist");
		}
		return response.toString();
	}

	// TC specialist worklist new future scheduled
	@CrossOrigin()
	@ApiOperation(value = "TCSpecialist future scheduled", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = {
			"/getTCSpecialistWorklistFutureScheduled/{providerServiceMapID}/{serviceID}/{userID}" }, method = {
					RequestMethod.GET })
	public String getTCSpecialistWorklistFutureScheduled(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("userID") Integer userID,
			@PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && userID != null) {
				String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(providerServiceMapID,
						userID, serviceID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
						+ providerServiceMapID + " UserID = " + userID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getTC_SpecialistWorkList future scheduled:" + e);
			response.setError(5000, "Error while getting TC specialist future scheduled worklist");
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "download file from file system", consumes = "application/json", produces = "application/octet-stream")
	@RequestMapping(value = { "/downloadFile" }, method = RequestMethod.POST)
	public ResponseEntity<InputStreamResource> downloadFile(@RequestBody String requestOBJ, HttpServletRequest request)
			throws Exception {
		JSONObject obj = new JSONObject(requestOBJ);
		try {
//	/	String contentType = null;
//		Resource resource = null;
//		if (file != null) {
//			JSONObject obj = new JSONObject(file);
//			if (obj != null && obj.has("fileName") & obj.has("filePath"))
//				;
//			resource = commonServiceImpl.loadFileAsResource(obj.getString("fileName"), obj.getString("filePath"));
//
////			try {
////				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
////			} catch (Exception e) {
////			}
//			if (contentType == null)
//				contentType = "applicaiton/octet-stream";
//
//			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//					.contentLength(resource.getFile().getTotalSpace())
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename =" + resource.getFile().getName())
//					.body(resource);
//		} else
//			throw new Exception("Invalid request...!");
			
/*
 *
 *
 KA40094929 - Internal path disclosure - Decryption
 *
 *
 */
			if( obj.has("fileName") && obj.has("filePath") && obj.get("fileName") != null && obj.get("filePath") != null ){

			MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext,
					obj.getString("fileName"));
//			System.out.println("fileName: " + obj.getString("fileName"));
//			System.out.println("mediaType: " + mediaType);
			
			String decryptFilePath = aESEncryptionDecryption.decrypt(obj.getString("filePath"));

			File file = new File(decryptFilePath);
			//File file = new File(obj.getString("filePath"));
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

//			String filename = obj.getString("fileName");
//			HttpHeaders headers = new HttpHeaders();
//			headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Disposition"));
//			headers.set("Content-Disposition", "attachment; filename=" + filename);
//			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
			
			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + obj.getString("fileName"))
					// Content-Type
					.contentType(mediaType)
					// Contet-Length
					.contentLength(file.length()).body(resource);
			}
			else {
				throw new Exception("Invalid file name or file path");
			}
		} catch (Exception e) {
			logger.error("error while downloading file..." + obj.getString("fileName"));
			throw new Exception("Error while downloading file. Please contact administrator..");
		}

	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Physical History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPhysicalHistory" }, method = { RequestMethod.POST })
	public String getBenPhysicalHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPhysicalHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPhysicalHistory(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPhysicalHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting Physical history");
			logger.error("Error in getBenPhysicalHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Symptomatic questionnaire answer details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenSymptomaticQuestionnaireDetails" }, method = { RequestMethod.POST })
	public String getBenSymptomaticQuestionnaireDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary Symptomatic questionnaire answer details request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenSymptomaticQuestionnaireDetailsData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("Get Beneficiary Symptomatic questionnaire answer details response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting details");
			logger.error("Error in Get Beneficiary Symptomatic questionnaire answer details:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary previous Diabetes history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPreviousDiabetesHistoryDetails" }, method = { RequestMethod.POST })
	public String getBenPreviousDiabetesHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary previous Diabetes history request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPreviousDiabetesData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("Get Beneficiary previous Diabetes history response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting details");
			logger.error("Error in Get Beneficiary previous Diabetes history:" + e);
		}
		return response.toString();
	}

	/***
	 * @author DU20091017
	 * @param comingRequest
	 * @param Authorization
	 * @return
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary TM case sheet", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/Case-sheet/TMReferredprintData" }, method = { RequestMethod.POST })
	public String getTMReferredPrintData(
			@ApiParam(value = "{\r\n" + "  \"VisitCategory\": \"String\",\r\n" + "  \"benFlowID\": \"Integer\",\r\n"
					+ "  \"benVisitID\": \"Integer\",\r\n" + "  \"beneficiaryRegID\": \"Long\",\r\n"
					+ "  \"visitCode\": \"Long\"\r\n" + "}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization,
			@RequestHeader(value = "ServerAuthorization") String ServerAuthorization) {
		OutputResponse response = new OutputResponse();
		logger.info("getTMReferredPrintData request - " + comingRequest);
		try {
			if (comingRequest != null) {
				BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(comingRequest, BeneficiaryFlowStatus.class);
				String casesheetData = null;

				// to check whether case sheet is already downloaded or not
				int caseSheetStatus = commonServiceImpl.checkIsCaseSheetDownloaded(obj.getBenVisitCode());

				if (caseSheetStatus == 1 ) {
					// fetch case sheet from downloaded table
					casesheetData = commonServiceImpl.getTmCaseSheetOffline(obj);
				} else if (caseSheetStatus == 0) {
					casesheetData = commonServiceImpl.getCaseSheetFromCentralServer(comingRequest, ServerAuthorization);
				}
				if (casesheetData != null)
					response.setResponse(casesheetData);
				else
					response.setError(5000, "Beneficiary pending for Tele-Consultation");
			} else
				response.setError(5000, "Invalid request");
		}catch (IEMRException e) {
			logger.error("getTMReferredPrintData iemrexception : " + e);
			if(e.getErrorCode() != null && e.getErrorCode() == 5002)
				response.setError(5003, e.getMessage());
			else
				response.setError(5000, e.getMessage());
		} catch (Exception e) {
			logger.error("Error on getTMReferredPrintData Exception : " + e);
			response.setError(5000, "Error in getting case sheet - "+ e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary previous Referral history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPreviousReferralHistoryDetails" }, method = { RequestMethod.POST })
	public String getBenPreviousReferralHistoryDetails(

			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary previous Referral history request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPreviousReferralData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("Get Beneficiary previous Referral history response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting details");
			logger.error("Error in Get Beneficiary previous Referral history:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary TM case sheet", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/Case-sheet/centralServerTMCaseSheet" }, method = { RequestMethod.POST })
	public String getTMCaseSheetFromCentralServer(
			@ApiParam(value = "{\r\n" + "  \"VisitCategory\": \"String\",\r\n" + "  \"benFlowID\": \"Integer\",\r\n"
					+ "  \"benVisitID\": \"Integer\",\r\n" + "  \"beneficiaryRegID\": \"Long\",\r\n"
					+ "  \"visitCode\": \"Long\"\r\n" + "}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();

		logger.info("getTMCaseSheetFromCentralServer request - " + comingRequest);
		try {
			if (comingRequest != null) {

				String caseSheetFromCentralServer = commonServiceImpl.getCaseSheetOfTm(comingRequest, Authorization);

				if (caseSheetFromCentralServer != null)
					response.setResponse(caseSheetFromCentralServer);
				else
					response.setError(5000, "Beneficiary pending for Tele-Consultation");

			} else
				response.setError(5000, "Invalid request");
		} 
		catch (IEMRException e) {
			logger.error("getTMCaseSheetFromCentralServer IEMR exception - " + e);
			if(e.getErrorCode() != null && e.getErrorCode() == 5002)
				response.setError(5002, e.getMessage());
			else
				response.setError(5000, e.getMessage());
		}
		catch (Exception e) {
			logger.error("getTMCaseSheetFromCentralServer Exception - " + e);
			response.setError(5000,"Error in MMU central server case sheet" + e.getMessage());
		}

		return response.toString();

	}
	/**
	 * Author SH20094090
	 * @param comingRequest
	 * @return ProviderSpecificMasterData
	 */
	@CrossOrigin()
	@ApiOperation(value = "Calculate Beneficiary BMI Status", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/calculateBMIStatus" }, method = { RequestMethod.POST })
	public String calculateBMIStatus(@ApiParam(value = "{\"bmi\":\"double\",\"yearMonth\":\"String\",\"gender\":\"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("calculateBMIStatus request:" + comingRequest);
		try {
				String s = commonNurseServiceImpl.calculateBMIStatus(comingRequest);
				response.setResponse(s);
			logger.info("calculateBMIStatus response:" + response);
		} catch (Exception e) {
			response.setError(5000, e.getMessage());
			logger.error("Error in calculateBMIStatus:" +  e.getMessage());
		}
		return response.toString();
	}

}
