package com.jgmonteiro.projetofullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jgmonteiro.projetofullstack.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
