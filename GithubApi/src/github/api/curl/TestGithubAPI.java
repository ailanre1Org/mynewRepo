package github.api.curl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author Reena Ailani
 * 
 * param: User 5-2-1 Id to add into all teams under Novartis-DevelopmentInformatics
 * param: Organization owner's token generated from Github
 *
 */

public class TestGithubAPI {

	
	public static void main(String args[]) {
		URL url;
		if(args.length != 3) {
			System.out.println("Usage: userId token [add][remove]");
			System.exit(0);
		}
		String userId = args[0];
		String token = args[1];
		String addRemoveFlag = args[2];
		
		if(!(addRemoveFlag.equalsIgnoreCase("add") || addRemoveFlag.equalsIgnoreCase("remove"))) {
			System.out.println("Invalid input");
        	System.exit(0);
		}
		int i=1;
		try {
			do {
				url = new URL("https://api.github.com/orgs/ailanre1Org/teams?access_token=" + token + "&page="+i+"&per_page=100");
				// Get list of teams under organization
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
				
				String word = "id";
				String line = reader.readLine();
				if(line.indexOf(word) < 0)
					break;
				
				// Get the team Id for all teams
			    for (; line != null;) {
			        System.out.println(line);
			        //String word = "id";
			        int index = line.indexOf(word);
			        while (index >= 0) {
			            
			            String teamId = line.substring(index + 4, index +11);
			            System.out.println("TeamId: " + teamId);
			            
			            AddMemberService ams = new AddMemberService();
			            if(addRemoveFlag.equalsIgnoreCase("add")) {
			            	ams.updateTeamMaintainer(userId, teamId, token,"PUT");
			            } else if(addRemoveFlag.equalsIgnoreCase("remove")){
			            	ams.updateTeamMaintainer(userId, teamId, token, "DELETE");
			            } 
			            
			            index = line.indexOf(word, index + 1);
			        }
			        
			        line = reader.readLine();
			    }
			    i++;
			} while(i < 10);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	

	

}

