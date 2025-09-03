package com.tierra.auth.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NameNotFoundException;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tierra.auth.domain.OtpUser;
import com.tierra.auth.domain.Roles;
import com.tierra.auth.domain.User;
import com.tierra.auth.mapper.RoleMapper;
import com.tierra.auth.mapper.UserMapper;
import com.tierra.auth.model.ChangePasswordModel;
import com.tierra.auth.model.LoginModel;
import com.tierra.auth.model.RoleModel;
import com.tierra.auth.model.RoleViewModel;
import com.tierra.auth.model.UserCreateModel;
import com.tierra.auth.model.UserModel;
import com.tierra.auth.model.UserViewModel;
import com.tierra.auth.repo.OtpUserRepo;
import com.tierra.auth.repo.RoleRepo;
import com.tierra.auth.repo.UserRepo;
import com.tierra.auth.service.RoleService;
import com.tierra.auth.service.UserService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.EmailService;
import com.tierra.auth.utils.IDGeneration;
import com.tierra.auth.utils.UserIdPrinciple;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	IDGeneration idGeneration;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OtpUserRepo otpUserRepo;
	
	
	
	
	
    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo,
                           UserMapper userMapper, IDGeneration idGeneration,
                           PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
        this.idGeneration = idGeneration;
        this.encoder = encoder;
    }

	@Override
	public BaseResponse saveUser(UserCreateModel userModel) throws Exception {
		User userDomain2 = new User();
		if(userModel.getUserName()!=null) {
			userDomain2 = userRepo.findByUserName(userModel.getUserName()).orElse(null);
		}
		if(userDomain2==null) {
			if(userRepo.existsByMail(userModel.getMail())) {
				return new BaseResponse(userModel.getMail()+" User Already Exists!", HttpStatus.BAD_REQUEST.value());
			}
			OtpUser otpuser = otpUserRepo.findByMail(userModel.getMail());
			if(otpuser==null||!otpuser.getOtp().equals(userModel.getOtp())){
				return new BaseResponse(userModel.getMail()+" Invalid Otp!", HttpStatus.BAD_REQUEST.value());	
			}
			UserViewModel userViewModel =null;
		if(userModel.getPassword().equals(userModel.getConfirmpassword())) {
		User user = userMapper.convertCreateModelToDomain(userModel);
		user.setUsercode(idGeneration.GenerateId());
		user.setCreatedDate(LocalDateTime.now());
		user.setPassword(encoder.encode(userModel.getPassword()));
		user.setStatus(true);
		try {
			user = userRepo.save(user);
		}catch( Exception e){
			throw new CusException(e+": User Not Saved ", HttpStatus.BAD_REQUEST);
		}
		userViewModel = userMapper.convertDomainToViewModel(user);
		
		}else {
			throw new CusException(" Password Mismatch ",HttpStatus.UNAUTHORIZED);
		}
		return new BaseResponse(userViewModel.getFirstName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
		}else {
			return new BaseResponse(userModel.getUserName()+" User Already Exists!", HttpStatus.BAD_REQUEST.value());
		}
	}

	@Override
	public BaseResponse updateUser(UserViewModel userModel) throws Exception {
		User user;
		if(userModel.getUserId()!=null) {
			user = userRepo.findById(userModel.getUserId())
					.orElseThrow(() -> new CusException(" User not found for the given Id ", HttpStatus.NOT_FOUND));
		}else {
			throw new CusException(" Id not Found for User ", HttpStatus.NOT_FOUND);
		}
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setMobileNumber(userModel.getMobileNumber());
		user.setAddress(userModel.getAddress());
		user.setStatus(true);
		user.setModifiedDate(LocalDateTime.now());
		user.setModifiedBy(principle.getUserId());
		user = userRepo.save(user);
		return new BaseResponse(user.getFirstName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public UserViewModel getUserByUserCode(long usercode) throws Exception {
		User user;
		if(usercode!=0) {
			user = userRepo.findByUsercode(usercode)
					.orElseThrow(() -> new CusException("User not found for the given Id", HttpStatus.NOT_FOUND));
		}else {
			user = userRepo.findById(principle.getUserId())
					.orElseThrow(() -> new CusException(" User not found for the given Id ", HttpStatus.NOT_FOUND));
		}
		UserViewModel userViewModel = userMapper.convertDomainToViewModel(user);
		return userViewModel;
	}

	@Override
	public BaseResponse deleteUser(UserModel userModel) throws Exception {
		User user;
		if(userModel.getUsercode()!=0) {
			user = userRepo.findByUsercode(userModel.getUsercode())
					.orElseThrow(() -> new CusException("User not found for the given Id", HttpStatus.NOT_FOUND));
		}else if(userModel.getUserId()!=null) {
			user = userRepo.findById(userModel.getUserId())
					.orElseThrow(() -> new CusException(" User not found for the given Id ", HttpStatus.NOT_FOUND));
		}else {
			throw new CusException(" Id not Found for User ", HttpStatus.NOT_FOUND);
		}
		user.setStatus(false);
		user.setModifiedDate(LocalDateTime.now());
		user.setModifiedBy(principle.getUserId());
		user = userRepo.save(user);
	//	userRepo.deleteAll();
		return new BaseResponse(user.getUserName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public Page<UserViewModel> getAllUsers(String userName,Pageable pageable) throws Exception {
		Page<User> user = userRepo.findAll(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (userName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("userName"), "%" + userName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<UserViewModel> userModel = userMapper.ConverDomainToModel(user);
	    return userModel;
		
	}
	


	@Override
	public UserModel login(LoginModel loginRequest) throws Exception {
		User user;
		if(loginRequest.getUserName()!=null) {
			user = userRepo.findByUserName(loginRequest.getUserName())
					.orElseThrow(() -> new EntityNotFoundException("User not found"));
		}else {
			throw new CusException("UserName cannot be NULL", HttpStatus.BAD_REQUEST);
		}
		
		boolean isPasswordsame = encoder.matches(loginRequest.getPassword(),user.getPassword() );
		 if (isPasswordsame) {
	            return userMapper.convertDomainToModel(user);
	        } else {
	            throw new CusException("Incorrect password", HttpStatus.UNAUTHORIZED);
	        }
	}

	@Override
	public BaseResponse changePassword(ChangePasswordModel changePasswordModel) throws Exception {
		User user;
		boolean isPasswordsame=false;
		if(changePasswordModel.getUserCode()==null||changePasswordModel.getUserCode().equals(0L)) {
			user = userRepo.findById(principle.getUserId())
					.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		 isPasswordsame = encoder.matches(changePasswordModel.getOldPassword(),user.getPassword() );

		}else {
			user = userRepo.findByUsercode(changePasswordModel.getUserCode())
					.orElseThrow(() -> new EntityNotFoundException("User not found"));
			User admin = userRepo.findById(principle.getUserId())
					.orElseThrow(() -> new EntityNotFoundException("User not found"));
	
			if (admin.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("Admin"))) {
			    isPasswordsame = true;
			} else {
			    isPasswordsame = encoder.matches(changePasswordModel.getOldPassword(), user.getPassword());
			}
			
		}
		 if (isPasswordsame) {
			boolean isSame = changePasswordModel.getNewPassword().equals(changePasswordModel.getConfirmPassword());
			if(isSame) {
				user.setPassword(encoder.encode(changePasswordModel.getNewPassword()));
				userRepo.save(user);
				return new BaseResponse(" Password Changed Successfully!! ", HttpStatus.OK.value());
			}else {
	            throw new CusException(" New password and Confirm password doesnt match ", HttpStatus.UNAUTHORIZED);
	
			}
	        } else {
	            throw new CusException(" Old password is Incorrect ", HttpStatus.UNAUTHORIZED);
	        }
		
	}
	
	@Override
	public BaseResponse forgotPassword(ChangePasswordModel changePasswordModel) throws Exception {
		User user;
		user = userRepo.findByUserName(changePasswordModel.getMail())
				.orElse(null);
		

		if(user!=null&&user.getOtp()!=null&&user.getOtp().equals(changePasswordModel.getOtp())) {
			if (user.getOtpRequestAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
				return new BaseResponse(" OTP Expired ", HttpStatus.BAD_REQUEST.value());
			}
			boolean isSame = changePasswordModel.getNewPassword().equals(changePasswordModel.getConfirmPassword());
			if(isSame) {
				user.setPassword(encoder.encode(changePasswordModel.getNewPassword()));
				userRepo.save(user);
				return new BaseResponse(" Password Changed Successfully!! ", HttpStatus.OK.value());
			}else {
				return new BaseResponse("New password and Confirm password doesnt match ", HttpStatus.BAD_REQUEST.value());	
			}
			
			
		}else {
			return new BaseResponse(" Invalid OTP ", HttpStatus.BAD_REQUEST.value());

		}
			
	}

	@Override
	public UserModel findByUserId(UUID userID) {
		User user=new User();
		user=userRepo.findById(userID).orElse(null);
		UserModel userModel=new UserModel();
		userModel=userMapper.convertDomainToModel(user);
		
		return userModel;
	}

	@Override
	public BaseResponse sendOtp(UserModel userModel) {
		if(userModel.getUserType()==null) {
			User user=userRepo.findByUserName(userModel.getMail()).orElse(null);
			if(user==null) {
				return new BaseResponse("No user found with given mail! ", HttpStatus.BAD_REQUEST.value());
			}
			String otp = emailService.sendOtpForForgotPassword(userModel.getMail());
			user.setOtpRequestAt(LocalDateTime.now());
			user.setOtp(otp);
			userRepo.save(user);
			return new BaseResponse(" OTP sent to registered Email Successfully!! ", HttpStatus.OK.value());

		}else {
			User user=userRepo.findByUserName(userModel.getMail()).orElse(null);
			if(user!=null) {
				return new BaseResponse("User Already exists with given mail! ", HttpStatus.BAD_REQUEST.value());
			}
			
			String otp = emailService.sendOtpForNewUser(userModel.getMail());
			OtpUser otpuser = new OtpUser();
			otpuser = otpUserRepo.findByMail(userModel.getMail());
			if(otpuser!=null) {
				otpUserRepo.deleteById(otpuser.getId());
				otpuser = new OtpUser();
			}
			otpuser.setMail(userModel.getMail());
			otpuser.setOtp(otp);
			otpuser.setRequestAt(LocalDateTime.now());
			otpUserRepo.save(otpuser);
			return new BaseResponse(" OTP sent Successfully, Please check your mail!! ", HttpStatus.OK.value());
		}
	}
	



}
