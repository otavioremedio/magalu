package com.magalu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magalu.api.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
