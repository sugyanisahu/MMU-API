package com.iemr.mmu.service.snomedct;

import com.iemr.mmu.data.snomedct.SCTDescription;

public interface SnomedService {

	public SCTDescription findSnomedCTRecordFromTerm(String term);

	public String findSnomedCTRecordList(SCTDescription sctdescription) throws Exception;

}
