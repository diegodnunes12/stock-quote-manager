package br.com.inatel.diego.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import br.com.inatel.diego.model.Quote;
import br.com.inatel.diego.repository.IQuote;


@RestController
@RequestMapping({"/stock-quote-manager"})
public class ControllerApi {
	
	private IQuote iQuote;
	
	ControllerApi(IQuote iQuote) {
		this.iQuote = iQuote;
	}
	
	@GetMapping
	public List findAll(){
	  return iQuote.findAll();
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Quote> findById(@PathVariable int id){
	  return iQuote.findById(id)
	          .map(record -> ResponseEntity.ok().body(record))
	          .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public String create(@RequestBody Quote quote){
		
		String url="http://192.168.99.102:8080/stock/" + quote.getStock().toLowerCase();
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(url, String.class);
		
		if(resp == null) {
			return "Stock was not found: " + quote.getStock();
		}
		else {
			Quote qt = iQuote.save(quote);
			
			return "Quota added successfully. Value: " + qt.getValue() + " Date: " + qt.getValue();
		}
	}
}
