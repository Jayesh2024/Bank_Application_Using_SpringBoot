package com.ts.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.model.Account;
import com.ts.model.User;
import com.ts.repository.AccountRepository;
import com.ts.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountRepository accRepository;
	
	


	public String registerAcco(User user) {
//	Checking same details in database to avoid same registration
		Optional<User> usr = userRepository.findByAadharOrPanOrEmail(user.getAadhar(), user.getPan(), user.getEmail());
		if(usr.isPresent()) {
			return " Please enter Your own details, this data already exits";
		}
		
		Account acc = new Account();
		Random rand = new Random();	
		User u = null;
		
//		set no unique number
		boolean uniqAccNo = false;
		
		while(!uniqAccNo) {
//			generating unique account no.
			Long accNo = rand.nextLong(10000);
//			check random number available or not
			Optional<Account> exitingAcc = accRepository.findById(accNo);
//			if is not present ->
			if(!exitingAcc.isPresent()) {
				uniqAccNo = true; // unique AccNo found then ->
				u = userRepository.save(user); // save new user details
				acc.setAccountNo(accNo);		// and create new account
				acc.setAccType("Saving");
				acc.setBalance(0);
				acc.setUserId(u);
			}else {
				return "Sorry, We can't create your account, unable to generate unique account number"
						+ " for your Id !!!!!!";
			}
		}
		accRepository.save(acc);	//Saving new account
		
		return "Congragulation "+ u.getName() +", your account created Succesfully and your Account No is: "+acc.getAccountNo();
	}
	

	public String moneyDeposit(Long accNo, String email, int depoAmmount) {
		
		Optional<Account> accNum = accRepository.findByAccountNo(accNo);
		Optional<User> usrEmail = userRepository.findByEmail(email);
		
		if(!accNum.isPresent() || !usrEmail.isPresent()) {
			return "Account number or email Id invalid, please check it.....";
		}
//		get all data regarding accNum And user email from database
		Account acc = accNum.get();
		User usr = usrEmail.get();
		
		int newBalance,  oldBalance = acc.getBalance();
		
		if(depoAmmount <= 0) {
			return "Please, Enter deposit amount greater than zero";
		}else {
			newBalance = oldBalance +  depoAmmount;
			acc.setBalance(newBalance);
		}
		
		accRepository.save(acc);
		
		return  usr.getName() +" in your account " +depoAmmount+ " Rs is credited, your current balance is "+newBalance+" Rs.";
	}
	
	
	public String moneyWithdraw(Long accNo, String email, int withdrAmmount) {
		
		Optional<Account> accNum = accRepository.findByAccountNo(accNo);
		Optional<User> usr = userRepository.findByEmail(email);
		
		if(!accNum.isPresent() || !usr.isPresent()) {
			return "Please enter valid account number or email Id....!!!!!!!";
		}
		
//		get account object 
		Account acc = accNum.get();		
		
		int newBalance, oldBalance = acc.getBalance();;
		
		if(withdrAmmount > oldBalance) {
			return "Insufficient Balance....";
		}else if(withdrAmmount <= 0) {
			return "Ammount not be less than Zero.....";
		}else {
			newBalance = oldBalance - withdrAmmount;
			acc.setBalance(oldBalance);
		}
		
		accRepository.save(acc);
		
		return acc.getUserId().getName() +" from your account " +withdrAmmount+ " Rs has been withdrawed, your current balance is "+newBalance+" Rs.";
	}
	
	public String transaction(Long fromAccount, Long toAccount, int transfAmmount) {
		Optional<Account> fromAccNo = accRepository.findByAccountNo(fromAccount);
		Optional<Account> toAccNo = accRepository.findByAccountNo(toAccount);
		
		if(!fromAccNo.isPresent() || !toAccNo.isPresent()) {
			return "Please enter valid Account Number to transfer money.....";
		}
		
		Account acc = fromAccNo.get();
		Account acc1 = toAccNo.get();
		
		int oldBalance = acc.getBalance(), newBalance = acc1.getBalance();
		if(transfAmmount <= 0) {
			return "Please enter ammount greater than zero...!!!!!";
		}else if(transfAmmount > oldBalance) {
			return "Insufficient Balance.....";
		}else {
			acc1.setBalance(newBalance + transfAmmount);
			acc.setBalance(oldBalance - transfAmmount);
		}
		
		accRepository.save(acc);
		accRepository.save(acc1);

		return acc.getUserId().getName() + " from your account "+transfAmmount+ " Rs has been debited and transfer to "
				+acc1.getUserId().getName() +"'s account, your current balance is "+ acc.getBalance()+" Rs.";
		}
		
	
	public String checkBalance(Long AccNo, String email) {
		Optional<Account> accNum = accRepository.findByAccountNo(AccNo);
		Optional<User> usr = userRepository.findByEmail(email);
		
		if(!accNum.isPresent() || !usr.isPresent()) {
			return "Invalid Email Id or Account Number, please enter currect details....";
		}
		
		Account acc = accNum.get();
		
		accRepository.save(acc);
		
		return acc.getUserId().getName() +" your current balance is " +acc.getBalance()+" Rs.";
	}
	
	
	public String delAccount(Long AccNo, String email) {
		Optional<Account> accNum = accRepository.findByAccountNo(AccNo);
		Optional<User> usr = userRepository.findByEmail(email);
		
		if(!accNum.isPresent() || !usr.isPresent()) {
			return "Invalid Email Id or Account Number, please enter currect details....";
		}
		
//		get object of user and account by accNum and usrEmail and delete it.
		accRepository.delete(accNum.get());
		userRepository.delete(usr.get());
		
		return usr.get().getName() +" your account has been deleted.....";
	}

}

