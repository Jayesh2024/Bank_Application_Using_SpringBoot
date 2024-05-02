package com.ts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ts.model.User;

import com.ts.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/msg")
	public String hello() {
		System.out.println("Hyyyyyy");
		return "Hello World";
	}
	
	@PostMapping("/reg-acc")
	public String registerAcc(@RequestBody User user) {
		return userService.registerAcco(user);
	}
	

	@PutMapping("/deposit")
	public String deposit(@RequestParam("accNo") Long accNu, 
							@RequestParam("email") String email,
							@RequestParam("ammount") int depoAmmo) {
		return userService.moneyDeposit(accNu, email, depoAmmo);
	}
	
	@PutMapping("/withdraw")
	public String withdraw(@RequestParam("accNo") Long accNu, 
							@RequestParam("email") String email,
							@RequestParam("ammount") int witdrAmmo) {
	return userService.moneyWithdraw(accNu, email, witdrAmmo);
	}
	
	@PutMapping("/transferAmmo")
	public String moneyTransfer(@RequestParam("fromAccount") Long accNum,
								@RequestParam("toAccount") Long accNumm,
								@RequestParam("ammount") int TransfAmmount) {
		return userService.transaction(accNum, accNumm, TransfAmmount);
	}
	
	
	@GetMapping("/checkBalance")
	public String checkBalance(@RequestParam("accNo") Long accNum,
								@RequestParam("email") String email) {
		
		return userService.checkBalance(accNum, email);
	}
	
	@DeleteMapping("delAccount")
	public String deleteAccount(@RequestParam("accNo") Long accNum,
								@RequestParam("email") String email) {
		
		return userService.delAccount(accNum, email);
	}
}
