package com.logicq.mlm.dao.user;

import java.util.Date;
import java.util.List;

import com.logicq.mlm.model.profile.UserDocument;

public interface IDocumentUploadDAO {
	
	void saveDocumentDetails(UserDocument uploadDocument);
	void updateDocuemnt(UserDocument uploadDocument);
	List<UserDocument> getDocumentsAccordingToDate(Date inputDate);
	
	List<UserDocument> getDocumentsAccordingToUserName(String userName);
	UserDocument getDocuemnt(long docuemntid);
	

}
