package Main;

public class Producto {
	
	private int idProd;
	private double precioBase;
	private int stock;
	private String name;
	private String imgURL;
	
	//---Constructores---//
	public Producto(){
		idProd = 0;
		precioBase = 0;
		stock = 0;
		name = new String("");
		imgURL = new String("");
	}
	public Producto(int idProd, double precioBase, int stock, String name, String imgURL){
		this.idProd = idProd;
		this.precioBase = precioBase;
		this.stock = stock;
		this.name = new String(name);
		this.imgURL = new String(imgURL);
	}
	//---End Constructores---//
	
	//---Setters & Getters---//
	public int getIdProd(){
		return this.idProd;
	}
	public double getPrecioBase(){
		return this.precioBase;
	}
	public int getStock(){
		return this.stock;
	}
	public String getName(){
		return this.name;
	}
	public String getImgURL(){
		return this.imgURL;
	}
	public void setIdProd(int id){
		this.idProd = id;
	}
	public void setPrecioBase(double precio){
		this.precioBase = precio;
	}
	public void setStock(int stock){
		this.stock = stock;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setImgURL(String imgURL){
		this.imgURL = imgURL;
	}
	//---End Setters & Getters---//	
	public double getFinalPrice(){
		return this.precioBase;
	}
	
	
}

