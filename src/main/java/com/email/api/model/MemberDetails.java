package com.email.api.model;

public class MemberDetails {

	private String memberId;
	private String memberName;

	public MemberDetails() {

	}

	public MemberDetails(String memberId, String memberName) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
