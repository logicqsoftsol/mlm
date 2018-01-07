package com.logicq.mlm.service.user;

import java.util.Date;
import java.util.List;

import com.logicq.mlm.model.profile.UserDocument;
import com.logicq.mlm.model.profile.UserProfile;

public interface IDocumentUploadService {
	
	void saveDocumentDetails(UserDocument uploadDocument);
	void updateDocumentDetails(long docuemntid,UserProfile userProfile);
	List<UserDocument> getDocumentsAccordingToDate(Date inputDate);
	
	UserDocument getDocumentsAccordingToUserName(String userName);
	UserDocument getDocuemnt(long docuemntid);
	

}
