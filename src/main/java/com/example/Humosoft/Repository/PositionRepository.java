package com.example.Humosoft.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
	boolean existsByPositionName(String positionName);
	Optional<Position>  findByPositionName(String positionName);
	List<Position> findByDeletedFalse();

}
