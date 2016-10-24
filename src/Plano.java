//DNI 48767995z ANTON COY, JOSE VICENTE

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;


public class Plano 
{
	private Casilla[][] pl;
	private Coordenadas dimension;
	
	//si es menor que cero ponemos tres en su lugar, si no es asi le ponemos su numero, i para las filas y j para las columnas
	public Plano(int i, int j) 
	{
		// TODO Auto-generated constructor stub
		int ip,jp;
		
		if(i < 0)
		{
			ip = 3;
		}
		else
		{
			ip = i;
		}
		
		if(j < 0)
		{
			jp = 3;
		}
		else
		{
			jp = j;
		}
		
		pl = new Casilla[ip][jp];
		this.dimension = new Coordenadas(ip,jp);
	}
	
	//comprobamos que las coordenadas sean correctas y de que no haya nada en es posicion
	public boolean setCasilla(Casilla x)
	{
		
	if(x.getCoordenadas() != null && this.dimension != null)
		{
			if(x.getCoordenadas().getFila() <= this.dimension.getFila()-1 && x.getCoordenadas().getColumna() <= this.dimension.getColumna()-1 && x.getCoordenadas().getColumna() > -1 && x.getCoordenadas().getFila() > -1)
			{
				if(pl[x.getCoordenadas().getFila()][x.getCoordenadas().getColumna()] != null)
				{
					return false;
				}
				else
				{
					pl[x.getCoordenadas().getFila()][x.getCoordenadas().getColumna()] = x;
					return true;
				}
			}
		}
		return false;
	}
	
	
	//desde aqui leemos el archivo que se pasa por f
	public void leePlano(String f)
	{
		if(f == null || f.length() < 1)
		{
			///vacio
		}
		else
		{
			String [] planoleido = this.leerdicc(f);
			int numerodecolumnasmapa = dimension.getColumna();
			int numerodefilasmapa = dimension.getFila();
			if(planoleido[0].compareToIgnoreCase("<MAPA>") == 0)
			{
				for(int c=1;c < (planoleido.length);c++)
				{
					if(c == 1)
					{
						if(planoleido[c].toString().charAt(0) != '<')
						{
							numerodecolumnasmapa = planoleido[c].toString().length();//esto determinara la longitud de columnas	
							numerodefilasmapa=1;//hemos leido la fila 1
						}
						else
						{
							break;
						}
					}
					else
					{
						if(planoleido[c].toString().charAt(0) != '<')
						{
							numerodefilasmapa++;//leemos la fila c
						}
						else
						{
							break;//prevenimos de que no se siga leyendo si se acaba la lectura de la zona <MAPA>,redimensionariamos la matriz de pl con las columnas y filas recogidas asi sabriamos exactamente cuantas filas hay y empezar a introducir datos limpiamente
						}
					}
				}
				
				//tenemos que redimensionar si las columnas son mayores o si las filas lo son.
				if(numerodecolumnasmapa != this.dimension.getColumna() || numerodefilasmapa != dimension.getFila())
				{
					boolean redimensionarx = false;
					boolean redimensionary = false;
					
					//redimensionamos en el caso de que sea mayor
					if(numerodecolumnasmapa > this.dimension.getColumna())
					{
						 redimensionary = true;
					}
					
					//redimensionamos en el caso de que sea mayor
					if(numerodefilasmapa > dimension.getFila())
					{
						redimensionarx = true;
					}
					
					//el metodo redimensionar se encarga de redimensionar la matrix dejando todos los valores existentes iguales
					if(redimensionarx && redimensionary)
					{
						redimensionar(numerodefilasmapa,numerodecolumnasmapa);
					}
					else if(redimensionarx)
					{
						redimensionar(numerodefilasmapa,-1);
					}
					else if(redimensionary)
					{
						redimensionar(-1,numerodecolumnasmapa);
					}
				}
			}
			//aqui ya solo nos faltaria meter los datos en la matriz
			boolean dondeterminamapa=true;
			int numerodelecturasiguiente=-1;
			for(int k=1; k < planoleido.length;k++)
			{
				if(planoleido[k].toString().charAt(0) != '<' && dondeterminamapa)
				{
					for(int j=0;j < this.pl[k-1].length;j++)
					{
						if(j < (planoleido[k].toString().length()))
						{
							if(pl[k-1][j] == null || pl[k-1][j].getTipo() == 'l')
							{
								if(planoleido[k].toString().charAt(j) == 'l' || planoleido[k].toString().charAt(j) == 'o')
								{
									this.pl[k-1][j] = new Casilla(planoleido[k].toString().charAt(j));
									this.pl[k-1][j].setCoordenadas((k-1), j, this);
								}
								else
								{
									this.pl[k-1][j] = new Casilla('l');
									this.pl[k-1][j].setCoordenadas((k-1), j, this);
								}
							}
						}
						else
						{
							if(pl[k-1][j] == null || pl[k-1][j].getTipo() == 'l')
							{
								this.pl[k-1][j] = new Casilla('l');
								this.pl[k-1][j].setCoordenadas((k-1), j, this);
							}
						}
					}
				}
				else
				{
					if(dondeterminamapa != false)
					{
						numerodelecturasiguiente= k;
						dondeterminamapa = false;
					}
				}
			}

			//ahora si hay mas datos los vamos creando
			if(numerodelecturasiguiente != -1)
			{
				for(int l=numerodelecturasiguiente; l < planoleido.length;l++)
				{	
					if(planoleido[l].compareToIgnoreCase("<DESTINO>") == 0)
					{
						//dos lineas
						if((l+1) < planoleido.length && (l+2) < planoleido.length && planoleido[l+1].matches("^[a-zA-Z]+$") && planoleido[l+2].matches("^[0-9]+"+" "+"[0-9]+$"))
						{
							if(Integer.parseInt(planoleido[l+2].substring(0,1)) < dimension.getFila() && Integer.parseInt(planoleido[l+2].substring(2,3)) < dimension.getColumna() && (pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))].getTipo() == 'l' || pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))] == null))
							{
								Casilla nueva = new Casilla('d');
								nueva.setNombre(planoleido[l+1]);
								nueva.setCoordenadas(Integer.parseInt(planoleido[l+2].substring(0, 1)),Integer.parseInt(planoleido[l+2].substring(2, 3)),this);
								this.pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))]=nueva;
							}
						}		
					}
					else if(planoleido[l].compareToIgnoreCase("<PUERTA>") == 0)
					{
						//tres lineas
						if((l+1) < planoleido.length && (l+2) < planoleido.length && (l+3) < planoleido.length && planoleido[l+1].matches("^[0-9]+$") && planoleido[l+2].matches("^[0-9]+"+" "+"[0-9]+$") && planoleido[l+3].matches("^[a-zA-Z]$"))
						{
							if(Integer.parseInt(planoleido[l+2].substring(0,1)) < dimension.getFila() && Integer.parseInt(planoleido[l+2].substring(2,3)) < dimension.getColumna() && (pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))].getTipo() == 'l' || pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))] == null))
							{
								Casilla nueva = new Casilla('p');
								nueva.setPuerta(Integer.parseInt(planoleido[l+1]),planoleido[l+3].charAt(0));
								nueva.setCoordenadas(Integer.parseInt(planoleido[l+2].substring(0, 1)),Integer.parseInt(planoleido[l+2].substring(2, 3)),this);
								this.pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))]=nueva;
							}
						}		
					}
					else if(planoleido[l].compareToIgnoreCase("<COMIENZO>") == 0)
					{
						//dos lineas
						if((l+1) < planoleido.length && (l+2) < planoleido.length && planoleido[l+1].matches("^[a-zA-Z]+$") && planoleido[l+2].matches("^[0-9]+"+" "+"[0-9]+$"))
						{
							
							if(Integer.parseInt(planoleido[l+2].substring(0,1)) < dimension.getFila() && Integer.parseInt(planoleido[l+2].substring(2,3)) < dimension.getColumna() && (pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))].getTipo() == 'l' || pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))] == null))
							{
								Casilla nueva = new Casilla('c');
								nueva.setNombre(planoleido[l+1]);
								nueva.setCoordenadas(Integer.parseInt(planoleido[l+2].substring(0, 1)),Integer.parseInt(planoleido[l+2].substring(2, 3)),this);
								this.pl[Integer.parseInt(planoleido[l+2].substring(0,1))][Integer.parseInt(planoleido[l+2].substring(2,3))]=nueva;
							}
						}								
					}
				}
			}	
		}
		rellenarnulos();
	}
	
	//comprobamos que las coordenadas que nos pasan esten correctas y no sean nulas, si es asi devolvemos el tipo
	public char consultaCasilla(Coordenadas x)
	{
		if(x != null && x.getFila() >= 0 && x.getColumna() >= 0 && x.getFila() < this.pl.length && x.getColumna() < this.dimension.getColumna() && this.pl[x.getFila()][x.getColumna()] != null)
		{
			return pl[x.getFila()][x.getColumna()].getTipo();
		}
		return 'F';
	}
	
	//comprobamos las casillas vecinas devolviendo un array de char de todas las que no esten dentro del plano
	public char[] consultaVecinas(Coordenadas x)
	{
		if(x != null && x.getFila() >= 0 && x.getColumna() >= 0 && x.getFila() < this.pl.length && x.getColumna() < this.pl[0].length && this.pl[x.getFila()][x.getColumna()] != null)
		{
			//esto hay que mirar que valores cambian
			String palabra="";
		
			if((x.getFila()-1) <= dimension.getFila()-1 && x.getColumna() <= this.dimension.getColumna()-1 && (x.getFila()-1) >= 0 && x.getColumna() >= 0)
			{
				if(this.pl[x.getFila()-1][x.getColumna()] != null)
				palabra = palabra+this.pl[x.getFila()-1][x.getColumna()].getTipo();
				else
				palabra = palabra+"F";
				
			}
			
			if((x.getFila()-1) <= dimension.getFila()-1 && (x.getColumna()+1) <= this.dimension.getColumna()-1 && (x.getFila()-1) >= 0 && x.getColumna()+1 >= 0)
			{
				if(this.pl[x.getFila()-1][x.getColumna()+1] != null)
				palabra = palabra+this.pl[x.getFila()-1][x.getColumna()+1].getTipo();
				else
				palabra = palabra+"F";
			}
			
			if((x.getFila()) <= dimension.getFila()-1 && x.getColumna()+1 <= this.dimension.getColumna()-1 && (x.getFila()) >= 0 && x.getColumna()+1 >= 0)
			{
				if(this.pl[x.getFila()][x.getColumna()+1] != null)
				palabra = palabra+this.pl[x.getFila()][x.getColumna()+1].getTipo();
				else
				palabra = palabra+"F";
			}
			
			if((x.getFila()+1) <= dimension.getFila()-1 && x.getColumna()+1 <= this.dimension.getColumna()-1 && (x.getFila()+1) >= 0 && x.getColumna()+1 >= 0)
			{
				if(this.pl[x.getFila()][x.getColumna()+1] != null)
				palabra = palabra+this.pl[x.getFila()+1][x.getColumna()+1].getTipo();
				else
				palabra = palabra+"F";
			}
			
			if((x.getFila()+1) <= dimension.getFila()-1 && x.getColumna() <= this.dimension.getColumna()-1 && (x.getFila()+1) >= 0 && x.getColumna() >= 0)
			{
				if(this.pl[x.getFila()+1][x.getColumna()] != null)
				palabra = palabra+this.pl[x.getFila()+1][x.getColumna()].getTipo();
				else
				palabra = palabra+"F";
			}
			
			if((x.getFila()+1) <= dimension.getFila()-1 && x.getColumna()-1 <= this.dimension.getColumna()-1 && (x.getFila()+1) >= 0 && x.getColumna()-1 >= 0)
			{
				if(this.pl[x.getFila()+1][x.getColumna()-1] != null)
				palabra = palabra+this.pl[x.getFila()+1][x.getColumna()-1].getTipo();
				else
				palabra = palabra+"F";

			}
			
			if((x.getFila()) <= dimension.getFila()-1 && x.getColumna()-1 <= this.dimension.getColumna()-1 && (x.getFila()) >= 0 && x.getColumna()-1 >= 0)
			{
				if(this.pl[x.getFila()][x.getColumna()-1] != null)
				palabra=palabra+this.pl[x.getFila()][x.getColumna()-1].getTipo();
				else
				palabra = palabra+"F";
			}
			
			if((x.getFila()-1) <= dimension.getFila()-1 && x.getColumna()-1 <= this.dimension.getColumna()-1 && (x.getFila()-1) >= 0 && x.getColumna()-1 >= 0)
			{
				if(this.pl[x.getFila()][x.getColumna()-1] != null)
				palabra= palabra+this.pl[x.getFila()-1][x.getColumna()-1].getTipo();
				else
				palabra = palabra+"F";
			}
			
			boolean comprobacion = false;
			char retorno[]=new char[palabra.length()];
			for(int h=0;h < palabra.length();h++)
			{
				retorno[h]=palabra.charAt(h);
				comprobacion = true;
			}
			
			if(comprobacion)
			{
				return retorno;
			}
		}
		return null;
	}
	
	//nos recorremos el plano mostrando las posiciones
	public void muestraPlano()
	{
		if(this.pl != null && pl.length > 0)
		{
			for(int d=0; d < pl.length;d++)
			{
				String linea="";
				for(int e=0; e < pl[d].length;e++)
				{
					if(pl[d][e] != null)
					{
						linea=linea+pl[d][e].getTipo();
					}
					else
					{
						linea=linea+"F";
					}
				}
				System.out.println(linea);
			}
		}
	}
	
	//comprobamos que no sea nulo y que tengan las mismas dimensiones,si es asi pasamos a comprovar con el equals cada una de las casillas
	public boolean equals(Plano compara)
	{
		
		if(compara != null && this.dimension.getColumna() == compara.dimension.getColumna() && this.dimension.getFila() == compara.dimension.getFila())
		{
			boolean esverdad = true;
			for(int x=0;x < this.pl.length; x++)
			{
				if(0 != this.dimension.getColumna())
				{
					for(int z=0;z < this.pl[x].length; z++)
					{
						if(!this.pl[x][z].equals(compara.pl[x][z]))
						{
							esverdad=false;
						}
					}
				}
			}
			if(esverdad)
			{
				return true;
			}
		}
		//luego comprobar una a una si la casilla es igual en funcion del tipo que sea
		//en el momento que una no sea igual se sale del for retornado false si termina el recorrido se devuelve true
		return false;
	}
	
	//NEW este metodo se escarga de recibir un texto y leerlo devolviendo un array de strings donde cada posicion es una linea
	public String[] leerdicc(String nombrefichero)//ok
	{
		if(nombrefichero == " " || nombrefichero == "" || nombrefichero == null)
		{
			return null;
		}
		
		String [] diccionario;
		FileReader fichero=null;
		BufferedReader lectura=null;
		String diccencadena=null;
		try
		{
			fichero=new FileReader(nombrefichero);
			lectura =new BufferedReader(fichero);
			String linea = lectura.readLine();
			while(linea != null)
			{
				if(linea.length() > 0)
				{
					if(linea.length() > 0)
					{
						if(diccencadena == null)
						{
							diccencadena=linea;
						}
						else
						{
							diccencadena=diccencadena+"@dicc"+linea;
						}
					}
				}
				linea = lectura.readLine();
			}
			diccionario= diccencadena.split("@dicc");
		}
		catch(IOException e)
		{
			System.out.println(e);
			return null;
		}
		
		try
		{
			if (fichero!=null)
			{
				fichero.close();
			}
			if (lectura!=null)
			{
				lectura.close();
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex);
		}
		return diccionario;
	}
	
	//NEW este metodo se encarga de redimensionar la matrix sin perder datos utilizando un doble for para  recorrer el viejo y el nuevo
	public void redimensionar(int x, int y)
	{
		int ax=0,by=0;
		if(x != -1 && y != -1)
		{
			ax=x;
			by=y;
		}
		else if(x != -1)
		{
			ax=x;
			by=this.dimension.getColumna();
		}
		else if(y != -1)
		{
			by=y;
			ax=this.dimension.getFila();	
		}
		
		Casilla[][] nuevamatrix = new Casilla[ax][by];
		
		for(int h=0;h < this.pl.length;h++)
		{
			for(int i=0;i < this.pl[h].length;i++)
			{
				nuevamatrix[h][i] = pl[h][i]; 
			}	
		}
		
		dimension = new Coordenadas(ax,by);
		pl = nuevamatrix;
	}
	
	//NEW rellena todos los campos nulos a l
	public void rellenarnulos()
	{
		for(int t=0;t < pl.length;t++)
		{
			for(int tw=0;tw < pl[t].length;tw++)
			{	
				if(pl[t][tw] == null)
				{
					pl[t][tw] = new Casilla('l');
					pl[t][tw].setCoordenadas(t,tw, this);
				}
			}
		}
	}
	
	//NEW funcion que busca y devuelve las casillas que sean destinos, retorna null si es imposible encontrar un destino
	public Vector<Casilla> buscardestinos()
	{
		Vector<Casilla> destinos = new Vector<Casilla>(1,1);
		for(int g=0;g < this.dimension.getFila();g++)
		{
			for(int f=0;f < this.dimension.getColumna();f++)
			{
				if(pl[g][f].esDestino() && pl[g][f].getNombre().matches("^[a-zA-Z]+$"))
				{
					destinos.add(pl[g][f]);
				}
			}
		}
		
		if(destinos.size() >= 1)
		{
			return destinos;
		}
		return null;
	}
	
	//NEW funcion que busca los destinos mas cercanos el uno del otro y devuelve el resultado en formato de string[]
	public String comparardestinos(Vector<Casilla> c)
	{
		int tam = c.size();
		String mascorto = "";
		int [] valores= new int[tam];
		for(int u=0;u < tam;u++)
		{
			Casilla origen = c.get(u);
			int valor=0;
			for(int y=0;y < tam;y++)
			{
				if(y != u)
				{
					if(c.get(u).esDestino()&& c.get(y).esDestino())
					{
						if(c.get(u).getCoordenadas() != null && c.get(y).getCoordenadas() != null)
						{
							int x1=origen.getCoordenadas().getFila();
							int x2=c.get(y).getCoordenadas().getFila();
							int y1=origen.getCoordenadas().getColumna();
							int y2=c.get(y).getCoordenadas().getColumna();
							int resta1 = x1 - x2;
							int resta2 = y1 - y2;
							int suma = Math.abs(resta1)+Math.abs(resta2);
							valor=valor+suma;	
						}
					}
				}
			}
			valores[u]=valor;
		}
		boolean compro=false;
		
		if(c != null)
		{
			int anterior=-1;
			for(int p=0;p < valores.length;p++)
			{
				if(valores[p] != 0)
				{
					if(anterior != -1 && valores[p] < anterior )
					{
						mascorto = "1:"+valores[p]+"-"+c.get(p).getCoordenadas().getInfo();				
						compro=true;
						anterior = valores[p];
					}
					else if(anterior == -1)
					{
						anterior = valores[p];
						compro = true;
					}
					else if(anterior != -1 && valores[p] == anterior)
					{
						mascorto = mascorto+c.get(p).getCoordenadas().getInfo();
						compro = true;
					}
				}
			}
			
			if(compro)
			{
				return mascorto;
			}
		}
		
		return null;
	}
	
	//NEW funcion que calcula todos las distancias de todas las casillas a los destinos, modo 2 te saca todos los costes, modo 1 solo los mas cercanos
	public String compararplano(Vector<Casilla> c,int modo)
	{
		Vector <Casilla>cuadrados = this.matrizavector();
		int tam = c.size();
		int tamx =cuadrados.size();
		String mascorto = "";
		int [] valores= new int[tamx];
		for(int y=0;y < tamx;y++)
		{
			int valor=0;
			for(int u=0;u < tam;u++)
			{						
							int x1=cuadrados.get(y).getCoordenadas().getFila();
							int x2=c.get(u).getCoordenadas().getFila();
							int y1=cuadrados.get(y).getCoordenadas().getColumna();
							int y2=c.get(u).getCoordenadas().getColumna();
							int resta1 = x1 - x2;
							int resta2 = y1 - y2;
							int suma = Math.abs(resta1)+Math.abs(resta2);
							valor=valor+suma;	
			}
			valores[y]=valor;
		}
		boolean compro=false;
		
		if(modo == 1)
		{
			if(c != null)
			{
				int anterior=-1;
				for(int p=0;p < valores.length;p++)
				{
					if(valores[p] != 0)
					{
						if(anterior != -1 && valores[p] < anterior )
						{
							mascorto = "1:"+valores[p]+"-"+cuadrados.get(p).getCoordenadas().getInfo();				
							compro=true;
							anterior = valores[p];
						}
						else if(anterior == -1)
						{
							anterior = valores[p];
							compro = true;
						}
						else if(anterior != -1 && valores[p] == anterior)
						{
							mascorto = mascorto+cuadrados.get(p).getCoordenadas().getInfo();
							compro = true;
						}
					}
				}
				
				if(compro)
				{
					return mascorto;
				}
			}
		}
		
		if(modo == 2)
		{
			String separador = System.getProperty("line.separator");
			Vector <String> total = new Vector<String>(1,1); 
			if(c != null)
			{
				int anterior=-1;
				for(int p=0;p < valores.length;p++)
				{
					if(valores[p] != 0)
					{
						if(anterior == -1)
						{
								mascorto = "2:"+valores[p]+"-"+cuadrados.get(p).getCoordenadas().getInfo();
								total.add(mascorto);
								anterior = valores[p];
								compro = true;
						}
						else if(anterior != -1)
						{
							boolean esta = false;
							int numero = 1;
							for(int fg = 0;fg < total.size(); fg++)
							{
								if((total.get(fg).indexOf("2:"+valores[p]+"-") >= 0))
								{
									esta=true;
									numero=fg;
								}
							}
							
							if(esta)
							{
								String elemento=total.get(numero)+cuadrados.get(p).getCoordenadas().getInfo();
								total.set(numero, elemento); 
								compro = true;
							}
							
							if(!esta)
							{
								mascorto = "2:"+valores[p]+"-"+cuadrados.get(p).getCoordenadas().getInfo();
								total.add(mascorto);
							}
						}
					}
				}
				mascorto="";
				Collections.sort(total);
				for(int fg = 0;fg < total.size(); fg++)
				{
							mascorto=mascorto+total.get(fg)+separador;
				}
				if(compro)
				{
					return mascorto;
				}
			}
		}		
		
		return null;
	}
	
	//NEW devuelve la dimension
	public Coordenadas getDimensiones()
	{
		return this.dimension;
	}
	
	//NEW convierte la matriz en un vector
	public Vector<Casilla> matrizavector()
	{
		Vector<Casilla> destinos = new Vector<Casilla>(1,1);
		for(int x=0;x < this.dimension.getFila();x++)
		{
			for(int y=0;y < this.dimension.getColumna();y++)
			{
				if((!pl[x][y].esObstaculo()) && (!pl[x][y].esComienzo()) && (!pl[x][y].esPuerta()))
				destinos.add(pl[x][y]);
			}
		}
		return destinos;
	}
	
	
	public String comparardestinosdos(Vector<Casilla> c)
	{
		int tam = c.size();
		String mascorto = "";
		int [] valores= new int[tam];
		for(int u=0;u < tam;u++)
		{
			Casilla origen = c.get(u);
			int valor=0;
			for(int y=0;y < tam;y++)
			{
				if(y != u)
				{
					if(c.get(u).esDestino()&& c.get(y).esDestino())
					{
						if(c.get(u).getCoordenadas() != null && c.get(y).getCoordenadas() != null)
						{
							int x1=origen.getCoordenadas().getFila();
							int x2=c.get(y).getCoordenadas().getFila();
							int y1=origen.getCoordenadas().getColumna();
							int y2=c.get(y).getCoordenadas().getColumna();
							int resta1 = x1 - x2;
							int resta2 = y1 - y2;
							int suma = Math.abs(resta1)+Math.abs(resta2);
							valor=valor+suma;	
						}
					}
				}
			}
			valores[u]=valor;
		}
		boolean compro=false;
		
		if(c != null)
		{
			for(int p=0;p < valores.length;p++)
			{
				if(valores[p] != 0)
				{
						mascorto = mascorto+"1:"+valores[p]+"-"+c.get(p).getCoordenadas().getInfo()+System.getProperty("line.separator");				
						compro=true;
				}
			}
			
			if(compro)
			{
				return mascorto;
			}
		}
		
		return null;
	}
}