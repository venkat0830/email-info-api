package com.email.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "trackmyrecord2")
@CompoundIndexes({
		@CompoundIndex(name = "providerTin_recordLastUpdatedDate", def = "{'providerDetails.providerTin' : 1, 'recordInfo.recordLastUpdateDate': -1}") })
public class RecordDetails {
	@Id
	private String id;
	private RecordInfo recordInfo;
	private ProviderDetails providerDetails;
	private MemberDetails memberDetails;
	private ClaimDetails claimDetails;

	public RecordDetails() {

	}

	public RecordDetails(RecordInfo recordInfo, ProviderDetails providerDetails, MemberDetails memberDetails,
			ClaimDetails claimDetails) {
		super();
		this.recordInfo = recordInfo;
		this.providerDetails = providerDetails;
		this.memberDetails = memberDetails;
		this.claimDetails = claimDetails;
	}

	public RecordInfo getRecordInfo() {
		return recordInfo;
	}

	public void setRecordInfo(RecordInfo recordInfo) {
		this.recordInfo = recordInfo;
	}

	public ProviderDetails getProviderDetails() {
		return providerDetails;
	}

	public void setProviderDetails(ProviderDetails providerDetails) {
		this.providerDetails = providerDetails;
	}

	public MemberDetails getMemberDetails() {
		return memberDetails;
	}

	public void setMemberDetails(MemberDetails memberDetails) {
		this.memberDetails = memberDetails;
	}

	public ClaimDetails getClaimDetails() {
		return claimDetails;
	}

	public void setClaimDetails(ClaimDetails claimDetails) {
		this.claimDetails = claimDetails;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
