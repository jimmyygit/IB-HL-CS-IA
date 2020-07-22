import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class FileHandler {
	private static BufferedWriter writer;
	private static BufferedReader reader;
	
	public static boolean addTourney(String fileName, String newName, String[] stats) 
			throws Exception {
		reader = new BufferedReader(new FileReader(fileName));
		ArrayList<String> tourList = new ArrayList<String>();
		String lineOfText = reader.readLine();
        while (lineOfText != null)
        {
            tourList.add(lineOfText);
            lineOfText = reader.readLine();
        }
        reader.close();
  
        if (tourList.contains(newName)) {
    		JOptionPane.showMessageDialog(null, "Name already in use. Please enter a new name.", 
    				"Name Already Used", JOptionPane.WARNING_MESSAGE);
    	} else if (newName.equals("")) {
    		JOptionPane.showMessageDialog(null, "Empty name. Please enter a name.", 
    				"Empty Name", JOptionPane.WARNING_MESSAGE);
    	} else if (stats[0].trim().equals("")) {
    		JOptionPane.showMessageDialog(null, "No statistics. Please enter at least "
    				+ "one statistic to record.", 
    				"Empty Name", JOptionPane.WARNING_MESSAGE);
    	} else {
    		writer = new BufferedWriter(new FileWriter(fileName, true));
    		writer.write(newName);
    		writer.newLine();
    		writer.close();
    		return true;
    	}
        return false;
	}
	
	
	public static void addTourneyFile(String name, boolean isRobin, boolean isSize4, 
			String[] stats) throws IOException{
		
		writer = new BufferedWriter(new FileWriter(name + ".txt"));
		if (isRobin) writer.write("robin 0");
		else {
			if (isSize4) writer.write("playoff 0/4");
			else writer.write("playoff 0/8");
		}
		writer.newLine();
		writer.write(stats.length + "");
		writer.newLine();
		
		for (String stat : stats) {
			writer.write(stat);
			writer.newLine();
		}
		writer.close();
	}
	
	
	public static String[] getTourneyList(String tourneyName) throws IOException {
		String[] tourneyList; 
		reader = new BufferedReader(new FileReader(tourneyName));
		ArrayList<String> tourList = new ArrayList<String>();
		String lineOfText = reader.readLine();
        while (lineOfText != null)
        {
            tourList.add(lineOfText);
            lineOfText = reader.readLine();
        }
        reader.close();
        tourneyList = tourList.toArray(new String[tourList.size()]);
		return tourneyList;
	}
	
	
	public static String getTeamNumber(String tourneyName) throws IOException {
		reader = new BufferedReader(new FileReader(tourneyName+".txt"));
		String[] type = reader.readLine().split(" ");
		reader.close();
		return type[1];
	}
	
	
	public static String getTourneyType(String tourneyName) throws IOException {
		reader = new BufferedReader(new FileReader(tourneyName+".txt"));
		String[] type = reader.readLine().split(" ");
		reader.close();
		return type[0];
	}
	
	
	public static boolean addTeam(String tourneyName, String teamName, String imagePath) 
			throws IOException{
		reader = new BufferedReader(new FileReader(tourneyName+".txt"));
		ArrayList<String> teamList = new ArrayList<String>();
		String lineOfText = reader.readLine();
        while (lineOfText != null)
        {
            teamList.add(lineOfText);
            lineOfText = reader.readLine();
        }
        reader.close();
  

        if (teamList.contains(teamName + " " + teamName+".png") || 
        		teamList.contains(teamName + " defaultTeam.png")) {
    		JOptionPane.showMessageDialog(null, "Name already in use. Please enter a new name.", 
    				"Name Already Used", JOptionPane.WARNING_MESSAGE);
    	} else if (teamName.equals("")) {
    		JOptionPane.showMessageDialog(null, "Empty name. Please enter a name.", 
    				"Empty Name", JOptionPane.WARNING_MESSAGE);
    	} else {
    		writer = new BufferedWriter(new FileWriter(tourneyName+".txt"));
    		String[] type = teamList.get(0).split(" ");
    		if (type[0].equals("robin")) {
    			int size = Integer.parseInt(type[1]);
    			writer.write("robin " + (size+1));
    			writer.newLine();
    		} else {
    			String size = type[1];
   				String newLine = "playoff "+ (Integer.parseInt(size.charAt(0)+"")+1) 
   						+"/"+size.charAt(2);
    			writer.write(newLine);
    			writer.newLine();
    		}
    		
    		//write all stats
    		for (int i = 1; i < teamList.size(); i++) {
    			writer.write(teamList.get(i));
    			writer.newLine();
    		}
    		
    		//write team with image path
    		if (imagePath != null) {
    			BufferedImage image = ImageIO.read(new File(imagePath));
	            Image tempImg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	            BufferedImage teamImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
	            teamImage.getGraphics().drawImage(tempImg, 0, 0, null);
	            ImageIO.write(teamImage, "png", new File(teamName+".png"));
	  			writer.write(teamName + " " + teamName+".png");
    		} else {
    			writer.write(teamName + " defaultTeam.png");
    		}
    		writer.newLine();
    		writer.close();
    		
    		// create team file
    		writer = new BufferedWriter(new FileWriter(tourneyName+" "+teamName+".txt"));
    		writer.close();
    		
    		return true;
    	}
        return false;
	}
	
	
	public static ArrayList<String> getTeamList(String tourneyName) throws IOException {
		reader = new BufferedReader(new FileReader(tourneyName+".txt"));
		ArrayList<String> teamList = new ArrayList<String>();
		String lineOfText = reader.readLine();
		int size = Integer.parseInt(lineOfText.split(" ")[1].charAt(0)+"");
		lineOfText = reader.readLine();
		int numStats = Integer.parseInt(lineOfText);
		for (int i = 0; i < numStats; i++) {
			lineOfText = reader.readLine();
		}
		
		for (int i = 0; i < size; i++) {
			lineOfText = reader.readLine();
            teamList.add(lineOfText);
        }
        reader.close();
        return teamList;
	}
	
	public static void updateMatchList(String tourneyName) 
			throws IOException{ 
		reader = new BufferedReader(new FileReader(tourneyName+".txt"));
		String lineOfText = reader.readLine();
		lineOfText = reader.readLine();
		int numStats = Integer.parseInt(lineOfText);
		reader.close();
		ArrayList<String> teamList = getTeamList(tourneyName);
		for (int i = 0; i < teamList.size(); i++) {
			teamList.set(i, teamList.get(i).split(" ")[0]);
		}
		
		for (int i = 0; i < teamList.size(); i++) {
			String curName = teamList.get(i);
			writer = new BufferedWriter(new FileWriter(tourneyName+" "+curName+".txt"));
			for (int j = 0; j < teamList.size(); j++) {
				if (i != j) {
					writer.write(teamList.get(j)+" ");
					for (int k = 0; k < numStats; k++) {
						writer.write("0 ");
					}
					writer.newLine();
				}	
			}
			writer.close();
		}
	}
	
	
	public static String[] getStatLine(String tourneyName, String team1, String team2) 
			throws IOException{
		reader = new BufferedReader(new FileReader(tourneyName+" "+team1+".txt"));
		String[] statLine;
		String lineOfText = reader.readLine();
        while (lineOfText != null)
        {
        	statLine = lineOfText.split(" ");
        	if (statLine[0].equals(team2)) {
        		reader.close();
        		return Arrays.copyOfRange(statLine, 1, statLine.length);
        	}
            lineOfText = reader.readLine();
        }
        reader.close();
		return new String[1];
	}
	
	
	public static String[] getStatNames(String tourneyName) throws IOException {
		reader = new BufferedReader(new FileReader(tourneyName+".txt"));
		String lineOfText = reader.readLine();
		lineOfText = reader.readLine();
		int numStats = Integer.parseInt(lineOfText);
		String[] statNames = new String[numStats];
		for (int i = 0; i < numStats; i++) {
			statNames[i] = reader.readLine();
		}
		reader.close();
		return statNames;
	}
	
	
	public static void updateStats(String tourneyName, String team1, String team2, 
			String[] statLine) throws IOException {
		reader = new BufferedReader(new FileReader(tourneyName+" "+team1+".txt"));
		ArrayList<String> lineHistory = new ArrayList<String>();
		String lineOfText = reader.readLine();
        while (lineOfText != null)
        {
        	lineHistory.add(lineOfText);
        	lineOfText = reader.readLine();
        }
        reader.close();
        
        writer = new BufferedWriter(new FileWriter(tourneyName+" "+team1+".txt"));
        for (String line : lineHistory) {
        	if (team2.equals(line.split(" ")[0])) {
        		writer.write(team2+" ");
        		for(int i = 0; i < statLine.length; i++) {
        			writer.write(statLine[i]+" ");
        		}
        	} else {
        		writer.write(line);
        	}
        	writer.newLine();
        }
        writer.close();
	}
	
}









