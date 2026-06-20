package com.qht.crm.whatsapp.controller;

import static com.qht.crm.controller.ApiMapping.WHATS_APP_TEMPLATE_REST_URL;
import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qht.crm.entity.Employee;
import com.qht.crm.entity.Product;
import com.qht.crm.repository.EmployeeRepository;
import com.qht.crm.repository.ProductRepository;
import com.qht.crm.security.jwt.JwtConfiguration;
import com.qht.crm.security.jwt.JwtVerify;
import com.qht.crm.whatsapp.dto.WhatsAppPhoneNumberTemplateDto;
import com.qht.crm.whatsapp.entity.WhatsAppPhoneNumber;
import com.qht.crm.whatsapp.requests.WhatsAppMainControllerRequest;
import com.qht.crm.whatsapp.service.WhatsAppPhoneNumberService;
import com.qht.crm.whatsapp.service.WhatsAppPhoneNumberTemplatesService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces="application/json", path = WHATS_APP_TEMPLATE_REST_URL)
@AllArgsConstructor
@CrossOrigin(origins="*")
public class WhatsAppPhoneNumberTemplatesController {

	private final WhatsAppPhoneNumberTemplatesService whatsAppPhoneNumberTemplatesService; 
	private final WhatsAppPhoneNumberService whatsAppPhoneNumberService;
	private final JwtConfiguration jwtConfiguration;
	private final EmployeeRepository employeeRepository;
	private final ProductRepository productRepository;
	private final SecretKey secretKey;
	    
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public ResponseEntity<Boolean> create(@RequestBody WhatsAppPhoneNumberTemplateDto whatsAppPhoneNumberTemplateDto,@RequestHeader (name="Authorization") String token){
		Boolean toReturn = true;
		
		token = token.replace(jwtConfiguration.getTokenPrefix(), "");
    	//System.out.println(token);
    	
    	Employee employee= new JwtVerify().verifyTokenOrThrowError(token, secretKey, employeeRepository);
    	
    	if(employee.getOrganization().trim().equals(whatsAppPhoneNumberTemplateDto.getOrganization().trim()))
    	{
    		Product product = null;
    		if(whatsAppPhoneNumberTemplateDto.getProductId()!=null) {
    			product = productRepository.getOne(whatsAppPhoneNumberTemplateDto.getProductId());
    		}
    		whatsAppPhoneNumberTemplatesService.create(product,whatsAppPhoneNumberTemplateDto);
    		toReturn= true;
    		return status(HttpStatus.OK).body(toReturn);
    	}
    	else
    	{
    		//System.out.println("I am in else controller");
    		
    		return status(HttpStatus.UNAUTHORIZED).body(toReturn);
    	} 
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public ResponseEntity<Boolean> update(@RequestBody WhatsAppPhoneNumberTemplateDto whatsAppPhoneNumberTemplateDto,@RequestHeader (name="Authorization") String token){
		Boolean toReturn = true;
		
		token = token.replace(jwtConfiguration.getTokenPrefix(), "");
    	//System.out.println(token);
    	
    	Employee employee= new JwtVerify().verifyTokenOrThrowError(token, secretKey, employeeRepository);
    	
    	if(employee.getOrganization().trim().equals(whatsAppPhoneNumberTemplateDto.getOrganization().trim()))
    	{
    		toReturn= whatsAppPhoneNumberTemplatesService.update(whatsAppPhoneNumberTemplateDto);
    		return status(HttpStatus.OK).body(toReturn);
    	}
    	else
    	{
    		//System.out.println("I am in else controller");
    		
    		return status(HttpStatus.UNAUTHORIZED).body(toReturn);
    	} 
	}
	
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public ResponseEntity<Boolean> delete(@RequestParam Long id,@RequestParam String organization,@RequestHeader (name="Authorization") String token){
		Boolean toReturn = true;
		
		token = token.replace(jwtConfiguration.getTokenPrefix(), "");
    	//System.out.println(token);
    	
    	Employee employee= new JwtVerify().verifyTokenOrThrowError(token, secretKey, employeeRepository);
    	
    	if(employee.getOrganization().trim().equals(organization))
    	{
    		toReturn= whatsAppPhoneNumberTemplatesService.delete(id);
    		return status(HttpStatus.OK).body(toReturn);
    	}
    	else
    	{
    		//System.out.println("I am in else controller");
    		
    		return status(HttpStatus.UNAUTHORIZED).body(toReturn);
    	} 
	}
	
    @GetMapping("/getAllByOrganization")
	@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public ResponseEntity<List<WhatsAppPhoneNumberTemplateDto>> getAllByOrganization(@RequestParam String organization,@RequestHeader (name="Authorization") String token){
	        
		List<WhatsAppPhoneNumberTemplateDto> toReturn = null;
		
    	token = token.replace(jwtConfiguration.getTokenPrefix(), "");
    	
    	//System.out.println(token);
    	
    	Employee employee= new JwtVerify().verifyTokenOrThrowError(token, secretKey, employeeRepository);
    	
    	if(employee.getOrganization().trim().equals(organization.trim()))
    	{
    		toReturn= whatsAppPhoneNumberTemplatesService.findAllByOrganization(organization);
    		return status(HttpStatus.OK).body(toReturn);
    	}
    	else
    	{
    		//System.out.println("I am in else controller");
    		
    		return status(HttpStatus.UNAUTHORIZED).body(toReturn);
    	} 	
	}
    
    @PostMapping("/getAllByOrganizationAndWhatsAppPhoneNumber")
   	@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
   	public ResponseEntity<List<WhatsAppPhoneNumberTemplateDto>> getAllByOrganizationAndWhatsAppPhoneNumber(@RequestBody WhatsAppMainControllerRequest whatsAppControllerRequest,@RequestHeader (name="Authorization") String token){
   	        
   		List<WhatsAppPhoneNumberTemplateDto> toReturn = null;
   		
       	token = token.replace(jwtConfiguration.getTokenPrefix(), "");
       	
       	//System.out.println(token);
       	
       	Employee employee= new JwtVerify().verifyTokenOrThrowError(token, secretKey, employeeRepository);
       	
       	if(employee.getOrganization().trim().equals(whatsAppControllerRequest.getOrganization().trim()))
       	{
       		WhatsAppPhoneNumber whatsAppPhoneNumber = whatsAppPhoneNumberService.findByPhoneNumber(whatsAppControllerRequest.getPhoneNumber());
       		
       		if(whatsAppPhoneNumber != null)
       		toReturn= whatsAppPhoneNumberTemplatesService.getAllByOrganizationAndWhatsAppPhoneNumber(whatsAppControllerRequest.getOrganization(),whatsAppPhoneNumber);
       		else 
       		return status(HttpStatus.NO_CONTENT).body(toReturn);
       		
       		return status(HttpStatus.OK).body(toReturn);
       	}
       	else
       	{
       		//System.out.println("I am in else controller");
       		
       		return status(HttpStatus.UNAUTHORIZED).body(toReturn);
       	} 	
   	}
    
}
