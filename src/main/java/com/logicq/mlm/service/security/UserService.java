package com.logicq.mlm.service.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.logicq.mlm.common.factory.LoginFactory;
import com.logicq.mlm.model.login.Login;
import com.logicq.mlm.vo.LoginVO;

public class UserService implements UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    private static  Map<String, LoginVO> userMap = new ConcurrentHashMap<String, LoginVO>();
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    
	
	public LoginVO checkUserDetails(final String username,final  String password) {
		LoginVO logindetails = loadUserByUsername(username);
		if (null == logindetails) {
			throw new UsernameNotFoundException("Check your User name and password");
		} else{
			if(StringUtils.isEmpty(logindetails.getPassword())){
				throw new UsernameNotFoundException("Check your User name and password");
			}
			//very soon we we will change with admin token base login
		//    String encodedpassword=	passwordEncoder.encode(uservo.getPassword());
			/*if(!password.equals(logindetails.getPassword())){
				throw new UsernameNotFoundException("Check your User name and password");
			}*/
			 detailsChecker.check(logindetails);
		}
		return logindetails;
	}

    public static void addUser(Login user) {
     LoginVO loginvo=LoginFactory.create(user);
        userMap.put(user.getUsername(), loginvo);
    }
    
    public static void removeUser(Login user) {
        userMap.remove(user.getUsername());
    }
    
    public static void removeUser(String username) {
        userMap.remove(username);
    }
    public static LoginVO getUserByUsername(String username) {
		return userMap.get(username);

	}
   
    
    public final LoginVO loadUserByUsername(String username) throws UsernameNotFoundException {
		return userMap.get(username);

	}
    
}
