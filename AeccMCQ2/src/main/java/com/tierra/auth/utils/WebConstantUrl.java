package com.tierra.auth.utils;

public interface WebConstantUrl {
	//Menus&SubMenus
	String User = "/Auth/User";
	String Roles = "/Auth/Roles";
	String Feature = "/AccessPolicy/Feature";
	String SubMenu = "/AccessPolicy/SubMenu";
	String Menu = "/AccessPolicy/Menu";
	String Permission = "/AccessPolicy/Permission";
	String Question = "/Question/Question";
	String Chapter = "/MasterData/Chapter";
	String Quality = "/MasterData/Quality";
	String Subject = "/MasterData/Subject";
	String StorageLocation = "/MasterData/StorageLocation";
	String PlanStatus = "/MasterData/PlanStatus";
	String Result = "/Result/Result";
	String Message = "/Message/Message";
	//Standard Features
	String Create = "/Create";
	String Update = "/Update";
	String Delete = "/Delete";
	String View = "/GetAll";
	String FindById = "/FindById";
	String FindUsersAttendedTests="/FindUsersAttendedTests";
	//Other Features
	String FindByUserCode = "/FindByUserCode";
	String AddWeeklyTest = "/AddWeeklyTest";
	String FindPermissionByMenuAndSubMenuAndFeature = "/FindPermissionByMenuAndSubMenuAndFeature";
	String FindByRoleId = "/FindByRoleId";
	String SignIn="/signin";
	String SignOut="/signout";
	String ChangePassword = "/ChangePassword";
	String SEND_OTP = "/sendOtp";
	String FORGOT_PASSWORD = "/ForgotPassword";
	String FindUserResult = "/FindUserResult";
	String FindSection = "/FindSection";
	String UserDashboard = "/UserDashboard";


}
