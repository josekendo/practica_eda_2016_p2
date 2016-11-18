import java.util.ArrayList;


public class exPath {

	private Coordenadas coor;
	private ArrayList<Coordenadas> camino;
	
	public exPath(int x, int y, ArrayList<Coordenadas> v)
	{
		// TODO Auto-generated constructor stub
		coor = new Coordenadas(x,y);
		if(v != null)
		{
			int tam = v.size();
			ArrayList<Coordenadas> copia = new ArrayList<Coordenadas>(tam);
			for(int f=0;f < tam;f++)
			{
				copia.add(v.get(tam));
			}
		}
	}
	
	public Coordenadas getCoordenadas()
	{
		return coor;
	}
	
	public ArrayList<Coordenadas> getCamino()
	{
		return camino;
	}
}