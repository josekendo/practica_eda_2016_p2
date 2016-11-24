import java.util.ArrayList;


public class exPath {

	private Coordenadas coor;//posicion actual
	private ArrayList<Coordenadas> camino;//camino recorrido al llegar a esta posicion(incluyendose asi misma) que sera coor
	
	public exPath(int x, int y, ArrayList<Coordenadas> v)
	{
		// TODO Auto-generated constructor stub
		coor = new Coordenadas(x,y);
		if(v != null)
		{
			int tam = v.size();
			ArrayList<Coordenadas> copia = new ArrayList<Coordenadas>(tam+1);
			for(int f=0;f < tam;f++)
			{
				copia.add(v.get(tam));
			}
			copia.add(coor);//agregamos nuestra propia coordenada
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