package Main;

public class ProductoDeportivo extends Producto{
	
	private String deporte;
	private float peso;
	
	//---Constructores---///
	public ProductoDeportivo(){
		super(0,0,0,"","");
		deporte = new String("");
		peso = 0;
	}
	public ProductoDeportivo(int idProd, float precioBase, int stock, String name, String deporte, float peso, String imgURL){
		super(idProd, precioBase, stock, name, imgURL);
		this.deporte = deporte;
		this.peso = peso;
	}
	//---End constructores---//
	
	
	
	//---Setters & Getters---//
	public void setDeporte(String deporte){
		this.deporte = deporte;
	}
	public void setPeso(float peso){
		this.peso = peso;
	}
	public String getDeporte(){
		return this.deporte;
	}
	public float getPeso(){
		return this.peso;
	}
	//---End Setters & Getters---//
	
	//---Metodos propios---//
	@Override
	public double getFinalPrice(){
		return (super.getPrecioBase()+((super.getPrecioBase()*15)/100)+(0.02 * this.peso));
	}
	
	
	
	
	
	
}
