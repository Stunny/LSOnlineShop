package Main;

public class ProductoOtros extends Producto{
	
	private String subCategoria;
	private String specs;
	
	//---Constructores---//
	public ProductoOtros(){
		super(0,0,0,"","");
		subCategoria = new String("");
		specs = new String("");
	}
	public ProductoOtros(int idProd, double precioBase, int stock, String name, String subCategoria, String specs, String imgURL){
		super(idProd, precioBase, stock, name, imgURL);
		this.subCategoria = subCategoria;
		this.specs = specs;
	}	
	//---End Constructores---//
	
	//---Setters & Getters---//
	public void setSubCategory(String subCat){
		this.subCategoria = subCat;
	}
	public void setSpecs(String specs){
		this.specs = specs;
	}
	public String getSubCategory(){
		return this.subCategoria;
	}
	public String getSpecs(){
		return this.specs;
	}
	
	//---End Setters & Getters---//
	
	//---Metodos propios---//
	@Override
	public double getFinalPrice(){
		return(super.getPrecioBase()+(super.getPrecioBase()*0.25));
	}
	
}
