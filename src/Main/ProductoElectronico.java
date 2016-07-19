package Main;

public class ProductoElectronico extends Producto{
	
	private float[] dimensiones = new float[3];
	private int voltage;
	
	//---Constructores---//
	public ProductoElectronico(){
		super(0,0,0,"","");
		voltage = 0;
	}
	public ProductoElectronico(int idProd, float precioBase, int stock, String name,int voltage, float height, float length, float depth, String imgURL){
		super(idProd, precioBase, stock, name, imgURL);
		this.voltage = voltage;
		this.dimensiones[0] = height;
		this.dimensiones[1] = length;
		this.dimensiones[2] = depth;
	}
	//---End Constructores---//
	
	//---Setters and getters---//
	public void setVoltage(int voltage){
		this.voltage = voltage;
	}
	public void setDimensions(float height, float length, float depth){
		dimensiones[0] = height;
		dimensiones[1] = length;
		dimensiones[2] = depth;
	}
	public int getVoltage(){
		return this.voltage;
	}
	public float getHeight(){
		return dimensiones[0];
	}
	public float getLength(){
		return dimensiones[1];
	}
	public float getDepth(){
		return dimensiones[2];
	}
	//---End Setters and Getters---//
	
	//---Metodos propios---//
	@Override
	public double getFinalPrice(){
		return (super.getPrecioBase()+(super.getPrecioBase()*0.21)+4);
	}
	
	
	
	
	
	
	
}
