package com.wp.refcodegen.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wp.refcodegen.constants.Constants;
import com.wp.refcodegen.dto.UserDto;
import com.wp.refcodegen.entity.CustomSequenceTracker;
import com.wp.refcodegen.entity.StateCodes;
import com.wp.refcodegen.entity.User;
import com.wp.refcodegen.exception.InvalidAgentIdException;
import com.wp.refcodegen.exception.RecordNotFoundException;
import com.wp.refcodegen.exception.UserAlreadyExistsException;
import com.wp.refcodegen.repository.CustomSequenceTrackerRepository;
import com.wp.refcodegen.repository.StateCodesRepository;
import com.wp.refcodegen.repository.UserRepository;

@Service
public class RefCodeGenService {
	
	@Autowired
	private CustomSequenceTrackerRepository seqTrackerRepository;
	
	@Autowired
	private StateCodesRepository stateCodesRepository;
	
	@Autowired
	private UserRepository userRepository;

	public UserDto createSalesAgent(UserDto userDto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userDto, User.class);
		user.setRole("SA");
		user.setId(generateSalesAgentId(user.getState()));
		user.setCreatedOn(LocalDate.now());
		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}
	
	public UserDto createProvider(UserDto userDto) {
		validateProvider(userDto);
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userDto, User.class);
		user.setRole("PVDR");
		String[] idAndRefCode = generateProviderIdAndReferralCode(user.getState()).split(" ");
		user.setId(idAndRefCode[0]);
		user.setReferralCode(idAndRefCode[1]);
		user.setCreatedOn(LocalDate.now());
		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}
	
	private String generateSalesAgentId(String state){
		Optional<StateCodes> opStateCode = stateCodesRepository.findById(state.trim().toUpperCase());
		if(opStateCode.isPresent()){
			Optional<CustomSequenceTracker> opSeq = seqTrackerRepository.findById(opStateCode.get().getStateCode());
			if(opSeq.isPresent()){
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append("S");
				strBuffer.append(opStateCode.get().getStateCode());
				strBuffer.append(String.format("%05d", opSeq.get().getSalesAgentSeq()));
				opSeq.get().setSalesAgentSeq(opSeq.get().getSalesAgentSeq()+1);
				seqTrackerRepository.save(opSeq.get());
				return strBuffer.toString();
			}
			throw new RecordNotFoundException("State not found!!");
		}
		throw new RecordNotFoundException("State not found!!");
	}
	
	private String generateProviderIdAndReferralCode(String state){
		Optional<StateCodes> opStateCode = stateCodesRepository.findById(state.trim().toUpperCase());
		if(opStateCode.isPresent()){
			Optional<CustomSequenceTracker> opSeq = seqTrackerRepository.findById(opStateCode.get().getStateCode());
			if(opSeq.isPresent()){
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append("P");
				strBuffer.append(opStateCode.get().getStateCode());
				strBuffer.append(String.format("%05d", opSeq.get().getProviderSeq()));
				opSeq.get().setProviderSeq(opSeq.get().getProviderSeq()+1);
				seqTrackerRepository.save(opSeq.get());
				String referralCode = Constants.REF_CODE_PREFIX+strBuffer.substring(1);
				strBuffer.append(" ");
				strBuffer.append(referralCode);
				return strBuffer.toString();
			}
			throw new RecordNotFoundException("State not found!!");
		}
		throw new RecordNotFoundException("State not found!!");
	}
	
	private void validateProvider(UserDto userDto){
		Optional<User> opUser = userRepository.findByContactNoAndEmailId(userDto.getContactNo(),userDto.getEmailId());
		if(opUser.isPresent()){
			throw new UserAlreadyExistsException("Provider is already linked!!");
		}
		else{
			Optional<User> opUser1 = userRepository.findById(userDto.getLinkedTo());
			if(!opUser1.isPresent()){
				throw new InvalidAgentIdException("Sales Agent Id is Invalid");
			}
		}
	}

	public List<UserDto> fetchAllProvidersByAgentId(String agentId) {
		List<User> list = userRepository.findByLinkedTo(agentId);
		ModelMapper mapper = new ModelMapper();
		List<UserDto> listDto= list.parallelStream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
		return listDto;
	}

	public UserDto updateProviderByProviderId(UserDto userDto) {
		Optional<User> opUser = userRepository.findById(userDto.getId());
		if(opUser.isPresent()){
			ModelMapper mapper = new ModelMapper();
			userRepository.save(mapper.map(userDto, User.class));
			return userDto;
		}
		else{
			throw new RecordNotFoundException("Provider with id: "+userDto.getId()+" not found!!");
		}
	}

	public UserDto fetchProviderById(String providerId) {
		Optional<User> opProvider = userRepository.findById(providerId);
		if(opProvider.isPresent()){
			ModelMapper mapper = new ModelMapper();
			return mapper.map(opProvider.get(), UserDto.class);
		}
		else{
			throw new RecordNotFoundException("Provider Id: "+providerId+" doesn't exists!!");
		}
	}

}
