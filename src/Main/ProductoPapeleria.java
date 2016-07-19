package Main;

public class ProductoPapeleria extends Producto{
	
	private int uds;
	
	//--Constructores---//
	public ProductoPapeleria(){
		super(0,0,0,"","");
		uds = 0;
	}
	public ProductoPapeleria(int idProd, float precioBase, int stock, String name, int unidades, String imgURL){
		super(idProd, precioBase, stock, name, imgURL);
		this.uds = unidades;
	}
	//---End Constructores---//
	
	//---Setters & Getters---//
	public void setUnidades(int uds){
		this.uds = uds;
	}
	public int getUnidades(){
		return this.uds;
	}
	//---End Setters & Getters---//
	
	//---Metodos propios---//
	@Override
	public double getFinalPrice(){
		return (super.getPrecioBase()+((super.getPrecioBase()+1)*0.3));
	}
	
	
	
	
	
	
}
