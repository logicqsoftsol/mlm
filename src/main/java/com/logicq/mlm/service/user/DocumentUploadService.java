package com.logicq.mlm.service.user;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.user.IDocumentUploadDAO;
import com.logicq.mlm.model.profile.UserDocument;
import com.logicq.mlm.model.profile.UserProfile;

@Service
@Transactional
public class DocumentUploadService  implements IDocumentUploadService{

	@Autowired
	IDocumentUploadDAO docuemntUploadDAO;
	
	@Override
	public void saveDocumentDetails(UserDocument uploadDocument) {
		docuemntUploadDAO.saveDocumentDetails(uploadDocument);
	}

	@Override
	public List<UserDocument> getDocumentsAccordingToDate(Date inputDate) {
		// TODO Auto-generated method stub
		return docuemntUploadDAO.getDocumentsAccordingToDate(inputDate);
	}

	@Override
	public UserDocument getDocumentsAccordingToUserName(String userName) {
		List<UserDocument> documents=docuemntUploadDAO.getDocumentsAccordingToUserName(userName);
		if(null!=documents && !documents.isEmpty()){
			return documents.get(0);
		}
		return null;
	}

	@Override
	public UserDocument getDocuemnt(long docuemntid) {
		return docuemntUploadDAO.getDocuemnt(docuemntid);
	}

	@Override
	public void updateDocumentDetails(long documentid, UserProfile userProfile) {
		UserDocument userDocument=docuemntUploadDAO.getDocuemnt(documentid);
		userDocument.setMobileNumber(userProfile.getLogindetails().getMobilenumber());	
		userDocument.setUsername(userProfile.getLogindetails().getUsername());
		userDocument.setProfileID(userProfile.getId());
		docuemntUploadDAO.updateDocuemnt(userDocument);
   
	}

}
