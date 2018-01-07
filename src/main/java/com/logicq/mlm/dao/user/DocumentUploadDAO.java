package com.logicq.mlm.dao.user;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.profile.UserDocument;

@Repository
public class DocumentUploadDAO  extends AbstractDAO<UserDocument> implements IDocumentUploadDAO{

	@Override
	public void saveDocumentDetails(UserDocument uploadDocument) {
		save(uploadDocument);
	}

	@Override
	public List<UserDocument> getDocumentsAccordingToDate(Date inputDate) {
		StringBuilder query=new StringBuilder();
		query.append(" from UploadDocument ud where ud.uploadDate='"+inputDate+"'");
		return (List<UserDocument>) execcuteQuery(query.toString());
	}

	@Override
	public List<UserDocument> getDocumentsAccordingToUserName(String userName) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserDocument ud where ud.username='"+userName+"'" );
		return (List<UserDocument>) execcuteQuery(query.toString());
	}

	@Override
	public UserDocument getDocuemnt(long docuemntid) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserDocument ud where ud.documentID="+docuemntid+"" );
		return  execcuteQueryForUnique(query.toString());
	}

	@Override
	public void updateDocuemnt(UserDocument uploadDocument) {
		update(uploadDocument);
	}

	
}
