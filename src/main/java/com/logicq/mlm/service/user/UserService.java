package com.logicq.mlm.service.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.logicq.mlm.common.helper.StringFormatHelper;
import com.logicq.mlm.common.helper.sms.MessageHelper;
import com.logicq.mlm.dao.user.IUserDAO;
import com.logicq.mlm.model.login.Authority;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.profile.WalletDetails;
import com.logicq.mlm.model.wallet.WalletStatement;
import com.logicq.mlm.service.networkdetails.INetworkDetailsService;
import com.logicq.mlm.service.otp.IOTPService;
import com.logicq.mlm.service.wallet.IWalletStmntService;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	IUserDAO userdao;

	@Autowired
	IOTPService otpservice;

	@Autowired
	IWalletStmntService walletStatementservice;

	@Autowired
	INetworkDetailsService netWorkDetailsService;

	@Autowired
	IDocumentUploadService docuemntService;

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserProfile fetchUser(UserProfile user) {

		return userdao.fetchUser(user);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUser(UserProfile user) {
		userdao.deleteUser(user);

	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveUser(UserProfile user) throws Exception {

		if (null != user.getConatctDetails()) {
			user.getConatctDetails().setUserprofile(user);
		}

		if (null != user.getLogindetails()) {
			user.getLogindetails().setEnabled(Boolean.TRUE);
			user.getLogindetails().setUserprofile(user);
			if (StringUtils.isEmpty(user.getLogindetails().getEmail())) {
				user.getLogindetails().setEmail(MessageHelper.getAdminEmail());
			}
			if (null == user.getLogindetails().getAuthorities()) {
				user.getLogindetails().setAuthorities(new ArrayList<Authority>());
				user.getLogindetails().getAuthorities().add(new Authority());
			}

			// This user hardcode to handle temporary solve problem
			user.getLogindetails().getAuthorities().get(0).setId(new Long(2));
			user.getLogindetails().getAuthorities().get(0).setName("USER");
		}
		if (null != user.getNetworkinfo()) {
			user.getNetworkinfo().setMemberlevel("LEVEL0");
			user.getNetworkinfo().setUserprofile(user);
			user.getNetworkinfo().setDateofjoin(new Date());
		}
		if (null != user.getBankAccountDetails()) {
			user.getBankAccountDetails().setUserprofile(user);
		}
		if (null != user.getSocialdetails()) {
			user.getSocialdetails().setUserprofile(user);
		}
		if (null == user.getWalletdetails()) {
			user.setWalletdetails(new WalletDetails());
		}
		WalletStatement walletstment = new WalletStatement();
		if (null != user.getWalletdetails()) {
			user.getWalletdetails().setUserprofile(user);
			user.getWalletdetails().setIsactive(Boolean.FALSE);
			user.getWalletdetails().setWalletid(StringFormatHelper.randomString().toUpperCase());
			user.getWalletdetails().setWalletnumber(StringFormatHelper.sevenDigitRandomNumber());
			walletstment.setCurrentbalance(new BigDecimal(00.00));
			walletstment.setMaxencashable(new BigDecimal(00.00));
			walletstment.setPayout(new BigDecimal(00.00));
			walletstment.setEncashedAmount(new BigDecimal(00.00));
			walletstment.setWallet(user.getWalletdetails());
			walletstment.setWalletid(user.getWalletdetails().getWalletid());
			walletstment.setWalletlastupdate(null);
		}
		userdao.saveUser(user);
		walletStatementservice.addWalletStmnt(walletstment);

		com.logicq.mlm.service.security.UserService.addUser(user.getLogindetails());
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateUser(UserProfile user) {
		userdao.updateUser(user);
		com.logicq.mlm.service.security.UserService.addUser(user.getLogindetails());
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserProfile fetchUserAccordingToProfileId(Long profileid) {
		return userdao.fetchUserAccordingToProfileId(profileid);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserProfile fetchUserAccordingToUserName(String username) {
		return userdao.fetchUserAccordingToUserName(username);
	}

}
