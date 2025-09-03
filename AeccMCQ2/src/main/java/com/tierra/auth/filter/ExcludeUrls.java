package com.tierra.auth.filter;

import org.springframework.stereotype.Component;

import com.tierra.auth.utils.WebConstantUrl;
@Component
public class ExcludeUrls {
	
//	exclude Urls if Authentication is required and access provieded for All users
	
	public boolean isPermitted (String requestedUrl) {
		if(requestedUrl.equals(WebConstantUrl.Roles+WebConstantUrl.View)||
	       requestedUrl.equals(WebConstantUrl.User+WebConstantUrl.ChangePassword)||
	       requestedUrl.equals(WebConstantUrl.User+WebConstantUrl.FindByUserCode)||
	       requestedUrl.equals(WebConstantUrl.Roles+WebConstantUrl.FindByRoleId)||
	       requestedUrl.equals(WebConstantUrl.Permission+WebConstantUrl.FindPermissionByMenuAndSubMenuAndFeature)||
	       requestedUrl.equals(WebConstantUrl.Roles+WebConstantUrl.FindByRoleId)||
	       requestedUrl.equals(WebConstantUrl.Result+WebConstantUrl.UserDashboard)||
	       requestedUrl.equals(WebConstantUrl.User+WebConstantUrl.SignOut)
	       ) {
			return true;
		     }else {
		    return false; 
		     }
		
	}

}
