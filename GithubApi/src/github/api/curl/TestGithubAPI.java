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
		String userId = args[0];
		String token = args[1];
		try {
			
			url = new URL("https://api.github.com/orgs/Novartis-DevelopmentInformatics/teams?access_token=" + token);
			// Get list of teams under organization
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			
			// Get the team Id for all teams
		    for (String line; (line = reader.readLine()) != null;) {
		        System.out.println(line);
		        String word = "id";
		        int index = line.indexOf(word);
		        while (index >= 0) {
		            
		            String teamId = line.substring(index + 4, index +11);
		            System.out.println("TeamId: " + teamId);
		            
		            AddMemberService ams = new AddMemberService();
		            ams.addTeamMaintainer(userId, teamId, token);
		            index = line.indexOf(word, index + 1);
		        }
		    }
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	

	

}

