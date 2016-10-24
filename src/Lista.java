import java.util.ArrayList;


public class Lista {

	private NodoL pr;
	private NodoL ul;
	
	//inicializamos variables a null
	public Lista() {
		// TODO Auto-generated constructor stub
		pr = null;
		ul = null;
	}
	
	public boolean esVacia()
	{
		if(pr == null && ul == null)
		{
			return true;
		}
		
		return false;
	}
	
	public void insertaCabeza(ArrayList<Coordenadas> v)
	{
		if(pr != null)
		{
			NodoL nodo = pr;
			pr = new NodoL (v);
			pr.
		}
		else
		{
			this.pr = new NodoL(v);
		}
	}
	
	public void insertaCola(ArrayList<Coordenadas> v)
	{
		
	}
	
	public void inserta(ArrayList<Coordenadas> v, int i) throws IndexOutOfBoundsException
	{
		
	}
	
	public boolean borraCabeza()
	{
		return false;
	}
	
	public void borra(int i) throws IndexOutOfBoundsException
	{
		
	}
	
	public boolean borra(ArrayList<Coordenadas> v)
	{
		return false;
	}
	
	public void escribeLista()
	{
		
	}
	
	public int enLista(ArrayList<Coordenadas> v)
	{
		return -1;
	}
	
	public ArrayList<Coordenadas> getCamino(int pos) throws IndexOutOfBoundsException
	{
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
		
		public void escribeCamino()
		{
			if(this.camino != null)
			{
				for(Coordenadas coor : camino)
				{
					System.out.print(coor.getInfo());
				}
				//System.getProperties(); preguntar si necesita retorno de carro
			}			
		}
		
		public boolean comparaCamino(ArrayList<Coordenadas> v)
		{
			if(v != null && this.getCamino() != null)
			{
				if(this.getCamino().toString().compareTo(v.toString()) == 0)
				{
					return true;
				}
			}
			return false;
		}
	}
}
