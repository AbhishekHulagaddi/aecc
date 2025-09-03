package com.rim.auth.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

import com.rim.auth.domain.Roles;
import com.rim.auth.domain.User;
import com.rim.auth.mapper.RoleMapper;
import com.rim.auth.mapper.UserMapper;
import com.rim.auth.model.ChangePasswordModel;
import com.rim.auth.model.LoginModel;
import com.rim.auth.model.RoleModel;
import com.rim.auth.model.RoleViewModel;
import com.rim.auth.model.UserCreateModel;
import com.rim.auth.model.UserModel;
import com.rim.auth.model.UserViewModel;
import com.rim.auth.repo.RoleRepo;
import com.rim.auth.repo.UserRepo;
import com.rim.auth.service.RoleService;
import com.rim.auth.service.UserService;
import com.rim.auth.utils.IDGeneration;
import com.rim.auth.utils.UserIdPrinciple;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;

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
				throw new CusException(" Already user exist with given mailId ",HttpStatus.BAD_REQUEST);
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
		return new BaseResponse(userViewModel.getUserName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
		}else {
			throw new CusException(" User Already exist with given USERNAME ", HttpStatus.BAD_REQUEST);
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
		user.setMail(userModel.getMail());
		user.setMobileNumber(userModel.getMobileNumber());
		user.setUserName(userModel.getUserName());
		user.setStatus(true);
		user.setModifiedDate(LocalDateTime.now());
		user.setModifiedBy(principle.getUserId());
		user = userRepo.save(user);
		return new BaseResponse(user.getUserName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public UserViewModel getUserByUserCode(long usercode) throws Exception {
		User user;
		if(usercode!=0) {
			user = userRepo.findByUsercode(usercode)
					.orElseThrow(() -> new CusException("User not found for the given Id", HttpStatus.NOT_FOUND));
		}else {
			throw new CusException(" Usercode not Found for User ",HttpStatus.NOT_FOUND);
		}
		UserViewModel userViewModel = userMapper.convertDomainToViewModel(user);
		return userViewModel;
	}

	@Override
	public BaseResponse deleteUser(UserModel userModel) throws Exception {
		User user;
		if(userModel.getUserId()!=null) {
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
		if(changePasswordModel.getUserName()!=null) {
			user = userRepo.findByUserName(changePasswordModel.getUserName())
					.orElseThrow(() -> new EntityNotFoundException("User not found"));
		}else {
			throw new CusException("UserName cannot be NULL", HttpStatus.BAD_REQUEST);
		}
		
		boolean isPasswordsame = encoder.matches(changePasswordModel.getOldPassword(),user.getPassword() );

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
	



}
