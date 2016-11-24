import java.util.ArrayList;
import java.util.Stack;


public class Exploracion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args[0] != null && args[1] != null && args[2] != null)
		{
			Plano juego = new Plano(Integer.parseInt(args[1]),Integer.parseInt(args[2]));//creamos el plano con sus dimensiones vacio
			juego.leePlano(args[0]);//Leemos el plano por primera vez
			Boolean nosalir = true;//No se saldra hasta que encuentre todos los caminos hasta llegar a la puerta
			Lista caminos = new Lista();//Esto almacenara nuestros caminos realizados una vez que encontremos un destino
			Stack<exPath> ruta = new Stack<exPath>();//norte (^), sur (v), este (->), oeste (<-)
			Boolean caidaentrampa = false;//indica si has caido en la trampa
			Boolean encuentroelfinal = false;//indica si has encontrado el final del camino, siempre y cuando hayas encontrado todos los destinos o una salida
			Boolean primerainteracion = true;//se ejecuta en la primera interaccion
			Boolean segundainteracion = false;//nos sirve para desapilar, mirar la posicion, mirar las rutas disponibles y apilar las direcciones en ruta
			exPath casillactual=null;//nos servira para conocer la ultimas coordenadas con las que estabamos trabajando 
			int destinos = juego.getdestinos();//destinos que se pueden encontrar
			int propiocontador = 0;//contador de destinos encontrados
			Coordenadas comienzo = juego.getcomienzo();
			Boolean modobuscarc = false;//este modo se activara cuando se vacie la pila
			while(nosalir)
			{
				if(caidaentrampa)//esto lo ejecuta cuando has caida en la trampa
				{
					if(primerainteracion)//hacemos el primer paso de cargar el primer expath en la pila
					{
						ArrayList<Coordenadas> cami = new ArrayList<Coordenadas>();
						exPath nuevo = new exPath(comienzo.getFila(),comienzo.getColumna(),cami);
						ruta.push(nuevo);
						primerainteracion = false;
						segundainteracion = true;
					}
					else if(segundainteracion && ruta.size() > 0)//desapilamos la cabeza y apilamos los diferentes lados
					{
						casillactual = ruta.pop();
						char tipo =  juego.consultaCasilla(casillactual.getCoordenadas());
						if(tipo == 'l' || tipo == 'c' ||(tipo == 'p' && juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'P'))//comienzo se trata igual que libre
						{
							//puesto que las puertas nunca se ponen como visitadas
							if(!(tipo == 'p' && juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'P'))
							{
								juego.cambiaraestado(casillactual.getCoordenadas(),'v');//ponemos en visitado esta posicion
							}
							
							//esto nos servira para saber si tenemos que salir
							if(modobuscarc == true && tipo == 'c')
							{
								nosalir = false;
								if(propiocontador == destinos)
								encuentroelfinal = true;
								else
								encuentroelfinal = false;
							}
							
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()+1),casillactual.getCoordenadas().getColumna())) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()+1),casillactual.getCoordenadas().getColumna())) != 'v')//sur
							{
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()+1),casillactual.getCoordenadas().getColumna(),nuevocamino); 
								ruta.push(nuevopaso);								
							}

							//apilamos en la pila las posiciones mientras que no sean obstaculos
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()-1),casillactual.getCoordenadas().getColumna())) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()-1),casillactual.getCoordenadas().getColumna())) != 'v')//norte
							{					
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()-1),casillactual.getCoordenadas().getColumna(),nuevocamino); 
								ruta.push(nuevopaso);
							}
							
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()-1))) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()-1))) != 'v')//oeste
							{
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()-1),nuevocamino); 
								ruta.push(nuevopaso);								
							}							
							
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()+1))) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()+1))) != 'v')//este
							{
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()+1),nuevocamino); 
								ruta.push(nuevopaso);								
							}

						}
						else if(tipo == 'p')//si es una puerta haremos lo que nos dicen
						{
							
							// directamente para la propiedad B no hacemos nada porque ya se ha desapilado esta opcion, la proxima vez ejecutara la siguiente opcion mas cercana siempre y cuando no este vacia la pila
							if(juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'S')
							{
								//visitamos destino mas cercano,siempre y cuando exista, si no volvemos al destino
								if(juego.buscardestinos() != null)
								{
									ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
									exPath nuevopaso = new exPath(juego.buscardestinos().firstElement().getCoordenadas().getFila(),juego.buscardestinos().firstElement().getCoordenadas().getColumna(),nuevocamino); 
									ruta.push(nuevopaso);								
								}
								else
								{
									ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
									exPath nuevopaso = new exPath(juego.getcomienzo().getFila(),juego.getcomienzo().getColumna(),nuevocamino); 
									ruta.push(nuevopaso);
									//encolamos
									if(caminos.esVacia())
										{caminos.insertaCabeza(nuevocamino);}
									else
										{caminos.insertaCola(nuevocamino);}
									
									encuentroelfinal = true;
									nosalir = false;
									//aqui fin de juego
								}
							}
							else if(juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'T')
							{
								
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath(juego.getcomienzo().getFila(),juego.getcomienzo().getColumna(),nuevocamino); 
								ruta.push(nuevopaso);
								//encolamos
								if(caminos.esVacia())
									{caminos.insertaCabeza(nuevopaso.getCamino());}
								else
									{caminos.insertaCola(nuevopaso.getCamino());}
								
								//reinicializamos y cambiamos el sentido de la marcha
								juego.cambiaraestado(casillactual.getCoordenadas(), 'T');
								juego.inicializartodolovistoalibre();//inicializamos todos los v a l
								primerainteracion = true;//volvemos al primer paso
								segundainteracion = false;
								comienzo = juego.getcomienzo();
								ruta = new Stack<exPath>();//borramos la pila, se encarga el recogedor de basura de java
								caidaentrampa = true;
							}
							else if(juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'X')
							{
								
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								//encolamos
								if(caminos.esVacia())
									{caminos.insertaCabeza(nuevocamino);}
								else
									{caminos.insertaCola(nuevocamino);}
														
								encuentroelfinal = true;
								nosalir = false;
								//aqui fin de juego	
								
							}
						}
						else if(tipo == 'd')//si es un destino haremos lo que dice en implementacion
						{
							//insertamos el camino en la lista
							if(caminos.esVacia())
							{
								caminos.insertaCabeza(casillactual.getCamino());
							}
							else
							{
								caminos.insertaCola(casillactual.getCamino());
							}
							
							juego.cambiaraestado(casillactual.getCoordenadas(),'l');//cambiamos su estado de d a l
							
							juego.inicializartodolovistoalibre();//inicializamos todos los v a l
							
							comienzo = casillactual.getCoordenadas();//guardamos la posicion como el siguiente comienzo
							
							ruta = new Stack<exPath>();//borramos la pila, se encarga el recogedor de basura de java
							
							primerainteracion = true;//volvemos al primer paso
							segundainteracion = false;
							
							propiocontador++;
						}
						
						
					}
					else if(ruta.size() <= 0 && modobuscarc != true)//esto pasara si no hay nada en la pila y no se ha encontrado una puerta x, o se ha vuelto al comienzo
					{
						//haremos desde la posicion actual una ultima vuelta
						if(casillactual != null)
						{
							comienzo=casillactual.getCoordenadas();
							//reinicializamos todo a libre, menos las puertas
							juego.inicializartodolovistoalibre();//inicializamos todos los v a l
							//en la proxima interaccion empezara de nuevo pero esta vez si encuentra comienzo lo encolara y terminara, si no lo encuentra con el el modobuscarc en true terminara cuando este la pila vacia  
							primerainteracion = true;//volvemos al primer paso
							segundainteracion = false;
							modobuscarc = true;
						}
						else
						{
							modobuscarc = true;
						}
					}
					else if(ruta.size() <= 0 && modobuscarc != true)
					{
						//se termina el juego perdiendo
						nosalir = false;
						encuentroelfinal = false;
					}					
					
				}
				else if(!caidaentrampa)//esto lo ejecuta cuando no has caido en la trampa
				{
					if(primerainteracion)//hacemos el primer paso de cargar el primer expath en la pila
					{
						ArrayList<Coordenadas> cami = new ArrayList<Coordenadas>();
						exPath nuevo = new exPath(comienzo.getFila(),comienzo.getColumna(),cami);
						ruta.push(nuevo);
						primerainteracion = false;
						segundainteracion = true;
					}
					else if(segundainteracion && ruta.size() > 0)//desapilamos la cabeza y apilamos los diferentes lados
					{
						casillactual = ruta.pop();
						char tipo =  juego.consultaCasilla(casillactual.getCoordenadas());
						if(tipo == 'l' || tipo == 'c' ||(tipo == 'p' && juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'P'))//comienzo se trata igual que libre
						{
							//puesto que las puertas nunca se ponen como visitadas
							if(!(tipo == 'p' && juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'P'))
							{
								juego.cambiaraestado(casillactual.getCoordenadas(),'v');//ponemos en visitado esta posicion
							}
							
							//esto nos servira para saber si tenemos que salir
							if(modobuscarc == true && tipo == 'c')
							{
								nosalir = false;
								if(propiocontador == destinos)
								encuentroelfinal = true;
								else
								encuentroelfinal = false;
							}
							
							//apilamos en la pila las posiciones mientras que no sean obstaculos
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()-1),casillactual.getCoordenadas().getColumna())) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()-1),casillactual.getCoordenadas().getColumna())) != 'v')//norte
							{					
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()-1),casillactual.getCoordenadas().getColumna(),nuevocamino); 
								ruta.push(nuevopaso);
							}
							
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()+1),casillactual.getCoordenadas().getColumna())) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()+1),casillactual.getCoordenadas().getColumna())) != 'v')//sur
							{
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()+1),casillactual.getCoordenadas().getColumna(),nuevocamino); 
								ruta.push(nuevopaso);								
							}

							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()+1))) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()+1))) != 'v')//este
							{
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()+1),nuevocamino); 
								ruta.push(nuevopaso);								
							}
							
							if(juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()-1))) != 'o' || juego.consultaCasilla(new Coordenadas((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()-1))) != 'v')//oeste
							{
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath((casillactual.getCoordenadas().getFila()),(casillactual.getCoordenadas().getColumna()-1),nuevocamino); 
								ruta.push(nuevopaso);								
							}							
						}
						else if(tipo == 'p')//si es una puerta haremos lo que nos dicen
						{
							
							// directamente para la propiedad B no hacemos nada porque ya se ha desapilado esta opcion, la proxima vez ejecutara la siguiente opcion mas cercana siempre y cuando no este vacia la pila
							if(juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'S')
							{
								//visitamos destino mas cercano,siempre y cuando exista, si no volvemos al destino
								if(juego.buscardestinos() != null)
								{
									ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
									exPath nuevopaso = new exPath(juego.buscardestinos().firstElement().getCoordenadas().getFila(),juego.buscardestinos().firstElement().getCoordenadas().getColumna(),nuevocamino); 
									ruta.push(nuevopaso);								
								}
								else
								{
									ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
									exPath nuevopaso = new exPath(juego.getcomienzo().getFila(),juego.getcomienzo().getColumna(),nuevocamino); 
									ruta.push(nuevopaso);
									//encolamos
									if(caminos.esVacia())
										{caminos.insertaCabeza(nuevocamino);}
									else
										{caminos.insertaCola(nuevocamino);}
									
									encuentroelfinal = true;
									nosalir = false;
									//aqui fin de juego
								}
							}
							else if(juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'T')
							{
								
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								exPath nuevopaso = new exPath(juego.getcomienzo().getFila(),juego.getcomienzo().getColumna(),nuevocamino); 
								ruta.push(nuevopaso);
								//encolamos
								if(caminos.esVacia())
									{caminos.insertaCabeza(nuevopaso.getCamino());}
								else
									{caminos.insertaCola(nuevopaso.getCamino());}
								
								//reinicializamos y cambiamos el sentido de la marcha
								juego.cambiaraestado(casillactual.getCoordenadas(), 'T');
								juego.inicializartodolovistoalibre();//inicializamos todos los v a l
								primerainteracion = true;//volvemos al primer paso
								segundainteracion = false;
								comienzo = juego.getcomienzo();
								ruta = new Stack<exPath>();//borramos la pila, se encarga el recogedor de basura de java
								caidaentrampa = true;
							}
							else if(juego.consultaCasillaPropiedad(casillactual.getCoordenadas()) == 'X')
							{
								
								ArrayList<Coordenadas> nuevocamino = casillactual.getCamino();
								//encolamos
								if(caminos.esVacia())
									{caminos.insertaCabeza(nuevocamino);}
								else
									{caminos.insertaCola(nuevocamino);}
														
								encuentroelfinal = true;
								nosalir = false;
								//aqui fin de juego	
								
							}
						}
						else if(tipo == 'd')//si es un destino haremos lo que dice en implementacion
						{
							//insertamos el camino en la lista
							if(caminos.esVacia())
							{
								caminos.insertaCabeza(casillactual.getCamino());
							}
							else
							{
								caminos.insertaCola(casillactual.getCamino());
							}
							
							juego.cambiaraestado(casillactual.getCoordenadas(),'l');//cambiamos su estado de d a l
							
							juego.inicializartodolovistoalibre();//inicializamos todos los v a l
							
							comienzo = casillactual.getCoordenadas();//guardamos la posicion como el siguiente comienzo
							
							ruta = new Stack<exPath>();//borramos la pila, se encarga el recogedor de basura de java
							
							primerainteracion = true;//volvemos al primer paso
							segundainteracion = false;
							propiocontador++;
						}
						
						
					}
					else if(ruta.size() <= 0 && modobuscarc != true)//esto pasara si no hay nada en la pila y no se ha encontrado una puerta x, o se ha vuelto al comienzo
					{
						//haremos desde la posicion actual una ultima vuelta
						if(casillactual != null)
						{
							comienzo=casillactual.getCoordenadas();
							//reinicializamos todo a libre, menos las puertas
							juego.inicializartodolovistoalibre();//inicializamos todos los v a l
							//en la proxima interaccion empezara de nuevo pero esta vez si encuentra comienzo lo encolara y terminara, si no lo encuentra con el el modobuscarc en true terminara cuando este la pila vacia  
							primerainteracion = true;//volvemos al primer paso
							segundainteracion = false;
							modobuscarc = true;
						}
						else
						{
							modobuscarc = true;
						}
					}
					else if(ruta.size() <= 0 && modobuscarc != true)
					{
						//se termina el juego perdiendo
						nosalir = false;
						encuentroelfinal = false;
					}
				}
			}
			
			if(!encuentroelfinal)
			{
				System.out.println("HAS PERDIDO EL JUEGO");
			}
			
			if(!caminos.esVacia())
			{
				caminos.escribeLista();
			}
			
		}
	}

}
