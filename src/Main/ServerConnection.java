package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.omg.CORBA.portable.InputStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 
 * @author Javier Ortiz & Alex Vogel
 * 
 */

public class ServerConnection {
	
	private String sURL;
	private String msg;
	private int res;
	
	///CONSTRUCTORES///
	public ServerConnection(){
		sURL = new String("");
	}
	public ServerConnection(String address){
		sURL = address;
	}
	
	///SETTERS AND GETTERS///
	public void setURL(String sURL){
		this.sURL = sURL;
	}
	public String getURL(){
		return sURL;
	}
	public String getMsg(){
		return this.msg;
	}
	public int getRes(){
		return this.res;
	}
	
	///METHODS///
	public Producto getProduct(int idProd) throws IOException{
		Producto prod = new Producto();
		
		JsonObject all = this.getProducts();
		JsonArray lista = all.get("object").getAsJsonArray();
		JsonObject element;
		
		int i = 0;
		boolean found = false;
		
		while((!found) || (i != (lista.size()-1))){
			element = lista.get(i).getAsJsonObject();
			if(element.get("id").getAsInt() == idProd){
				found = true;
			}
			i++;
		}
		
		if(found){
			element = lista.get(i).getAsJsonObject();
			switch(element.get("category").getAsInt()){
				case 1:
					prod = new ProductoDeportivo();
					prod.setIdProd(element.get("id").getAsInt());
					prod.setImgURL(element.get("image").getAsString());
					prod.setName(element.get("product_name").getAsString());
					prod.setPrecioBase(element.get("price").getAsDouble());
					prod.setStock(element.get("stock").getAsInt());
					((ProductoDeportivo) prod).setDeporte(element.get("sport").getAsString());
					((ProductoDeportivo) prod).setPeso(element.get("weight").getAsFloat());
					break;
				case 2:
					prod = new ProductoElectronico();
					prod.setIdProd(element.get("id").getAsInt());
					prod.setImgURL(element.get("image").getAsString());
					prod.setName(element.get("product_name").getAsString());
					prod.setPrecioBase(element.get("price").getAsDouble());
					prod.setStock(element.get("stock").getAsInt());
					((ProductoElectronico) prod).setVoltage(element.get("ac_supply").getAsInt());
					((ProductoElectronico) prod).setDimensions(element.get("height").getAsFloat(), element.get("width").getAsFloat(), element.get("depth").getAsFloat());
					break;
				case 3:
					prod = new ProductoPapeleria();
					prod.setIdProd(element.get("id").getAsInt());
					prod.setImgURL(element.get("image").getAsString());
					prod.setName(element.get("product_name").getAsString());
					prod.setPrecioBase(element.get("price").getAsDouble());
					prod.setStock(element.get("stock").getAsInt());
					((ProductoPapeleria) prod).setUnidades(element.get("units_per_box").getAsInt());
					break;
				case 4:
					prod = new ProductoOtros();
					prod.setIdProd(element.get("id").getAsInt());
					prod.setImgURL(element.get("image").getAsString());
					prod.setName(element.get("product_name").getAsString());
					prod.setPrecioBase(element.get("price").getAsDouble());
					prod.setStock(element.get("stock").getAsInt());
					((ProductoOtros) prod).setSpecs(element.get("additional_spoecs").getAsString());
					((ProductoOtros) prod).setSubCategory(element.get("alt_category").getAsString());
					break;
			}
		}else{
			prod.setName("NULL");
		}
		
		
		return prod;
	}
	public JsonObject getProducts() throws IOException{
		
		String get = sURL + "?method=getProducts";
		URL url = new URL(get);
		
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		String azucar = request.getContent().toString();

		System.out.println(azucar + "  [][][]Mamamia");

		JsonParser parser = new JsonParser();
		JsonObject src = (JsonObject) parser.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject obj = src.getAsJsonObject();
		this.msg = obj.get("msg").getAsString();
		this.res = obj.get("res").getAsInt();
		
		request.disconnect();
		return obj;
	}
	public boolean uploadProduct(String pincode) throws IOException{
		
		String post = sURL + "?method=uploadProduct&pincode="+pincode+"&";
		Scanner scr = new Scanner(System.in);
		int option = 0;
		
		System.out.println("Nom: ");
		String nom = scr.nextLine();
		post += "product_name="+URLEncoder.encode(nom, "UTF-8")+"&";
		System.out.println("Estoc(unitats): ");
		post += "stock="+Integer.toString(scr.nextInt())+"&";
		System.out.println("Preu(�): ");
		post += "price="+Float.toString(scr.nextFloat())+"&";
		System.out.println("Imatge(URL): ");
		post += "image="+scr.nextLine()+"&";
		
		while(option < 1 || option > 4){
			System.out.println("A quina categoria pertany el producte?");
			System.out.println("\n");
			System.out.println("1. Electronica.");
			System.out.println("2. Esports.");
			System.out.println("3. Papereria.");
			System.out.println("4. Altres.");
			System.out.println("\nSelecciona una opcio:");
	
			option = scr.nextInt();
			
			switch(option){
				case 1: //Publicar producte electronic
					post += "category=2&";
					System.out.println("Voltatge: ");
					post += "ac_supply="+Float.toString(scr.nextFloat())+"&";
					System.out.println("Amplada: ");
					post += "width="+Float.toString(scr.nextFloat())+"&";
					System.out.println("Alcada: ");
					post += "height="+Float.toString(scr.nextFloat())+"&";
					System.out.println("Fundaria: ");
					post += "depth="+Float.toString(scr.nextFloat());
					break; 
				case 2: //Publicar producte esportiu
					post += "category=1&";
					System.out.println("Nom de l'esport: ");
					post += "sport="+URLEncoder.encode(scr.nextLine(), "UTF-8")+"&";
					System.out.println("Pes(Kg): ");
					post += "weight="+Float.toString(scr.nextFloat());
					break;
				case 3: //Publicar producte de papereria
					post += "category=3&";
					System.out.println("Unitats per capsa: ");
					post += "units_per_box="+Integer.toString(scr.nextInt());
					break;
				case 4: //Publicar producte altres
					post += "category=4&";
					System.out.println("Subcategoria: ");
					post += "alt_category="+URLEncoder.encode(scr.nextLine(), "UTF-8")+"&";
					System.out.println("Especificacions addicionals: ");
					post += "additional_specs="+URLEncoder.encode(scr.nextLine(),"UTF-8");
					break;			
				default:
					System.out.println("Opcio no valida.\n");
					break;
			}
		}
		System.out.println("Vols publicar el producte "+nom+" (S/N)?");
		char conf = (char)scr.nextByte();
		do{
			switch(conf){
				case 'S':
					URL url = new URL(post);
					HttpURLConnection request = (HttpURLConnection) url.openConnection();
					request.connect();
					
					JsonParser parser = new JsonParser();
					JsonElement src = parser.parse(new InputStreamReader((InputStream) request.getContent()));
					JsonObject obj = src.getAsJsonObject();
					
					this.res = obj.get("res").getAsInt();
					this.msg = obj.get("msg").getAsString();
					
					request.disconnect();
					
					if(res == 1)System.out.println("Producte publicat!");
					if(res == 0)System.out.println(msg);
					
					return true;
				case 'N':
					System.out.println("Producte "+nom+" descartat.");
					
					return false;
				default:
					System.out.println("Vols publicar el producte "+nom+" (S/N)?");
					conf = (char)scr.nextByte();
					break;
			}
		}while(conf != 'S' && conf != 'N');
		
		scr.close();
		
		return false;
	}
	public User buy(User user) throws IOException{
		
		String buy = sURL +"?method=buy&";
		Scanner scr = new Scanner(System.in);
		
		System.out.println("Quin producte vols comprar: ");
		int prodID = scr.nextInt();
		Producto prod = this.getProduct(prodID);
		String name = new String(prod.getName());
		double price = 0;
		System.out.println("\n");
		
		String description = new String("--- "+prod.getName()+" - "+Double.toString(prod.getPrecioBase())
										+"� - ");
		if(prod instanceof ProductoDeportivo) {
			description += Double.toString(((ProductoDeportivo)prod).getFinalPrice());
			price = ((ProductoDeportivo)prod).getFinalPrice();
		}
		else if(prod instanceof ProductoElectronico){
			description += Double.toString(((ProductoElectronico)prod).getFinalPrice());
			price = ((ProductoElectronico)prod).getFinalPrice();
		}
		else if(prod instanceof ProductoPapeleria){
			description += Double.toString(((ProductoPapeleria)prod).getFinalPrice());
			price = ((ProductoPapeleria)prod).getFinalPrice();
		}
		else if(prod instanceof ProductoOtros){
			description += Double.toString(((ProductoOtros)prod).getFinalPrice());
			price = ((ProductoOtros)prod).getFinalPrice();
		}
		
		description += "� - "+Integer.toString(prod.getStock())+" unitats ---";
		
		System.out.println(description+"\n");
		
		System.out.println("Quants vols comprar: ");
		int quantity = scr.nextInt();
		System.out.println("Vols comprar el producte "+name+" (S/N)?\n");
		char conf = (char)scr.nextByte();
		do{
			switch(conf){
				case 'S':
					buy += "id="+Integer.toString(prodID)+"&quantity="+Integer.toString(quantity);
					double finalPrice = price * quantity;
					if(finalPrice > user.getConfig().getCapital()){
						System.out.println("No tens suficient capital com per dur a terme aquesta compra.\n");
					}else{
						URL url = new URL(buy);
						HttpURLConnection request = (HttpURLConnection) url.openConnection();
						request.connect();
						
						JsonParser parser = new JsonParser();
						JsonElement src = parser.parse(new InputStreamReader((InputStream) request.getContent()));
						JsonObject obj = src.getAsJsonObject();
						
						this.res = obj.get("res").getAsInt();
						this.msg = obj.get("msg").getAsString();
						
						request.disconnect();
						
						if(res == 0) System.out.println(msg+"\n");
						if(res == 1){
							user.getConfig().setCapital(user.getConfig().getCapital()-finalPrice);
							System.out.println("S'ha realitzat la compra del producte "+name
												+" ("+Integer.toString(quantity)+" unitats).");
							System.out.println("Capital disponible: "+Double.toString(user.getConfig().getCapital())+" �\n");
						}
					}
					break;
				case 'N':
					System.out.println("Compra cancel.lada.");
					break;
				default:
					System.out.println("Vols comprar el producte "+name+" (S/N)?\n");
					conf = (char)scr.nextByte();
					break;
			}
		}while(conf != 'S' && conf != 'N');
		scr.close();
		return user;
		
	}
	public void delete(int prodID, String pincode) throws IOException{
		
		String delete = sURL +"?method=delete&id="+Integer.toString(prodID)+"&pincode="+pincode;
		URL url = new URL(delete);
		
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		
		JsonParser parser = new JsonParser();
		JsonElement src = parser.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject obj = src.getAsJsonObject();
		
		this.res = obj.get("res").getAsInt();
		this.msg = obj.get("msg").getAsString();
		
		request.disconnect();
		
		if(res == 1) System.out.println("Producte eliminat!\n");
		if(res == 0) System.out.println(msg+"\n");
	}
}
