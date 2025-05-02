package com.practice.mysql.bookapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import com.practice.mysql.bookapi.dto.UserDto;
import com.practice.mysql.bookapi.dto.UserReservationsResponse;
import com.practice.mysql.bookapi.dto.UserResponse;
import com.practice.mysql.bookapi.dto.JWTAuthResponse;
import com.practice.mysql.bookapi.dto.LoginDto;
import com.practice.mysql.bookapi.dto.SignupDto;
import com.practice.mysql.bookapi.exception.UserNotFoundException;
import com.practice.mysql.bookapi.mapper.UserMapper;
import com.practice.mysql.bookapi.model.User;
import com.practice.mysql.bookapi.model.Role;
import com.practice.mysql.bookapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.practice.mysql.bookapi.security.JwtTokenProvider;
import com.practice.mysql.bookapi.service.UserService;
import com.practice.mysql.bookapi.service.RoleService;

import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// https://stackoverflow.com/questions/62452666/error-creating-bean-in-spring-constructor-threw-exception-nested-exception-is
	@PostConstruct
	public void init() {

		if (!roleService.isExist("Admin")) {
			Role role1 = new Role();
			role1.setDescription("Admin role");
			role1.setName("ADMIN");
			addDefaultRole(role1);
		}

		if (!roleService.isExist("EMPLOYEE")) {
			Role role2 = new Role();
			role2.setDescription("Employee role");
			role2.setName("EMPLOYEE");
			addDefaultRole(role2);
		}

		if (!roleService.isExist("USER")) {
			Role role3 = new Role();
			role3.setDescription("User role");
			role3.setName("USER");
			addDefaultRole(role3);
		}

	}

	@Override
	public JWTAuthResponse login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getEmail(), loginDto.getPassword()));

		System.out.println(" loginDto " + loginDto.getEmail());

		System.out.println(" login authentication " + authentication);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

		Map<String, String> token = jwtTokenProvider.generateToken(authentication);

		User user = userRepository.findByEmail(loginDto.getEmail());

		if (user == null) {
			throw new UserNotFoundException("user could not be found");
		}

		jwtAuthResponse.setAccessToken(token.get("accessToken"));
		jwtAuthResponse.setRefreshToken(token.get("refreshToken"));
		jwtAuthResponse.setUserId(Integer.toString(user.getId()));

		jwtAuthResponse.setMessage("login success.");

		return jwtAuthResponse;
	}

	// https://stackoverflow.com/questions/72845608/password-is-not-getting-encoded-in-spring-security
	@Override
	public UserDto signup(SignupDto signupDto) {
		// add check for username exists in database
		// https://stackoverflow.com/questions/16232833/how-to-respond-with-an-http-400-error-in-a-spring-mvc-responsebody-method-retur
		if (userRepository.existsByUsername(signupDto.getUsername())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}

		// add check for email exists in database
		if (userRepository.existsByEmail(signupDto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}

		// Set default role as USER
		Role role = roleService.findByName("USER");
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);

		// If email domain is admin.edu, add ADMIN role
		if (signupDto.getEmail().split("@")[1].equals("admin.edu")) {
			role = roleService.findByName("ADMIN");
			roleSet.add(role);
		}

		// EMPLOYEE
		// If email domain is employee.edu, add ADMIN role
		if (signupDto.getEmail().split("@")[1].equals("employee.edu")) {
			role = roleService.findByName("EMPLOYEE");
			roleSet.add(role);
		}

		User user = new User();
		user.setUsername(signupDto.getUsername());
		user.setEmail(signupDto.getEmail());

		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		user.setRoles(roleSet);

		User newUser = userRepository.save(user);
		return UserMapper.mapToDto(newUser);

	}

	@Override
	public UserResponse getAllUsers(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<User> users = userRepository.findAll(pageable);
		List<User> listOfCustomers = users.getContent();
		List<UserDto> content = listOfCustomers.stream().map(UserMapper::mapToDto).collect(Collectors.toList());

		UserResponse userResponse = new UserResponse();
		userResponse.setContent(content);
		userResponse.setPageNo(users.getNumber());
		userResponse.setPageSize(users.getSize());
		userResponse.setTotalElements(users.getTotalElements());
		userResponse.setTotalPages(users.getTotalPages());
		userResponse.setLast(users.isLast());

		return userResponse;
	}

	@Override
	public UserDto getUserById(int id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user could not be found"));
		return UserMapper.mapToDto(user);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user could not be found"));
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());

		User updateUser = userRepository.save(user);

		return UserMapper.mapToDto(updateUser);
	}

	@Override
	public void deleteUserById(int id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user could not be found"));
		userRepository.delete(user);
	}

	@Override
	public UserReservationsResponse getUserReservations(int id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user could not be found"));
		return UserMapper.mapToUserReservationsResponse(user);
	}

	private Role addDefaultRole(Role role) {
		return roleService.AddRole(role);
	}

}
