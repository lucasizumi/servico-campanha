package com.challenge.campanha.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.campanha.dto.CampanhaDto;
import com.challenge.campanha.model.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

	@Query(" SELECT new com.challenge.dto.CampanhaDto (campanha.id, campanha.nome, time.nome, campanha.dataInicioVigencia, campanha.dataFimVigencia) " +
            "FROM Campanha campanha " +
            "JOIN campanha.time time " +
            "WHERE campanha.dataFimVigencia >= :dataFim " +
            "ORDER BY campanha.nome, campanha.dataFimVigencia DESC")
    List<CampanhaDto> buscarTodasCampanhasVigentes(@Param("dataFim") LocalDate dataFim);
	
	@Query("SELECT campanha " +
            "FROM Campanha campanha WHERE " +
            "campanha.dataInicioVigencia >= :dataInicio AND " +
            "campanha.dataFimVigencia <= :dataFim ")
    List<Campanha> buscarCampanhasPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
	
	@Query(" SELECT new com.challenge.dto.CampanhaDto (campanha.id, campanha.nome, time.nome, campanha.dataInicioVigencia, campanha.dataFimVigencia) " +
            "FROM Campanha campanha " +
            "JOIN campanha.time time " +
            "WHERE campanha.dataFimVigencia >= :dataFim " +
            "AND time.nome = :time " +
            "ORDER BY campanha.nome, campanha.dataFimVigencia DESC")
    List<CampanhaDto> buscarCampanhasVigentesPorTime(@Param("time") String time, @Param("dataFim") LocalDate dataFim);
	
}
