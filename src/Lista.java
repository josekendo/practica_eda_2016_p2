import java.util.ArrayList;

import org.junit.Test;


public class Lista {

	private NodoL pr;
	private NodoL ul;
	
	//inicializamos variables a null
	public Lista() 
	{
		// TODO Auto-generated constructor stub
		pr = null;
		ul = null;
	}
	
	@Test
	//Comprobamos si hay algo en la cabeza de la lista, si no hay algo devolvemos false
	public boolean esVacia()
	{
		if(pr == null)
		{
			return true;
		}
		
		return false;
	}
	
	@Test
	//comprobamos que v no sea nula, si lo es no hacemos nada, si no lo es cambiamos el primero por el nuevo y en el next del nuevo ponemos el pr original
	public void insertaCabeza(ArrayList<Coordenadas> v)
	{
		if(v != null)
		{
			if(this.pr != null)
			{
				NodoL nodo = this.pr;
				NodoL nodonuevo = null;
				nodonuevo = new NodoL (v);
				nodonuevo.cambiaNext(nodo);
				this.pr = nodonuevo;
			}
			else
			{
				this.pr = new NodoL(v);
			}
		}
	}
	
	@Test
	//comprobamos que no sea nulo v, si no lo es cambiamos el ultimo por este ultimo, poniendo en siguiente del ultimo original al nuevo nodo ultimo.
	public void insertaCola(ArrayList<Coordenadas> v)
	{
		if(v != null)
		{
			if(this.ul != null)
			{
				NodoL nodo = this.ul;
				NodoL nodonuevo = null;
				nodonuevo = new NodoL (v);
				nodo.cambiaNext(nodonuevo);
				this.ul = nodonuevo;
			}
			else
			{
				this.ul = new NodoL(v);
			}
		}
	}
	
	@Test
	//si existe lo ponemos en esa posicion, si es la cabeza lo ponemos en la cabeza, si es la cola lo ponemos en la cola
	public void inserta(ArrayList<Coordenadas> v, int i) throws IndexOutOfBoundsException
	{
		if(v != null)
		{
			if(pr != null && ul != null)
			{
				NodoL siguiente=pr;
				NodoL anterior = null;
				NodoL escogido= null;
				int contador = 0;
				
				while(siguiente != null)
				{
						if(i == contador)
						{
							escogido = siguiente;
							siguiente = null;
						}
						else
						{
							if(siguiente != null && siguiente.next != null)
							{
								contador++;
								anterior = siguiente;
								siguiente = siguiente.next;
							}
							else
							{
								//cuando este se ejecute es que hemos llegado al final de la lista
								siguiente = null;
							}
						}
					}
				
					if(escogido != null)
					{
						NodoL nuevonodo = new NodoL(v);
						NodoL next = escogido.next;
						
						if(anterior != null)
						{
							anterior.cambiaNext(nuevonodo);
						}
						
						nuevonodo.next = next;
						
						//aqui miramos si es la cabeza o es la cola 
						
						//esto significa que es la cabeza
						if(anterior == null)
						{
							pr = nuevonodo;
						}
						
						//esto significa que es la cabeza
						if(next == null)
						{
							ul = nuevonodo;
						}
						
					}
					else
					{
						throw new IndexOutOfBoundsException();
					}
				}
			}
			else if(pr == null)
			{
	            throw new IndexOutOfBoundsException();
			}
	}

	@Test
	//si la cabeza es nula devolvemos false, si no lo es comprobamos si la cabeza tiene nodos sucesores si los tiene ponemos al primer sucesor como cabeza y devolvemos true, si no tiene sucesor ponemos la variable de instancia pr a null
	public boolean borraCabeza()
	{
		if(pr != null && ul != null)
		{
			if(pr.next != null)
			{
				//esto significa que tenemos que almacenar su sucesor para ponerlo como el primero, el primero al no tener referencia se borrara
				pr = pr.next;
				return true;
			}
			else
			{
				//esto significa que no tiene nada en next por lo que solo hace falta ponerlo a null en ul y pr puesto que es el unico nodo
				pr = null;
				ul = null;
				return true;
			}
		}

		return false;
	}
	
	@Test
	//si la variable de instancia ul es nula devolvemos false, si no lo es nos recorremos toda la lista para encontar el antecesor de la cola y ponemos el next a nulo luego ponemos ese como ultimo y devolvemos
	public boolean borraCola()
	{
		if(ul != null && pr != null)
		{
			NodoL siguiente = pr;
			NodoL anterior = null;
			int contador = -1;
			
			while(siguiente != null)
			{
				if(contador != -1)
				{
					anterior = siguiente;
				}
				
				siguiente = siguiente.next; 
				contador++;
			}
			
			if(anterior != null && siguiente == null)//esto es que la lista tiene cabeza y cola diferente, pero tambien es que su antecesor que ahora sera la cola
			{
				anterior.next = null;
				ul = anterior;
				return true;
			}
			else if(anterior == null && siguiente == null)//esto significa que la cola y la cabeza son el mismo nodo, entonces se ponen ambas a nulo
			{
				pr = null;
				ul = null;
				return true;
			}
			
		}
		
		return false;
	}
	
	@Test
	//es igual que insertar menos que cambias la direccion de anterior por la nueva, teniendo en cuenta que sea el primero y/o el ultimo
	public void borra(int i) throws IndexOutOfBoundsException
	{
		if(i > -1)
		{
			if(pr != null && ul != null)
			{
				NodoL siguiente=pr;
				NodoL anterior = null;
				NodoL escogido= null;
				int contador = 0;
				
				while(siguiente != null)
				{
						if(i == contador)
						{
							escogido = siguiente;
							siguiente = null;
						}
						else
						{
							if(siguiente != null && siguiente.next != null)
							{
								contador++;
								anterior = siguiente;
								siguiente = siguiente.next;
							}
							else
							{
								//cuando este se ejecute es que hemos llegado al final de la lista
								siguiente = null;
							}
						}
				}
				
					if(escogido != null)
					{
						
						if(anterior == null)//si fuera nulo seria el primero, 2 opciones que sea solo la cabeza o que sea cabeza y ultima
						{
							if(escogido.next != null)//esto significa que es la cabeza pero no es la cola
							{
								pr = escogido.next; 
							}
							else //significa que escogido es la cabeza y la cola
							{
								pr = null;
								ul = null;
							}
						}
						else // si anterior no es nulo significa que puede ser la cola o ser un nodo intermedio
						{
							if(escogido.next == null) //significa que es la cola
							{
								anterior.next = null;
								ul = anterior;
							}
							else //esto significa que es un nodo intermedio
							{
								anterior.next = escogido.next;
							}
						}
						
					}
					else
					{
						throw new IndexOutOfBoundsException();
					}
				}
			}
			else if(pr == null)
			{
	            throw new IndexOutOfBoundsException();
			}
	}
	
	@Test
	//borra el primer nodo que el arraylist sea igual a v, es casi identico al anterior pero cambiando los returns
	public boolean borra(ArrayList<Coordenadas> v)
	{
		if(v != null)
		{
			if(pr != null && ul != null)
			{
				NodoL siguiente=pr;
				NodoL anterior = null;
				NodoL escogido= null;
				
				while(siguiente != null)
				{
						if(siguiente.comparaCamino(v))
						{
							escogido = siguiente;
							siguiente = null;
						}
						else
						{
							if(siguiente != null && siguiente.next != null)
							{
								anterior = siguiente;
								siguiente = siguiente.next;
							}
							else
							{
								//cuando este se ejecute es que hemos llegado al final de la lista
								siguiente = null;
							}
						}
				}
				
					if(escogido != null)
					{
						
						if(anterior == null)//si fuera nulo seria el primero, 2 opciones que sea solo la cabeza o que sea cabeza y ultima
						{
							if(escogido.next != null)//esto significa que es la cabeza pero no es la cola
							{
								pr = escogido.next; 
								return true;
							}
							else //significa que escogido es la cabeza y la cola
							{
								pr = null;
								ul = null;
								return true;
							}
						}
						else // si anterior no es nulo significa que puede ser la cola o ser un nodo intermedio
						{
							if(escogido.next == null) //significa que es la cola
							{
								anterior.next = null;
								ul = anterior;
								return true;
							}
							else //esto significa que es un nodo intermedio
							{
								anterior.next = escogido.next;
								return true;
							}
						}
						
					}
					else
					{
						return false;
					}
				}
			}
		
			return false;
	}
	
	@Test
	//nos recorremos uno a uno los diferentes caminos sacandos sus coordenadas (las que no sean nulos, puesto que el enunciado no indica que hacer con las coordenadas que sean nulas)
	public void escribeLista()
	{
		if(ul != null && pr != null)
		{
			NodoL siguiente = pr;
			int contador = 1;
			
			while(siguiente != null)
			{
				String numerocamino = "Camino "+contador+": ";
				String caminopasos = "";
				if(siguiente.camino != null)
				{
					for(Coordenadas coordenada:siguiente.camino)
					{
						if(coordenada != null)
						{
							caminopasos=caminopasos+coordenada.getInfo();
						}
					}
					System.out.println(numerocamino+caminopasos);
				}
				siguiente = siguiente.next; 
				contador++;
			}
		}
	}
	
	@Test
	//nos recorremos la lista buscando el nodo que contenga el camino v, si lo encontramos devolvemos en que posicion lo hemos encontrado
	public int enLista(ArrayList<Coordenadas> v)
	{
		if(ul != null && pr != null && v != null)
		{
			NodoL siguiente = pr;
			int contador = -1;
			
			while(siguiente != null)
			{
				contador++;
				if(siguiente.comparaCamino(v))
				{
					siguiente = null;
				}
				else
				{
					siguiente = siguiente.next;
					if(siguiente == null)
					{
						contador = -1;
					}
				}
			}
			
			return contador;
		}
		
		return -1;
	}
	
	@Test
	public ArrayList<Coordenadas> getCamino(int pos) throws IndexOutOfBoundsException
	{
		if(pr != null && ul != null && pos > -1)
		{
			NodoL siguiente=pr;
			NodoL escogido= null;
			int contador = 0;
			
			while(siguiente != null)
			{
					if(pos == contador)
					{
						escogido = siguiente;
						siguiente = null;
					}
					else
					{
						if(siguiente != null && siguiente.next != null)
						{
							contador++;
							siguiente = siguiente.next;
						}
						else
						{
							//cuando este se ejecute es que hemos llegado al final de la lista
							siguiente = null;
						}
					}
			}
			
				if(escogido != null)
				{
					if(escogido.getCamino() != null)
					{
						return escogido.getCamino();
					}
				}
				else
				{
					throw new IndexOutOfBoundsException();
				}			
		}
		throw new IndexOutOfBoundsException();
	}
	
	//new sirve para devolver la cabeza o la cola
	public NodoL getDatos(int op)
	{
		if(op == 1)
		{
			return this.pr;
		}
		if(op == 2)
		{
			return this.ul;
		}
		
		return null;
	}
	
	private class NodoL
	{
		private ArrayList<Coordenadas> camino;
		private NodoL next;
		
		public NodoL( ArrayList<Coordenadas> v)
		{
			camino = v;
			next = null;
		}
		
		@SuppressWarnings("unused")
		public NodoL( ArrayList<Coordenadas> v, NodoL n)
		{
			camino = v;
			next = n;			
		}
		
		public void cambiaNext(NodoL n)
		{
			if(n != null)
			{
				this.next=n;
			}
		}
		
		public ArrayList<Coordenadas> getCamino()
		{
			return this.camino;
		}
		
		@SuppressWarnings("unused")
		public void escribeCamino()
		{
			if(this.camino != null)
			{
				for(Coordenadas coor : camino)
				{
					if(coor != null)
					{
						System.out.print(coor.getInfo());
					}
				}
				//System.getProperties(); preguntar si necesita retorno de carro
			}			
		}
		
		//comprobamos que la longitud de los caminos sean la misma, que no sean nulos, y si es asi revisamos uno a uno en su orden para ver si son iguales
		public boolean comparaCamino(ArrayList<Coordenadas> v)
		{
			if(v != null && this.camino != null)
			{
				if(v.size() != this.camino.size())
				{
					return false;
				}
				
				int contador = 1;
				int contador2 = 1;
				Boolean esigual = true;
				for(Coordenadas coorde:v)
				{
					String viejo="";
					String	nuevo="";
					
					if(coorde != null)
					{
						viejo = coorde.getInfo();
					}
					else
					{
						viejo = null;
					}
					
					for(Coordenadas coordec:this.camino)
					{
						if(coordec != null)
						{
							nuevo = coordec.getInfo();
						}
						else
						{
							nuevo = null;
						}
						
						if(contador == contador2)
						{
							if(nuevo != null && viejo != null)
							{
								if(nuevo.compareToIgnoreCase(viejo) != 0)
								{
									esigual = false;
								}
							}
							else if(nuevo != null && viejo == null)
							{
								esigual = false;
							}
							else if(nuevo == null && viejo != null)
							{
								esigual = false;
							}
						}
						
						contador2++;
					}
					
					contador++;
				}
				
				return esigual;
			}
			return false;
		}
	}
}
