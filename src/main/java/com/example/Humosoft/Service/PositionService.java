package com.example.Humosoft.Service;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.PositionRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.PositionMapper;
import com.example.Humosoft.Model.Position;
import com.example.Humosoft.Repository.PositionRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

	private final PositionMapper positionMapper;
	private final PositionRepository positionRepository;

	// Create operation
	public Position create(PositionRequest request) {
		Position position = positionMapper.toPosition(request);
		if(positionRepository.existsByPositionName(request.getPositionName())) {
			throw new WebErrorConfig(ErrorCode.POSITION_ALREADY_EXISTS);
		}
		
		return positionRepository.save(position);
	}

	// Read operation: Find a position by ID
	public Position getById(Integer id) {
		Optional<Position> position = positionRepository.findById(id);
		return position.orElseThrow(() -> new WebErrorConfig(ErrorCode.POSITION_NOT_FOUND));
	}

	// Read operation: Get all positions
	public List<Position> getAll() {
	    return positionRepository.findByDeletedFalse();
	}
	// Update operation
	public Position update(Integer id, PositionRequest request) {

		// Map updated fields from request
		Position p = positionMapper.updatePosition(id, request);
		return positionRepository.save(p);
	}

	// Delete operation
	public void delete(Integer id) {
		 Position existingPosition = getById(id);
		    existingPosition.setDeleted(true);
		    positionRepository.save(existingPosition);
		
	}
}
