package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.PositionRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Model.Position;
import com.example.Humosoft.Service.PositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("positions")
@RequiredArgsConstructor
public class PositionController {
	private final PositionService positionService;

	// Create: Tạo mới một Position
	@PostMapping
	public Apiresponse<Position> createPosition(@RequestBody PositionRequest positionRequest) {
		Position createdPosition = positionService.create(positionRequest);
		return Apiresponse.<Position>builder().code(201) // Mã trạng thái 201 - Created
				.message("Position created successfully").result(createdPosition).build();
	}

	// Read: Lấy thông tin một Position theo ID
	@GetMapping("/{id}")
	public Apiresponse<Position> getPositionById(@PathVariable Integer id) {
		Position position = positionService.getById(id);
		return Apiresponse.<Position>builder().code(200) // Mã trạng thái 200 - OK
				.message("Position found").result(position).build();
	}

	// Read: Lấy danh sách tất cả các Position
	@GetMapping
	public Apiresponse<List<Position>> getAllPositions() {
		List<Position> positions = positionService.getAll();
		return Apiresponse.<List<Position>>builder().code(200) // Mã trạng thái 200 - OK
				.message("All positions retrieved successfully").result(positions).build();
	}

	// Update: Cập nhật thông tin Position
	@PutMapping("/{id}")
	public Apiresponse<Position> updatePosition(@PathVariable Integer id,
			@RequestBody PositionRequest positionRequest) {
		Position updatedPosition = positionService.update(id, positionRequest);
		return Apiresponse.<Position>builder().code(200) // Mã trạng thái 200 - OK
				.message("Position updated successfully").result(updatedPosition).build();
	}

	// Delete: Xóa Position theo ID
	@DeleteMapping("/{id}")
	public Apiresponse<Void> deletePosition(@PathVariable Integer id) {
		positionService.delete(id);
		return Apiresponse.<Void>builder().code(204) // Mã trạng thái 204 - No Content (khi xóa thành công)
				.message("Position deleted successfully").build();
	}

}
