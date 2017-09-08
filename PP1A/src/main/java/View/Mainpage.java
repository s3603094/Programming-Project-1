package View;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import model.Menu;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public class Mainpage {
	public static Menu menu;
	
	//this class is used to test for Velocity template

	public static void helloWorld(){
		staticFiles.location("/public");		
		menu = new Menu();
		
		//vtl loader
		get("/", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			//"placeholder", "value"
			model.put("template", "/mainpage/mainpage.vtl");
			model.put("table", "/utils/companyTable.vtl");
			model.put("news", "/utils/stockNews.vtl");
			
			// The vtl files are located under the resources directory
			//The line below are required to make the page works
            return new VelocityTemplateEngine().render(
            		new ModelAndView(model, "layout.vtl")
            		);
        });
		
		get("/login", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			model.put("template", "/mainpage/login.vtl");
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "layout.vtl"));
		});
		
		get("/redirectUser", (req, res) -> {
			Map<String, Object> model = testLogin(req);
						
			return new VelocityTemplateEngine().render(new ModelAndView(model, "layout.vtl"));
		});
		
		get("/register", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			model.put("template", "/mainpage/register.vtl");
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "layout.vtl"));
		});
		
		get("/regSuccess", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			String userName = req.queryParams("username");
			String firstName = req.queryParams("firstname");
			String lastName = req.queryParams("lastname");
			int age = Integer.parseInt(req.queryParams("age"));
			String password = req.queryParams("password");
			String confirmPassword = req.queryParams("confirmpassword");
			
			if(menu.register(userName, firstName, lastName, age, password, confirmPassword))
			{
				model.put("template", "user.vtl");
			}
			else
			{
				model.put("template", "/mainpage/register.vtl");
			}
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "layout.vtl"));
		});
		
		get("/admin", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			model.put("userTemplate", "/users/admin.vtl");
			
			model.put("username", "Admin");
			model.put("fname", "Colonel");
			model.put("lname", "Sanders");
			model.put("age", 50);
			model.put("password", "#Admin123");
			
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "users/samplePlayerProfile.vtl"));
		});
		
		//test
		get("/user", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			model.put("userTemplate", "/users/user.vtl");
			
			model.put("username", "John123");
			model.put("fname", "John");
			model.put("lname", "Nguyen");
			model.put("age", 20);
			model.put("password", "#Password123");
			
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "users/samplePlayerProfile.vtl"));
		});
		
		get("/TransactionAccount", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			model.put("userTemplate", "/users/TransactionAccount.vtl");
			model.put("chart", "/utils/chart.vtl");
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "users/samplePlayerProfile.vtl"));
		});
		
		
		//to test table
		get("/testTable", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			
			model.put("template", "/utils/companyTable.vtl");
			
			return new VelocityTemplateEngine().render(new ModelAndView(model, "layout.vtl"));
		});
	}
	
	//funtion for login
	public static Map<String, Object> testLogin(Request req){
		Map<String, Object> model = new HashMap<>();
		
		String username	= req.queryParams("username");
		String password	= req.queryParams("password");
		
		if(menu.login(username, password))
			model.put("template", "user.vtl");
		else
			model.put("template", "login.vtl");
		
		return model;		
	}
	
	//function for user
}
