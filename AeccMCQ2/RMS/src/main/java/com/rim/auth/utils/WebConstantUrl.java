package com.rim.auth.utils;

public interface WebConstantUrl {
	//Menus&SubMenus
	String User = "/Auth/User";
	String Roles = "/Auth/Roles";
	String Feature = "/AccessPolicy/Feature";
	String SubMenu = "/AccessPolicy/SubMenu";
	String Menu = "/AccessPolicy/Menu";
	String Permission = "/AccessPolicy/Permission";
	String Inventory = "/Inventory/Inventory";
	String Product = "/MasterData/Product";
	String Quality = "/MasterData/Quality";
	String Vendor = "/MasterData/Vendor";
	String StorageLocation = "/MasterData/StorageLocation";
	String PlanStatus = "/MasterData/PlanStatus";
	String Transactions = "/Transactions/Transactions";
	//Standard Features
	String Create = "/Create";
	String Update = "/Update";
	String Delete = "/Delete";
	String View = "/GetAll";
	String FindById = "/FindById";
	//Other Features
	String FindByUserCode = "/FindByUserCode";
	String FindPermissionByMenuAndSubMenuAndFeature = "/FindPermissionByMenuAndSubMenuAndFeature";
	String FindByRoleId = "/FindByRoleId";
	String SignIn="/signin";
	String SignOut="/signout";
	String ChangePassword = "/ChangePassword";


}
