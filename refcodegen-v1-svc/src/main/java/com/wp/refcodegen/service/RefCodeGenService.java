package com.wp.refcodegen.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wp.refcodegen.constants.Constants;
import com.wp.refcodegen.dto.UserDto;
import com.wp.refcodegen.entity.CustomSequenceTracker;
import com.wp.refcodegen.entity.StateCodes;
import com.wp.refcodegen.entity.User;
import com.wp.refcodegen.exception.InvalidStateException;
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
		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}
	
	public UserDto createProvider(UserDto userDto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userDto, User.class);
		user.setRole("PVDR");
		String[] idAndRefCode = generateProviderIdAndReferralCode(user.getState()).split(" ");
		user.setId(idAndRefCode[0]);
		user.setReferralCode(idAndRefCode[1]);
		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}
	
	private String generateSalesAgentId(String state){
		Optional<StateCodes> opStateCode = stateCodesRepository.findById(state.trim().toUpperCase());
		if(opStateCode.isPresent()){
			Optional<CustomSequenceTracker> opSeq = seqTrackerRepository.findById(opStateCode.get().getStateCode());
			if(opSeq.isPresent()){
				String saId = "S"+opStateCode.get().getStateCode()+opSeq.get().getSalesAgentSeq();
				opSeq.get().setSalesAgentSeq(opSeq.get().getSalesAgentSeq()+1);
				seqTrackerRepository.save(opSeq.get());
				return saId;
			}
			throw new InvalidStateException("State is Invalid!!");
		}
		throw new InvalidStateException("State is Invalid!!");
	}
	
	private String generateProviderIdAndReferralCode(String state){
		Optional<StateCodes> opStateCode = stateCodesRepository.findById(state.trim().toUpperCase());
		if(opStateCode.isPresent()){
			Optional<CustomSequenceTracker> opSeq = seqTrackerRepository.findById(opStateCode.get().getStateCode());
			if(opSeq.isPresent()){
				String saId = "P"+opStateCode.get().getStateCode()+opSeq.get().getProviderSeq();
				opSeq.get().setProviderSeq(opSeq.get().getProviderSeq()+1);
				seqTrackerRepository.save(opSeq.get());
				String referralCode = Constants.REF_CODE_PREFIX+saId.substring(1);
				return saId+" "+referralCode;
			}
			throw new InvalidStateException("State is Invalid!!");
		}
		throw new InvalidStateException("State is Invalid!!");
	}

}
