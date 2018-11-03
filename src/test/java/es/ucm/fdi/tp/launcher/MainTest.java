package es.ucm.fdi.tp.launcher;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.es.ucm.fdi.tp.launcher.Main;

//import static main.java.es.ucm.fdi.tp.launcher;
public class MainTest {

	@Test
	public void mas3args() {
		String[] h = new String[4];
		h[0] = "was";
		h[1] = "smart";
		h[2] = "smart";
		h[3] = "prueba";
		//Main.main(h);
		assertEquals("Main con más de tres argumentos", 
				false, Main.validArguments(h));
	}
	@Test
	public void juegoinvalido(){
		String[] h = new String[3];
		h[0] = "chess";
		h[1] = "smart";
		h[2] = "console";
		//String juego = "chess";
		assertEquals("Juego que no existe",false,Main.validArguments(h));
	}

}
