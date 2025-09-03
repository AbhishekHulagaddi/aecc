package com.tierra.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tierra.auth.mapper.RoleMapper;
import com.tierra.auth.mapper.UserMapper;
import com.tierra.auth.model.ChangePasswordModel;
import com.tierra.auth.model.LoginModel;
import com.tierra.auth.model.LoginResponseModel;
import com.tierra.auth.model.RoleModel;
import com.tierra.auth.model.UserCreateModel;
import com.tierra.auth.model.UserModel;
import com.tierra.auth.model.UserViewModel;
import com.tierra.auth.model.ViewAllAuthModel;
import com.tierra.auth.service.UserService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.JwtUtils;
import com.tierra.auth.utils.TokenBlacklistService;
import com.tierra.auth.utils.WebConstantUrl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(WebConstantUrl.User)
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	TokenBlacklistService tokenBlacklistService;
	
	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> saveUser (@RequestBody UserCreateModel userModel)throws Exception{
		BaseResponse baseResponse = userService.saveUser(userModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> UpdateUser(@RequestBody UserViewModel userModel)throws Exception{
		BaseResponse baseResponse = userService.updateUser(userModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteUser (@RequestBody UserModel userModel)throws Exception{
		BaseResponse baseResponse = userService.deleteUser(userModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindByUserCode)
	public ResponseEntity<?> getUserByUserCode (@RequestBody UserModel userModel)throws Exception{
		UserViewModel userM = userService.getUserByUserCode(userModel.getUsercode());
		return new ResponseEntity<>(userM,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllUsers (@RequestBody ViewAllAuthModel viewModel)throws Exception{
		
		Map<String, Object> map = new HashMap<>();
		Sort sort =null;
		Pageable pages = null;
		
		if(viewModel.getPropertyName()!=null&&viewModel.getDirection()!=null) {
			if(viewModel.getDirection().equalsIgnoreCase("desc")) {
				 sort = Sort.by(Sort.Direction.DESC, viewModel.getPropertyName());
			}else{
				 sort = Sort.by(Sort.Direction.ASC, viewModel.getPropertyName());
			}
			 pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize(), sort);
			
		}else {
			pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize());

		}
		Page<UserViewModel> userModel = userService.getAllUsers(viewModel.getRoleName(), pages);
		List<UserViewModel> userModelList = userModel.getContent();
		if (userModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", userModel.getTotalElements());
			map.put("total_pages", userModel.getTotalPages());
			map.put("usersList", userModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", userModel.getTotalElements());
		map.put("total_pages", userModel.getTotalPages());
		map.put("usersList", userModelList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}
	


	@PostMapping(WebConstantUrl.SignIn)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginModel loginRequest) throws Exception {
		
		UserModel userModel=userService.login(loginRequest);
	    String jwt = jwtUtils.generateJwtToken(userModel); 
	    LoginResponseModel loginResponse = new LoginResponseModel();
	    loginResponse.setUserId(userModel.getUserId());
	    loginResponse.setUserName(userModel.getUserName());
	    loginResponse.setMail(userModel.getMail());
	    List<String> roles = new ArrayList<>();
		 for(RoleModel role : userModel.getRoles()) {
			 roles.add(role.getRoleName());
		 }
	    loginResponse.setRoles(roles);
	    loginResponse.setToken(jwt.toString());
	    loginResponse.setUsercode(userModel.getUsercode());
		return new ResponseEntity<>(loginResponse,HttpStatus.OK);

	  }
	
	@PostMapping(WebConstantUrl.ChangePassword)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordModel changePasswordModel) throws Exception{
		BaseResponse baseResponse = userService.changePassword(changePasswordModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);

	}
	
	
	@PostMapping(WebConstantUrl.FORGOT_PASSWORD)
	public ResponseEntity<?> forgotPassword(@RequestBody ChangePasswordModel changePasswordModel) throws Exception{
		BaseResponse baseResponse = userService.forgotPassword(changePasswordModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);

	}
	
	@PostMapping(WebConstantUrl.SEND_OTP)
	public ResponseEntity<?> sendOtp(@RequestBody UserModel userModel) throws Exception{
		BaseResponse baseResponse = userService.sendOtp(userModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);

	}
	
	@PostMapping(WebConstantUrl.SignOut)
	public BaseResponse logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String tokenValue = authHeader.replace("Bearer ", "");
		// Invalidate the token
		tokenBlacklistService.invalidateToken(tokenValue);

		return new BaseResponse(CustomMessage.Lagout_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

//	@Operation(security = {})
//	@PostMapping("/signup")
//	  public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateModel signUpRequest) {
//	    if (userRepo.existsByUserName(signUpRequest.getUserName())) {
//	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//	    }
//
//	    if (userRepo.existsByMail(signUpRequest.getMail())) {
//	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//	    }
//
//	    // Create new user's account
//	    UserCreateModel user = new UserCreateModel(signUpRequest.getUserName(),
//	                         signUpRequest.getMail(),
//	                         encoder.encode(signUpRequest.getPassword()));
//	    Set<String> strRoles = new HashSet<>();
//	    for(RoleModel roleM : signUpRequest.getRoles())
//	    {
//	    	RoleDomain roleD = roleRepo.findByRoleId(roleM.getRoleId())
//	    			.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	    	String role = roleD.getRoleName();
//	    	strRoles.add(role);
//
//	    }
//	   // strRoles = signUpRequest.getRoles();
//	    Set<RoleModel> roles = new HashSet<>();
//
//	    if ((strRoles == null)||(strRoles.isEmpty())) {
//	      RoleDomain userRole = roleRepo.findByRoleName(RoleConstants.USER.toString())
//	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	      RoleModel model = roleMapper.convertDomainToModel(userRole);
//	      roles.add(model);
//	    } else {
//	      strRoles.forEach(role -> {
//	        switch (role) {
//	        case "Admin":
//	        	RoleDomain adminRole = roleRepo.findByRoleName(RoleConstants.ADMIN.toString())	
//	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	        	RoleModel model1 = roleMapper.convertDomainToModel(adminRole);
//	          roles.add(model1);
//
//	          break;
//	        case "mod":
//	        	RoleDomain modRole = roleRepo.findByRoleName(RoleConstants.MODERATOR.toString())
//	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	        	RoleModel model2 = roleMapper.convertDomainToModel(modRole);
//	          roles.add(model2);
//
//	          break;
//	        default:
//	        	RoleDomain userRole = roleRepo.findByRoleName(RoleConstants.USER.toString())
//	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	        	RoleModel model3 = roleMapper.convertDomainToModel(userRole);
//	          roles.add(model3);
//	        }
//	        
//	      });
//	    }
//
//	    user.setRoles(roles);
//	    UserDomain userD = userMapper.convertCreateModelToDomain(user);
//	    userRepo.save(userD);
//
//	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//	  }

//	  @PostMapping("/signout")
//	  public ResponseEntity<?> logoutUser() {
//	    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//	        .body(new MessageResponse("You've been signed out!"));
//	  }
	}
	


