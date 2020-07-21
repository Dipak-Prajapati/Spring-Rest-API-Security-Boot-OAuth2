package com.dips.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dips.dao.AddressDetailsRepository;
import com.dips.dao.UserDetailsRepository;
import com.dips.model.AddressModel;
import com.dips.model.UserInfo;

@Repository
@Transactional
public class UserInfoService {

	@Autowired
	private UserDetailsRepository userDatailsRepository;
	
	@Autowired
	private AddressDetailsRepository addressDetailsRepository;

	public UserInfo getUserInfoByUserName(String userName) {
		short enabled = 1;
		return userDatailsRepository.findByUserNameAndEnabled(userName, enabled);
	}

	public List<UserInfo> getAllActiveUserInfo() {
		return userDatailsRepository.findAllByEnabled((short) 1);
	}

	public UserInfo getUserInfoById(String userName,String pwd) {
		return userDatailsRepository.findById(userName,pwd);
	}

	public UserInfo addUser(UserInfo userInfo) {
		userInfo.setPwd(userInfo.getPwd());
		return userDatailsRepository.save(userInfo);
	}

	public UserInfo updateUser(UserInfo userRecord) {
		UserInfo userInfo = userDatailsRepository.findById(userRecord.getUserName(),userRecord.getPwd());
		userInfo.setEmail(userRecord.getEmail());
		userInfo.setPwd(userRecord.getPwd());
		userInfo.setRole(userRecord.getRole());
		userInfo.setEnabled(userRecord.getEnabled());
		userInfo.setPic(userRecord.getPic());

		List<AddressModel> addressId = addressDetailsRepository.getAddressId(userRecord.getId());

		List<Integer> idd = new ArrayList<Integer>();

		for (int i = 0; i < addressId.size(); i++) {
			System.out.println("Before remove :" + addressId.get(i));
		}

		for (int i = 0; i < userRecord.getAddressModel().size(); i++) {
			// System.out.println("addressId.get(i)"+addressId.get(i));
			System.out.println("userModel.getAddressModel().get(i).getId()" + userRecord.getAddressModel().get(i).getId());
			idd.add(userRecord.getAddressModel().get(i).getId());
			System.out.println("id list" + idd.get(i));
		}
		addressId.removeAll(idd);
		for (int i = 0; i < addressId.size(); i++) {
			System.out.println("after remove :" + addressId.get(i));
		}
		if (addressId.size() > 0) {
			for (int i = 0; i < addressId.size(); i++) {
			addressDetailsRepository.delete(addressId.get(i));
			}
		}

		userInfo.setAddressModel(userRecord.getAddressModel());
		return userDatailsRepository.save(userInfo);
	}

	public void deleteUser(Integer id) {
		userDatailsRepository.deleteById(id);
	}

	public UserInfo updatePassword(Integer id, UserInfo userRecord) {
		UserInfo userInfo = userDatailsRepository.findById(userRecord.getUserName(),userRecord.getPwd());
		userInfo.setPwd(userRecord.getPwd());
		return userDatailsRepository.save(userInfo);
	}

	/*public UserInfo updateRole(Integer id, UserInfo userRecord) {
		UserInfo userInfo = userDatailsRepository.findById(id);
		userInfo.setRole(userRecord.getRole());
		return userDatailsRepository.save(userInfo);
	}*/
}