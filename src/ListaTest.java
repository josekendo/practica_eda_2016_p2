import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ListaTest {

	Lista lista;
	
	/*		
	
	esperado =;
	devuelto =;
	assertEquals(esperado, devuelto);
	
	*/
	
	@Before
	public void crearLista()
	{
		lista = null;
		lista = new Lista();
	}
	
	@Test
	// Debe devolver true porque es nula la lista
	public void testEsVacia() {
		Boolean esperado = true;
		Boolean devuelto = lista.esVacia();
		assertEquals(esperado, devuelto);
	}
	
	
	@Test
	public void testInsertaCabeza() {
		ArrayList<Coordenadas> cor = null;
		lista.insertaCabeza(cor);
		boolean esperado = true;
		boolean devuelto = false;
		assertEquals(esperado, devuelto);
	}

	@Test
	public void testInsertaCola() {
		fail("Not yet implemented");
	}

	@Test
	public void testInserta() {
		fail("Not yet implemented");
	}

	@Test
	public void testBorraCabeza() {
		fail("Not yet implemented");
	}

	@Test
	public void testBorraInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testBorraArrayListOfCoordenadas() {
		fail("Not yet implemented");
	}

	@Test
	public void testEscribeLista() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnLista() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCamino() {
		fail("Not yet implemented");
	}

}
