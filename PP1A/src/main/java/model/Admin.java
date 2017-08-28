package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{
	
	private String user_ID;
	public Admin(String user_ID, String password, String firstName, String lastName, int age) {
		super(user_ID, password, firstName, lastName, age);
		this.user_ID = user_ID;
	}

	//Search file for a matching substring and returns arrayList
	public ArrayList<Player> searchPlayer(String user_ID){
		
		ArrayList<Player> matchingPl = new ArrayList<Player>();
		Player searchedPlayer = null;
		String[] splitString = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(FileTools.USER_DATA_FILE));
			String readLine = br.readLine();
			
			while (readLine != null)
			{
				splitString = readLine.split(",");
				if (splitString[0].contains(user_ID) && !splitString[0].equals("user_ID"))
				{
					searchedPlayer = new Player(splitString[0], splitString[1], splitString[2], splitString[3], Integer.parseInt(splitString[4]));
					//adding matching players to the list
					matchingPl.add(searchedPlayer);
				}
				readLine = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return matchingPl;
	}
	
	//Search file for all players adds each player into array list as Player object. Return Arraylist of Player
	public ArrayList<Player> viewAllPlayers(){
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		Player player = null;
		String[] splitString;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(FileTools.USER_DATA_FILE));
			String readLine = br.readLine();
			
			while (readLine != null)
			{
				splitString = readLine.split(",");
				if (splitString[0].equals(user_ID) && !splitString[0].equals("user_ID"))
				{
					//assigning value to the string array and adding to List
					player = new Player(splitString[0], splitString[1], splitString[2], splitString[3], Integer.parseInt(splitString[4]));
					allPlayers.add(player);
				}
				readLine = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return allPlayers;
	}
	
	public void delUser(String user_ID) throws IOException{
		FileTools ft = new FileTools();
		
		List<String[]> allPlayers = ft.readCSV(FileTools.USER_DATA_FILE);
		
		int pIdx = 0;
		//iterating through the List
		while(pIdx <= allPlayers.size()-1){
			//Searching for element with the correct user_ID
			if(allPlayers.get(pIdx)[0].equals(user_ID) && !allPlayers.get(pIdx)[0].equals("user_ID")){
				//Removing the element matching the user_ID
				allPlayers.remove(allPlayers.get(pIdx));
			}
			pIdx++;
		}
		//Re-writing the file with updated data
		ft.overwriteCSV(allPlayers, FileTools.USER_DATA_FILE);
	}
	
	public Player searchUser(String user_ID) throws IOException{
		FileTools ft = new FileTools();
		
		Player searchPl = null;
		List<String[]> allPlayers = ft.readCSV(FileTools.USER_DATA_FILE);
		
		int pIdx = 0;
		//iterating through the List
		while(pIdx <= allPlayers.size()-1){
			//Searching for element with the correct user_ID
			if(allPlayers.get(pIdx)[0].equals(user_ID) && !allPlayers.get(pIdx)[0].equals("user_ID")){
				//assigning the correct user
				searchPl = new Player(allPlayers.get(pIdx)[0], 
						              allPlayers.get(pIdx)[1], 
						              allPlayers.get(pIdx)[2], 
						              allPlayers.get(pIdx)[3], 
						              Integer.parseInt(allPlayers.get(pIdx)[4]));
			}
			pIdx++;
		}
		return searchPl;
	}
	
	//All transaction from one user
	public List<String[]> viewUserTrans(String user_ID, String filePath) throws IOException{
		FileTools ft = new FileTools();
		
		List<String[]> userTrans = ft.readCSV(FileTools.USER_TRANSACTION_LOG);
		
		//iterating and deleting all
		int i = 0;
		while(i <= userTrans.size()-1){
			if(!user_ID.equals(userTrans.get(i)[0])){
				userTrans.remove(userTrans.get(i));
			}
			i++;
		}
		return userTrans;
	}
	
	public List<String[]> viewAllTrans(String filePath) throws IOException{
		FileTools ft = new FileTools();
		//Load all their transactions into the list and return
		List<String[]> userTrans = ft.readCSV(FileTools.USER_TRANSACTION_LOG);
		
		return userTrans;
	}
}
