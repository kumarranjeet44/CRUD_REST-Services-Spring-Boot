package com.example.demo.Controller;

//if i have fron controller then i dont need presentational layer to send and view the server side data. post man will do all for us.
//this is how we will build RESTFUll services.
//we are making our mathod as services and after converting our method into the services then it can be accessed and process by anyone with fron end application.....
//and this serivs is called rest api.
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;
import com.example.demo.RepoDAO.UserDAO;
//now my application behave like REST services means http method + url
//Rest Services=Http method + URL;

//from the post man tool if we are sendig the data to server then it must send the data in JSON format bodyrequest
@RestController
public class MyController {
	
	@Autowired
	UserDAO dao;
	
	//@RequestMappling-->it not http method here is contain only url but in rest services it contin both http method and url
		
	@PostMapping("/register")//perform register operation using post method
	//get only one user information as json format from the postman tool
	public String register(@RequestBody User u) {
		//this method is now act like service so it is a service method whith url + http method(Post method)=REST API
		dao.save(u);
		return "Information has been saved successfully in database";
	}
	
	@PostMapping("/registerUsers")//perform register operation using post method
	//get more than one user information as json format from the postman tool
	public String registerUsers(@RequestBody ArrayList<User> U){
		for(User u:U) {
			dao.save(u);
		}
		
		return "All Information has been saved successfully in database";
	}
	
	@GetMapping("/search")//perform read operation using get method
	public User search(int id,String name) {//here we are returning the list of user with same name;
		System.out.println(name);//now go to the postman to see how query parameter is beig sent from there.
		User u=dao.findUserById(id);
		if(u!=null) {
			return u;
		}
		return u;		
	}
	
	@GetMapping("/findAll")
		public List<User> findAllUser(Model m) {
	     List<User> u=dao.findAllUsers();
	     if(u.size()!=0) {
	    	 m.addAttribute("Users", u);
				return u;
	     }
	    return u;
		}
	
	@PutMapping("/update")//update the information present in server using put method by sending new data as json format from postman tool
	public String update(@RequestBody User U) {
		int id=(int)U.getId();
		User u=dao.findUserById(id);
		if(u!=null) {
		//u.setUname(uname); we cannot do update the database,becaues using this only object content will change not database 
		//u.setEmail(email);-->this step will not update the information in database;			
		//use transactional query to update the database;
			dao.updateUser(U.getUname(),U.getEmail(),id);
			
			return "Information has been updated and saved into the database";
		}
		return "Information you are trying to change doesnot exist";
			
	}
	
	@DeleteMapping("/delete")//delete the user by id using delete method--->send the data from the post as query parametere or url parameter
	public String delete(int id) {
		User u=dao.findUserById(id);
		if(u!=null) {
			dao.delete(u);
			return "User has been removed from database";
		}
		return "No record found for the user you trying to remove";
		
	}
	

}
