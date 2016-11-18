import java.util.Vector;


public class Exploracion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args[0] != null && args[1] != null && args[2] != null)
		{
			Plano juego = new Plano(Integer.parseInt(args[1]),Integer.parseInt(args[2]));//creamos el plano con sus dimensiones vacio
			juego.leePlano(args[0]);//Leemos el plano por primera vez
			Boolean nosalir = true;//No se saldra hasta que encuentre todos los caminos hasta llegar a la puerta
			Lista caminos = new Lista();//Esto almacenara nuestros caminos realizados
			while(nosalir)
			{
				camino = algoritmo(juego);
				if(l != null)
				{
					caminos.add(l);
				}
				else
				{
					nosalir = false;
				}	
			}
			
			if(caminos.size() == 0)
			{
				System.out.println("HAS PERDIDO EL JUEGO");
			}
		}
	}
	
	//New en este metodo hacemos la logica de la aplicacion
	public static Lista algoritmo(Plano p)
	{
		
		return null;
	}

}
