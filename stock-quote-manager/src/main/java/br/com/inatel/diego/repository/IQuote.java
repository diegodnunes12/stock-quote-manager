package br.com.inatel.diego.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.diego.model.Quote;

public interface IQuote extends JpaRepository<Quote, Integer>{

}
