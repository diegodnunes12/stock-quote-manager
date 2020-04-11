package br.com.inatel.diego.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.inatel.diego.model.Quote;
import br.com.inatel.diego.model.Stock;
import br.com.inatel.diego.repository.IQuote;

@Controller
public class ControllerWeb {
	private IQuote iQuote;
	
	ControllerWeb(IQuote iQuote) {
		this.iQuote = iQuote;
	}
	
	@RequestMapping(value="/home")
	public String getQuotes(Model model) {
		List<Quote> quotes = iQuote.findAll();
		model.addAttribute("quotes", quotes);
		return "index";
	}
	
	@RequestMapping(value="/new")
	public String Salvar(Model model) {
		model.addAttribute("quote", new Quote());
		model.addAttribute("stock", new Stock());
		return "new";
	}
	
	/*
	 * Add new Quote 
	 */
	@PostMapping(value="/saveQuote")
	public String saveQuote(@Valid Quote quote, Model model, RedirectAttributes at) {		
		
		String url="http://192.168.99.102:8080/stock/" + quote.getStock().toLowerCase();
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(url, String.class);
		
		if(resp == null) {
			at.addFlashAttribute("message", "Stock was not found: "  + quote.getStock());
		    return "redirect:/new";
		}
		else {
			Quote qt = iQuote.save(quote);
			
			at.addFlashAttribute("message", "Quote added successfully. Value: " + qt.getValue() + " Date: " + qt.getValue());
		    return "redirect:/home";
		}
	}
	
	/*
	 * Add new Stock
	 */
	@PostMapping(value="/saveStock")
	public String saveStock(@Valid Stock stock, Model model, RedirectAttributes at) {
		
		URL url = null;
		try {
			url = new URL("http://192.168.99.102:8080/stock");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection http = (HttpURLConnection)con;
		try {
			http.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		http.setDoOutput(true);			
		
		String json = "{\"id\":\"" + stock.getId().toLowerCase().trim() + "\",\"description\":\"" + stock.getDescription() + "\"}";
		
		byte[] out = json.getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		try {
			http.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try(OutputStream os = http.getOutputStream()) {
		    os.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		at.addFlashAttribute("messageStock", "Stock added successfully.");
	    return "redirect:/new";
	}
}
