/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

package info.savestate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.json.*;

/**
 *
 * @author Joseph El-Khouri
 */
public class LiveTextBDBMJSON {
    
    private JSONObject json;
    private final int indentFactor = 2;
    
    public LiveTextBDBMJSON() { this("LiveTextBDBM.json"); }
    
    public LiveTextBDBMJSON(String dbPath) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(dbPath))) {
            // attempt to read in an existing JSON database
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
               sb.append(line).append('\n');
            // take JSON database that was read and put it into our instance DB
            json = new JSONObject(new JSONTokener(sb.toString()));
        } catch (FileNotFoundException ex) {
            // if the database doesn't exist, lets make a new one 
            // from scratch!
            try (PrintWriter writer = new PrintWriter(dbPath, "UTF-8")) {
                json = blankDatabase();
                writer.print(json.toString(indentFactor));
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException ex1) {
                System.out.println("Couldn't create new database! " + dbPath);
                System.exit(-2);
            }
        } catch (IOException ex) {
            System.out.println("Error opening database! " + dbPath);
            System.exit(-1);
        }
        
    }

    private JSONObject blankDatabase() {
        JSONObject blank = new JSONObject();
        // savepath
        blank.append("savepath", "");
        // teams - a json object full of teams
        blank.append("teams", new JSONArray());
        blank.getJSONArray("teams").put(blankTeam());
        // stats
        blank.append("stats", new JSONObject());
        blank.getJSONObject("stats").append("home", blankStatsList());
        blank.getJSONObject("stats").append("away", blankStatsList());
        
        return blank;
    }
    
    private JSONObject blankStatsList() {
        JSONObject blank = new JSONObject();
        blank.append("fieldgoals", "x-x");
        blank.append("3ptfieldgoals", "x.x%");
        blank.append("freethrows", "x-x");
        blank.append("rebounds", "x");
        blank.append("turnovers", "x");
        blank.append("startinglineup", new JSONArray());
        blank.append("scoringleaders", new JSONArray());
        blank.append("upcominggames",  new JSONArray());
        blank.append("keystothegame",  new JSONArray());
        for(int i=0; i<5; i++) {
            blank.getJSONArray("startinglineup").put(blankPlayer());
            blank.getJSONArray("scoringleaders").put(blankPlayer());
            blank.getJSONArray("upcominggames").put(blankUpcomingGame());  
        }
        for(int i=0; i<3; i++) 
            blank.getJSONArray("keystothegame").put("key to the game " + i);
        return blank;
    }
    
    private JSONObject blankUpcomingGame() {
        JSONObject blank = new JSONObject();
        blank.append("date", "");
        blank.append("vs", "");
        blank.append("time", "");
        return blank;
    }
    
    private JSONObject blankPlayer() {
        JSONObject blank = new JSONObject();
        blank.append("number", "");
        blank.append("position", "");
        blank.append("firstname", "");
        blank.append("lastname", "");
        blank.append("height", "");
        blank.append("weight", "");
        blank.append("year", "");
        blank.append("hometown", "");
        blank.append("points", "");
        blank.append("rebounds", "");
        blank.append("freethrows", "");
        blank.append("freethrowattempts", "");
        
        return blank;
    }
    
    private JSONObject blankTeam() {
        JSONObject blank = new JSONObject();
        blank.append("teamname", "");
        blank.append("mascot", "");
        blank.append("players", new JSONArray());
        blank.getJSONArray("players").put(blankPlayer());
        blank.append("coach", "");
        return blank;
    }
    
    
    
}
