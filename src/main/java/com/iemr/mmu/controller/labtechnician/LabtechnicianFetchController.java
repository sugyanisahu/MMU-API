package com.iemr.mmu.controller.labtechnician;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author NE298657
 * @Objective Fetching lab tests prescribed by doctor
 *
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/labTechnician", headers = "Authorization")
public class LabtechnicianFetchController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Autowired
	public void setLabTechnicianServiceImpl(LabTechnicianServiceImpl labTechnicianServiceImpl) {
		this.labTechnicianServiceImpl = labTechnicianServiceImpl;
	}

	/**
	 * @Objective Fetching beneficiary lab tests prescribed by doctor.
	 * @param requestOBJ
	 * @return lab tests prescribed by doctor
	 */
	@CrossOrigin
	@ApiOperation(value = "getBeneficiaryPrescribedProcedure..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/prescribedProceduresList" }, method = { RequestMethod.POST })
	public String getBeneficiaryPrescribedProcedure(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request obj to fetch lab tests :" + requestOBJ);
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null && !jsnOBJ.isJsonNull() && jsnOBJ.has("beneficiaryRegID") && jsnOBJ.has("visitCode")) {

				String s = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(
						jsnOBJ.get("beneficiaryRegID").getAsLong(), jsnOBJ.get("visitCode").getAsLong());
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error in prescribed procedure details");
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while getting prescribed procedure data:" + e);
			response.setError(5000, "Error while getting prescribed procedure data");
		}
		return response.toString();
	}

	// API for getting lab result based on beneficiaryRegID and visitCode
	// 11-07-2018
	@CrossOrigin()
	@ApiOperation(value = "get lab test result for a visitcode.", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/labResultForVisitcode" }, method = { RequestMethod.POST })
	public String getLabResultForVisitCode(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null && !jsnOBJ.isJsonNull() && jsnOBJ.has("beneficiaryRegID") && jsnOBJ.has("visitCode")) {
				String s = labTechnicianServiceImpl.getLabResultForVisitcode(jsnOBJ.get("beneficiaryRegID").getAsLong(),
						jsnOBJ.get("visitCode").getAsLong());

				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error while getting lab report");
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("Error while getting lab result for requested data:" + requestOBJ);
			response.setError(5000, "Error while getting lab report");
		}
		return response.toString();
	}
}
