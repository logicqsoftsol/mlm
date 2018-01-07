package com.logicq.mlm.service.networkdetails;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.logicq.mlm.dao.networkdetails.INetworkDetailsDAO;
import com.logicq.mlm.model.profile.NetworkInfo;
import com.logicq.mlm.service.security.UserService;
import com.logicq.mlm.vo.LoginVO;
import com.logicq.mlm.vo.NetworkVO;

@Service
@Transactional
public class NetworkDetailsService implements INetworkDetailsService {

	@Autowired
	INetworkDetailsDAO networkdetailsdao;
	
	@Override
	public NetworkInfo getNetworkDetails(String memberid) {
		return networkdetailsdao.getNetworkDetails(memberid);
	}

	@Override
	public void saveNetworkDetails(NetworkInfo networkinfo) {
		networkdetailsdao.saveNetworkDetails(networkinfo);
	}

	@Override
	public void updateNetworkDetails(NetworkInfo networkinfo) {
		networkdetailsdao.updateNetworkDetails(networkinfo);
	}

	@Override
	public List<NetworkInfo> getNetworkDetailsForParent(String parentid) {
		return networkdetailsdao.getNetworkDetailsForParent(parentid);
	}

	@Override
	public NetworkInfo getUpdatePedingNetworkDetails(String memberid) {
		return networkdetailsdao.getUpdatePedingNetworkDetails(memberid);
	}

	@Override
	public List<NetworkVO> getAllNetworkList(int pagesize, int pagenumber) {
		List<NetworkVO> networkList=new ArrayList<>();
		 List<NetworkInfo> networkinfolist=networkdetailsdao.getAllNetworkList(pagesize, pagenumber);
		 if(null!=networkinfolist && !networkinfolist.isEmpty()){
        	 for(NetworkInfo network:networkinfolist){
				NetworkVO networkvo = new NetworkVO();
				if (null != network.getDateofjoin()) {
					networkvo.setDateofjoin(network.getDateofjoin());
				}
				networkvo.setMemberid(network.getMemberid());
				networkvo.setParentmemberid(network.getParentmemberid());
				if(!StringUtils.isEmpty(network.getParentmemberid())){
				LoginVO parentloginvo=UserService.getUserByUsername(network.getParentmemberid());
				networkvo.setParentfirstname(parentloginvo.getFirstname());
				networkvo.setParentlastname(parentloginvo.getLastname());
				}
				LoginVO loginvo=UserService.getUserByUsername(network.getMemberid());
				networkvo.setFirstname(loginvo.getFirstname());
				networkvo.setLastname(loginvo.getLastname());
				networkList.add(networkvo);
        	 }
          }
		return networkList;
	}

	@Override
	public List<NetworkVO> getNetworkDetailsForParentWithPagination(String parentid) {
		List<NetworkVO> networkList=new ArrayList<>();
		 List<NetworkInfo> networkinfolist=networkdetailsdao.getNetworkDetailsForParent(parentid);
          if(null!=networkinfolist && !networkinfolist.isEmpty()){
        	 for(NetworkInfo network:networkinfolist){
				NetworkVO networkvo = new NetworkVO();
				if (null != network.getDateofjoin()) {
					networkvo.setDateofjoin(network.getDateofjoin());
				}
				networkvo.setMemberid(network.getMemberid());
				networkvo.setParentmemberid(network.getParentmemberid());
				
				if(!StringUtils.isEmpty(network.getParentmemberid())){
					LoginVO parentloginvo=UserService.getUserByUsername(network.getParentmemberid());
					networkvo.setParentfirstname(parentloginvo.getFirstname());
					networkvo.setParentlastname(parentloginvo.getLastname());
					}
				LoginVO loginvo=UserService.getUserByUsername(network.getMemberid());
				networkvo.setFirstname(loginvo.getFirstname());
				networkvo.setLastname(loginvo.getLastname());
				networkList.add(networkvo);
        	 }
          }
		 return  networkList;
	}

}
