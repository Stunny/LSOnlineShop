package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HTMLGeneration {
	
	public static String servURL = new String();
	public static JsonObject all;
	
	private static boolean[] comprobarCategorias(String cats){
		boolean[] ok = {false, false, false, false};
		// ok[0] -> Esports, ok[1]->Electronica, ok[2]->Papereria, ok[3]->Altres
		int i = 0;
		
		if(!cats.contains(",")){
			if(cats.contains("Tots") || cats.contains("tots")){
				for(i=0;i<4;i++){
					ok[i]=true;
				}
			}	
		}else{
			if(cats.contains("Esports")||cats.contains("esports"))ok[0]=true;
			if(cats.contains("Electronica")||cats.contains("electronica"))ok[1]=true;
			if(cats.contains("Papereria")||cats.contains("papereria"))ok[2]=true;
			if(cats.contains("Altres")||cats.contains("altres"))ok[3]=true;	
		}
		
		
		
		return ok;
	}
	public static boolean llistarProductes() throws IOException{
		System.out.println("Generant HTML...");
		
		String fileName = "llistatProductes.html";
		File f = new File(fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		JsonArray prods = all.get("object").getAsJsonArray();
		JsonObject prod;
		int i = 0;
		int size = prods.size();
		
		bw.write("<!DOCTYPE HTML>");
		bw.write("<html lang=\"es\">");
			bw.write("<head>");
				bw.write("<title>Llistar Productes</title>");
				bw.write("<meta charset=\"utf-8\"");
				bw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"tabla.css\">");
			bw.write("</head>");
			bw.write("<body>");
				bw.write("<header><h2>Llistar Productes</h2></header>");
				bw.write("<section>");
					bw.write("<table>");
						bw.write("<tr>");
							bw.write("<th>ID</th>");
							bw.write("<th>NOM</th>");
							bw.write("<th>ESTOC</th>");
							bw.write("<th>CATEGORIA</th>");
							bw.write("<th>PREU BASE</th>");
							bw.write("<th>PREU FINAL</th>");
							bw.write("<th>ALTRES</th>");
						bw.write("</tr>");
						
		int category = 0;
		String cat = new String();
		String altres = new String();
		Producto element = new Producto();
		for(i=0; i < size; i++){
			prod = prods.get(i).getAsJsonObject();
			category = prod.get("category").getAsInt();
			switch(category){
				case 1:
						bw.write("<tr class=\"sports\">");
						cat = "Esports";
						element = new ProductoDeportivo(prod.get("id").getAsInt(),prod.get("price").getAsFloat(),
														prod.get("stock").getAsInt(),prod.get("product_name").getAsString(),
														prod.get("sport").getAsString(),prod.get("weight").getAsFloat(),"");
						altres = "<p>Esport: "+((ProductoDeportivo)element).getDeporte()+"</p>";
						altres += "\n<p>"+Float.toString(((ProductoDeportivo)element).getPeso())+"Kg</p>";
					break;
				case 2:
						bw.write("<tr class=\"electronics\">");
						cat = "Electronic";
						element = new ProductoElectronico(prod.get("id").getAsInt(),prod.get("price").getAsFloat(),
														prod.get("stock").getAsInt(),prod.get("product_name").getAsString(),
														prod.get("ac_supply").getAsInt(),prod.get("height").getAsFloat(),
														prod.get("width").getAsFloat(),prod.get("depth").getAsFloat(),"");
						altres ="<p>Voltatge: "+Integer.toString(((ProductoElectronico)element).getVoltage())+" V</p>";
						altres += "\n<p>Mesures: ("+Float.toString(((ProductoElectronico)element).getHeight())
													+"cm,"+Float.toString(((ProductoElectronico)element).getLength())
													+"cm,"+Float.toString(((ProductoElectronico)element).getDepth())+"cm)</p>";
					break;
				case 3:
						bw.write("<tr class=\"papeleria\">");
						cat = "Papereria";
						element = new ProductoPapeleria(prod.get("id").getAsInt(), prod.get("price").getAsFloat(),
														prod.get("stock").getAsInt(),prod.get("product_name").getAsString(),
														prod.get("units_per_box").getAsInt(),"");
						altres = "<p>Unitats: "+Integer.toString(((ProductoPapeleria)element).getUnidades())+"</p>";
					break;
				case 4:
						bw.write("<tr class=\"other\">");
						cat = "Altres";
						element = new ProductoOtros(prod.get("id").getAsInt(),prod.get("price").getAsDouble(),
													prod.get("stock").getAsInt(),prod.get("product_name").getAsString(),
													prod.get("alt_category").getAsString(),prod.get("additional_specs").getAsString(),"");
						altres = "<p>Categoria: "+((ProductoOtros)element).getSubCategory()+"</p>";
						altres += "\n<p>"+((ProductoOtros)element).getSpecs()+"</p>";
					break;
			}
							bw.write("<td>"+Integer.toString(prod.get("id").getAsInt())+"</td>");
							bw.write("<td>"+prod.get("product_name").getAsString()+"</td>");
							bw.write("<td>"+Integer.toString(prod.get("stock").getAsInt())+"</td>");
							bw.write("<td>"+cat+"</td>");
							bw.write("<td>"+Float.toString(prod.get("price").getAsFloat())+"�</td>");
							bw.write("<td>"+Double.toString(element.getFinalPrice())+"E</td>");
							bw.write("<td>"+altres+"</td>");
						bw.write("</tr>");
		}
						
						
					bw.write("</table>");
				bw.write("</section>");
			bw.write("</body>");
		bw.write("</html>");
		bw.close();
		
		System.out.println("HTML Generat.");
		return true;
	}
	public static boolean llistarProductes(float minPrice, float maxPrice)throws IOException{
		
		ServerConnection srv = new ServerConnection(servURL);
		all = srv.getProducts();
		
		
		if(srv.getRes() == 0){
			System.out.println(srv.getMsg());
			return false;
		}
		
		JsonArray prods = all.get("object").getAsJsonArray();
		JsonArray out = new JsonArray();
		JsonObject element = new JsonObject();
		JsonObject newAll = new JsonObject();
		
		for(int i=0; i < prods.size(); i++){
			element = prods.get(i).getAsJsonObject();
			if((element.get("price").getAsFloat() >= minPrice)&&(element.get("price").getAsFloat() <= maxPrice)){
				out.add(element);
			}
		}
		
		newAll.addProperty("res", 1);
		newAll.addProperty("msg", "Productes dins el rang de preu especificat.");
		newAll.add("object", out);
		
		all = newAll;
		
		return llistarProductes();
	}
	public static boolean llistarProductes(String categories)throws IOException{
		
		if(categories != "Tots" || categories !="tots"){
			
			boolean[] cats = comprobarCategorias(categories);
		
			ServerConnection srv = new ServerConnection(servURL);
			all = srv.getProducts();
			
			
			if(srv.getRes() == 0){
				System.out.println(srv.getMsg());
				return false;
			}
		
			JsonArray prods = all.get("object").getAsJsonArray();
			JsonArray out = new JsonArray();
			JsonObject element = new JsonObject();
			JsonObject newAll = new JsonObject();
			
			for (int i=0; i<prods.size( );i++){
				
			}
		
		
		
		}
		return llistarProductes();
	}
	public static boolean consultarProducte(int idProd) throws IOException{
		System.out.println("Generant HTML...");
		
		ServerConnection srv = new ServerConnection(servURL);
		Producto prod = srv.getProduct(idProd);
		
		String fileName = new String(prod.getName()+".html");
		File f = new File(fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("<!DOCTYPE HTML>");
		bw.write("<html lang =\"es\">");
			bw.write("<head>");
				bw.write("<head><title>"+prod.getName()+"</title></head>");
				bw.write("<meta charset=\"utf-8\">");
			bw.write("</head>");
			bw.write("<body>");
				bw.write("<h2>Consultar producte</h2>");
				bw.write("<h3>"+prod.getName()+"</h3>");
				bw.write("<img src=\""+prod.getImgURL()+"\" alt=\""+prod.getName()+"\">");
				bw.write("<p>ID: "+prod.getIdProd()+"</p>");
				bw.write("<p>Preu base: "+prod.getPrecioBase()+" E</p>");
				bw.write("<p>Stock: "+prod.getStock()+" unitats</p>");/**
				 															*/
				
		if(prod instanceof ProductoDeportivo){
				bw.write("<p>Categoria: Esportiu</p>");
				bw.write("<p>Esport: "+((ProductoDeportivo)prod).getDeporte()+"</p>");
				bw.write("<p>Pes: "+Float.toString(((ProductoDeportivo)prod).getPeso())+" Kg</p>");
		}
		else if(prod instanceof ProductoElectronico){
				bw.write("<p>Categoria: Electr&oacute;nica</p>");
				bw.write("<p>Voltatge: "+Integer.toString(((ProductoElectronico)prod).getVoltage())+"V</p>");
				bw.write("<p>Dimensions: ("+Float.toString(((ProductoElectronico)prod).getHeight())+"cm, "
										+Float.toString(((ProductoElectronico)prod).getLength())+"cm, "
										+Float.toString(((ProductoElectronico)prod).getDepth())+"cm)</p>");
		}
		else if(prod instanceof ProductoPapeleria){
				bw.write("<p>Categoria: Papereria</p>");
				bw.write("<p>Unitats per paquet: "+Integer.toString(((ProductoPapeleria)prod).getUnidades())+"</p>");
		}
		else if(prod instanceof ProductoOtros){
				bw.write("<p>Categoria: Altres</p>");
				bw.write("<p>Sub-categoria: "+((ProductoOtros)prod).getSubCategory()+"</p>");
				bw.write("<p>Especificacions: "+((ProductoOtros)prod).getSpecs()+"</p>");
		}
			bw.write("</body>");
			
		bw.write("</html>");
		bw.close();
		System.out.println("HTML Generat.");
		return true;
	}
}
