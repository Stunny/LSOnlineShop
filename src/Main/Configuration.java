package Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 
 * @author Javier Ortiz & Alex Vogel
 * @version 1.0
 * 
 */
public class Configuration {

	private String nom;
	private String pincode;
	private double capital;
	private String fileRoute;

	/**
	 * Constructor sense parametres de la classe de configuracio que llegeix el fitxer de configuració.
	 */
	public Configuration(){
		nom = "";
		pincode = "";
		capital = -1;
		fileRoute = "";
	}

	/**
	 * Constructor de la classe configuració amb paràmetre d'inicialitzacio de la ruta de l'arxiu.
	 * @param fileRoute Ruta interna de l'arxiu de configuració que es vol assignar al construir un objecte de configuracio.
	 * @throws FileNotFoundException
     */
	public Configuration(String fileRoute) throws FileNotFoundException{
		this.fileRoute = fileRoute;
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(new FileReader(fileRoute));

		JsonObject conf = (JsonObject) obj.get("configuration");
		
		nom = conf.get("nom").getAsString();
		pincode = conf.get("pincode").getAsString();
		capital = conf.get("capital").getAsDouble();

		//System.out.println (nom + "|" + pincode + "|" + capital);
		
	}
	public String getNom(){
		return this.nom;
	}
	public String getPin(){
		return pincode;
	}
	public double getCapital(){
		return this.capital;
	}
	public String getFileRoute(){
		return this.fileRoute;
	}
	
	public void setPin(String pin)throws FileNotFoundException{
		this.pincode = pin;
		updateConfiguration();
	}
	public void setNom(String nombre)throws FileNotFoundException{
		this.nom = nombre;
		updateConfiguration();
	}
	public void setCapital(double money)throws FileNotFoundException{
		this.capital = money;
		updateConfiguration();
	}
	public void setFileRoute(String fileRoute) throws FileNotFoundException{
		this.fileRoute = fileRoute;
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(new FileReader(fileRoute));
		
		nom = obj.get("nom").getAsString();
		pincode = obj.get("pincode").getAsString();
		capital = obj.get("capital").getAsDouble();	
	}

	private void updateConfiguration() throws FileNotFoundException{
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(new FileReader(fileRoute));
		JsonObject conf = (JsonObject) obj.get("configuration");

		conf.remove("nom");
		conf.addProperty("nom", this.nom);
		conf.remove("pincode");
		conf.addProperty("pincode", this.pincode);
		conf.remove("nom");
		conf.addProperty("capital", this.capital);

        JsonObject obj2 = new JsonObject();
        obj2.add("configuration", conf);

        try {
            FileWriter fw = new FileWriter(fileRoute);
            fw.write(obj2.getAsString());
        }catch(IOException e){}
	}
}
