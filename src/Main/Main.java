package Main;

import java.util.Scanner;
import java.io.*;

public class Main {

	public static void printarMenu(){
		//--Printamos por pantalla las opciones del menu en ascii--//
		System.out.println("//********************LS Online Shop********************\\");
		System.out.println("\n\t\t1. Llistar productes");
		System.out.println("\t\t2. Consultar producte");
		System.out.println("\t\t3. Comprar producte");
		System.out.println("\t\t4. Publicar producte");
		System.out.println("\t\t5. Eliminar producte");
		System.out.println("\t\t6. Compte d usuari");
		System.out.println("\t\t7. Sortir");

	}
	public static void Menu() throws IOException{
		int opcio;
		Scanner scr = new Scanner(System.in); //--variable scanner para tener entrada de informacion desde el teclado--//
		printarMenu();
		System.out.println("\n\t\tSelecciona una opcio:\t");
		opcio = scr.nextInt();
		ServerConnection srv = new ServerConnection("http://so.housing.salle.url.edu/dpo2/ls-onlineshop.php");
		HTMLGeneration.servURL = srv.getURL();
		User user = new User("C:/Users/avoge/Documents/Documentos/LaSalle/Segundo/DPO/Practica 1/LSOnlineShop/configuration.json");
		do{
			switch(opcio){

				case 1:  ///LLI0STAR PRODUCTES
					System.out.println("\t\t1.Llistar productes per categoria.");
					System.out.println("\t\t2.Llistar productes per rang de preu.");
					System.out.println("\t\t3.Tornar al menu principal.\n");
					System.out.println("\tSelecciona una opcio:\t");
					opcio = scr.nextInt();

					do{
						switch(opcio){

							case 1: //--llistarPerCategoria();

								System.out.println("\n\tQuines categories vols llistar: ");
								String categories = new String(scr.nextLine());
								HTMLGeneration.llistarProductes(categories);
								break;
							case 2: //--llistarPerPreu();

								float priceMax=0;
								float priceMin=0;
								do{
									System.out.println("Preu minim: ");
									priceMin = scr.nextFloat();
									System.out.println("Preu maxim: ");
									priceMax = scr.nextFloat();
									if(priceMax < 0 || priceMin < 0){
										System.out.println("\n\t\tEls preus han de ser mes grans que 0.\n");
									}else{
										HTMLGeneration.llistarProductes(priceMin, priceMax);
									}
								}while(priceMax < 0 || priceMin < 0);
								break;
							default:
								System.out.println("Opcio no valida.");
								break;
						}
					}while(opcio != 3);
					break;

				case 2: //--ConsultarProducte();

					int idProd;
					do{
						System.out.println("Quin producte vols consultar: ");
						idProd = scr.nextInt();
						if(idProd < 0){
							System.out.println("La ID d'un producte no pot ser negativa.");
						}else{
							HTMLGeneration.consultarProducte(idProd);
							break;
						}
					}while(idProd < 0);
					break;
				case 3:///BUY PRODUCTE///
					user = srv.buy(user);
					break;

				case 4:///UPLOAD PRODUCTE///
					//--PublicarProducte();
			  		srv.uploadProduct(user.getConfig().getPin());
					break;

				case 5: ///DELETE PRODUCTE///
					System.out.println("Quin producte vols eliminar: ");
					idProd = scr.nextInt();
					System.out.println("\n");
					srv.delete(idProd, user.getConfig().getPin());
					break;

				case 6:
					//--CompteDusuari();
					do{
						System.out.println("\n\t\t1.Veure dades del compte.");
						System.out.println("\t\t2.Canviar dades del compte.");
						System.out.println("\t\t3.Tornar al menu principal.\n");
						System.out.println("Selecciona una opci�: ");
						opcio = scr.nextInt();
						switch(opcio){

							case 1:
								//--visualizar datos de cuenta
								System.out.println("Nom del compte: " + user.getConfig().getNom());					
								System.out.println("Pincode: " + user.getConfig().getPin().charAt(0)+user.getConfig().getPin().charAt(1)+"***");
								System.out.println(user.getConfig().getCapital() + "euros");
								break;
							case 2:
								//--cambiar datos de cuenta
								do{
									System.out.println("\n\t\t1.Canviar pincode.");
									System.out.println("\t\t2.Canviar capital.");
									System.out.println("\t\t3.Guardar i Tornar.\n");
									System.out.println("Selecciona una opci�: ");
									opcio = scr.nextInt();
									switch(opcio){
										case 1:
											//--Cambiar el pin
											System.out.println("\nIntrodueixi el pincode actual:");
											if(scr.next() != user.getConfig().getPin()){
												System.out.println("Pincode incorrecte.");
												break;
											}else{
												System.out.println("\nIntrodueixi el nou pincode:");
												user.getConfig().setPin(scr.next());
												break;
											}

										case 2:
											//--Actualizar capital
											System.out.println("Introdueixi el nou valor del seu capital. Recordi que ha de tenir dos decimals:");
											try {
												user.getConfig().setCapital(scr.nextDouble());
											}catch(Exception e){
												e.printStackTrace(); //Protegemos nuestro codigo
											}
											break;
										default:
											if(opcio != 3){
												System.out.println("Opci� no v�lida.");
												break;
											}else{
												break;
											}
									}
								}while(opcio != 3);
								//user.config.updateConfiguration();
							default:
								if(opcio != 3){
									System.out.println("Opci� no v�lida.");
									break;
								}else{
									break;
								}

						}
					}while(opcio != 3);
					break;

				case 7:
					//--TancarPrograma();
					System.out.println("Tancant botiga...");
					break;

				default:
					System.out.println("Opcio no valida.");
					break;
			}
			printarMenu();
			System.out.println("\t\tSelecciona una opcio:\t");
			opcio = scr.nextInt();

		}while(opcio != 7);
		scr.close();
	}

	public static void main(String[] args) throws IOException {

		Menu();

	}

}
