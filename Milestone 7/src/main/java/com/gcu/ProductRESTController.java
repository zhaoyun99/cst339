package com.gcu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.ProductBusinessInterface;

import com.gcu.model.ProductModel;



@RestController
@RequestMapping("/api/v1/products")
public class ProductRESTController {

	@Autowired
	private ProductBusinessInterface productsService;
	
	@GetMapping("/")
	public List <ProductModel> getProduct()
	{
		return productsService.getProduct();
	}
	
	
	@GetMapping("/{id}")
	public ProductModel getById(@PathVariable(name="id") int id) {
		return productsService.getById(id);
	}
}
