package aed;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparadorSuperavitTests {

    private ComparadorSuperavit comparador;
    private Ciudad ciudad1;
    private Ciudad ciudad2;
    private Ciudad ciudad3;

    @BeforeEach
    public void init() {
        comparador = new ComparadorSuperavit();

        // Creamos algunas ciudades con diferentes superávits e IDs
        ciudad1 = new Ciudad(1);
        ciudad1.modificarSuperavit(3000);

        ciudad2 = new Ciudad(2);
        ciudad2.modificarSuperavit(5000);

        ciudad3 = new Ciudad(3);
        ciudad3.modificarSuperavit(5000); // mismo superávit que ciudad2, pero con un id mayor
    }

    @Test
    public void testCompare_MayorSuperavitEsMayor() {
        int resultado = comparador.compare(ciudad2, ciudad1);
        assertTrue(resultado > 0, "Caso arg1 > arg2, la ciudad con el mayor superávit debería ser considerada mayor");
    }

    @Test
    public void testCompare_MenorSuperavitEsMenor() {
        int resultado = comparador.compare(ciudad1, ciudad2);
        assertTrue(resultado < 0, "Caso arg1 < arg2, la ciudad con el menor superávit debería ser considerada menor");
    }

    @Test
    public void testCompare_IgualSuperavit_MenorIdEsMayor() {
        int resultado = comparador.compare(ciudad2, ciudad3);
        assertTrue(resultado < 0, "Caso arg1 < arg2, el superávit es igual, la ciudad con menor ID debería ser considerada mayor");
    }
}