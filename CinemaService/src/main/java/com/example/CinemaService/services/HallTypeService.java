package com.example.CinemaService.services;

import com.example.CinemaService.dto.HallTypeRequestDTO;
import com.example.CinemaService.dto.HallTypeResponseDTO;
import com.example.CinemaService.mappers.HallTypeMapper;
import com.example.CinemaService.models.HallType;
import com.example.CinemaService.repos.HallTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HallTypeService {
    private final HallTypeRepository hallTypeRepository;
    private final HallTypeMapper hallTypeMapper;

    public List<HallTypeResponseDTO> findAll(){
        return hallTypeRepository.findAll().stream().map(hallTypeMapper::toDto).toList();
    }

    public void save(HallTypeRequestDTO dto){
        if(hallTypeRepository.findByTitle(dto.title()).isPresent()) throw new RuntimeException("Такой тип зала уже есть");
        hallTypeRepository.save(hallTypeMapper.toEntity(dto));
    }

    public void update(HallTypeRequestDTO dto, long hallTypeId){
        if(hallTypeRepository.findByTitle(dto.title()).isPresent()) throw new RuntimeException("Такой тип зала уже есть");
        HallType hallType = hallTypeRepository.findById(hallTypeId).
                orElseThrow(() -> new RuntimeException("Не найден тип зала с id " + hallTypeId));
        hallTypeMapper.update(dto, hallType);
        hallTypeRepository.save(hallType);
    }

    public void deleteById(long hallTypeId){
        hallTypeRepository.deleteById(hallTypeId);
    }
}
