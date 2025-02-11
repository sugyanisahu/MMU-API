package com.iemr.mmu.controller.dataSyncActivity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.iemr.mmu.service.dataSyncActivity.DownloadDataFromServerImpl;
import com.iemr.mmu.service.dataSyncActivity.DownloadDataFromServerTransactionalImpl;
import com.iemr.mmu.service.dataSyncActivity.UploadDataToServerImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author NE298657
 * @date 16-08-2018
 * @purpose Class used for data sync from van-to-server & server-to-van
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/dataSyncActivity", headers = "Authorization")
public class StartSyncActivity {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private UploadDataToServerImpl uploadDataToServerImpl;
	@Autowired
	private DownloadDataFromServerImpl downloadDataFromServerImpl;
	@Autowired
	private DownloadDataFromServerTransactionalImpl downloadDataFromServerTransactionalImpl;

	@CrossOrigin()
	@ApiOperation(value = "start data sync from Van to Server", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/van-to-server" }, method = { RequestMethod.POST })
	public String dataSyncToServer(@RequestBody String requestOBJ,
			@RequestHeader(value = "Authorization") String Authorization,
			@RequestHeader(value = "ServerAuthorization") String ServerAuthorization) {
		OutputResponse response = new OutputResponse();
		try {
			// System.out.println(LocalDateTime.now());
			JSONObject obj = new JSONObject(requestOBJ);
			if (obj != null && obj.has("groupID") && obj.get("groupID") != null && obj.has("user")
					&& obj.get("user") != null && obj.has("vanID") && obj.get("vanID") != null) {
				String s = uploadDataToServerImpl.getDataToSyncToServer(obj.getInt("vanID"), obj.getInt("groupID"),
						obj.getString("user"), ServerAuthorization);
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error in data sync");
			} else {
				response.setError(5000, "Invalid request, Either of groupID or user is invalid or null");
			}
		} catch (Exception e) {
			logger.error("Error in data sync : " + e);
			response.setError(e);
		}
		// System.out.println(LocalDateTime.now());
		return response.toStringWithSerialization();
	}

	@CrossOrigin()
	@ApiOperation(value = "get data sync group details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getSyncGroupDetails" }, method = { RequestMethod.GET })
	public String getSyncGroupDetails() {
		OutputResponse response = new OutputResponse();
		try {
			String s = uploadDataToServerImpl.getDataSyncGroupDetails();
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error in getting data sync group details");
		} catch (Exception e) {
			logger.error("Error in getting data sync group details : " + e);
			response.setError(e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @return Masters download in van from central server
	 */
	@CrossOrigin()
	@ApiOperation(value = "Data sync master download", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/startMasterDownload" }, method = { RequestMethod.POST })
	public String startMasterDownload(@RequestBody String requestOBJ,
			@RequestHeader(value = "Authorization") String Authorization,
			@RequestHeader(value = "ServerAuthorization") String ServerAuthorization) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(requestOBJ);
			if (obj != null && obj.has("vanID") && obj.get("vanID") != null && obj.has("providerServiceMapID")
					&& obj.get("providerServiceMapID") != null) {
				String s = downloadDataFromServerImpl.downloadMasterDataFromServer(ServerAuthorization,
						obj.getInt("vanID"), obj.getInt("providerServiceMapID"));
				if (s != null) {
					if (s.equalsIgnoreCase("inProgress"))
						response.setError(5000,
								"Download is already in progress from different device, kindly wait to finish this");
					else
						response.setResponse(s);
				} else
					response.setError(5000, s);
			} else {
				response.setError(5000,
						"vanID / providerServiceMapID or both are missing," + " Kindly contact the administrator.");
			}

		} catch (Exception e) {
			logger.error("Error in Master data Download : " + e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Data sync master download progress check", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/checkMastersDownloadProgress" }, method = { RequestMethod.GET })
	public String checkMastersDownloadProgress() {
		OutputResponse response = new OutputResponse();
		try {
//			Map<String, Object> resultMap = new HashMap<>();
//			resultMap.put("percentage", Math.floor(
//					((DownloadDataFromServerImpl.progressCounter * 100) / DownloadDataFromServerImpl.totalCounter)));
//			resultMap.put("failedMasterCount", DownloadDataFromServerImpl.failedCounter);
//			resultMap.put("failedMasters", DownloadDataFromServerImpl.failedMasters);
//			response.setResponse(new Gson().toJson(resultMap));
			response.setResponse(new Gson().toJson(downloadDataFromServerImpl.getDownloadStatus()));
		} catch (Exception e) {
			logger.error("Error in Master data Download progress check : " + e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "getVanDetailsForMasterDownload", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getVanDetailsForMasterDownload" }, method = { RequestMethod.GET })
	public String getVanDetailsForMasterDownload() {
		OutputResponse response = new OutputResponse();
		try {
			String s = downloadDataFromServerImpl.getVanDetailsForMasterDownload();
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting van details.");
		} catch (Exception e) {
			logger.error("Error while getting van details : " + e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "call Central API To Generate BenID And import To Local", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/callCentralAPIToGenerateBenIDAndimportToLocal" }, method = { RequestMethod.POST })
	public String callCentralAPIToGenerateBenIDAndimportToLocal(@RequestBody String requestOBJ,
			@RequestHeader(value = "Authorization") String Authorization,
			@RequestHeader(value = "ServerAuthorization") String ServerAuthorization) {
		OutputResponse response = new OutputResponse();
		try {
			int i = downloadDataFromServerImpl.callCentralAPIToGenerateBenIDAndimportToLocal(requestOBJ, Authorization,
					ServerAuthorization);
			if (i == 0) {
				response.setError(5000, "Error while generating UNIQUE_ID at central server");
			} else {
				if (i == 1) {
					response.setError(5000, "UNIQUE_ID generated successfully, but error while importing to local");
				} else if (i == 2)
					response.setResponse("UNIQUE_ID generated and imported successfully");
			}
		} catch (Exception e) {
			logger.error("Error while getting van details : " + e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "call Central API To download transaction data To Local", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/downloadTransactionToLocal" }, method = { RequestMethod.POST })
	public String downloadTransactionToLocal(@RequestBody String requestOBJ,
			@RequestHeader(value = "ServerAuthorization") String ServerAuthorization) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(requestOBJ);
			if (obj != null && obj.has("vanID") && obj.get("vanID") != null) {
				int i = downloadDataFromServerTransactionalImpl.downloadTransactionalData(obj.getInt("vanID"),
						ServerAuthorization);

				if (i > 0)
					response.setResponse("Success");
				else
					response.setError(5000, "Issue in download. Please contact administrator");
			}
		} catch (Exception e) {
			logger.error("Error while downloading inventory transaction data : " + e);
			response.setError(e);
		}
		return response.toString();
	}

}
