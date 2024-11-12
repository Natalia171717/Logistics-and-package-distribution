package aed;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparadorSuperavitTests {

    private ComparadorSuperavit comparador;
    private Ciudad ciudadMayorSuperavit;
    private Ciudad ciudadMenorSuperavit;
    private Ciudad ciudadIgualSuperavitMenorId;
    private Ciudad ciudadIgualSuperavitMayorId;

    @BeforeEach
    public void setUp() {
        comparador = new ComparadorSuperavit();

        // Creamos ciudades con diferentes IDs y superávits
        ciudadMayorSuperavit = new Ciudad(1);
        ciudadMayorSuperavit.modificarSuperavit(5000);

        ciudadMenorSuperavit = new Ciudad(2);
        ciudadMenorSuperavit.modificarSuperavit(2000);

        ciudadIgualSuperavitMenorId = new Ciudad(3);
        ciudadIgualSuperavitMenorId.modificarSuperavit(3000);

        ciudadIgualSuperavitMayorId = new Ciudad(4);
        ciudadIgualSuperavitMayorId.modificarSuperavit(3000);
    }

    @Test
    public void testCompare_MayorSuperavitEsMayor() {
        int resultado = comparador.compare(ciudadMayorSuperavit, ciudadMenorSuperavit);
        assertTrue(resultado > 0, "La ciudad con mayor superávit debería ser considerada mayor");
    }

    @Test
    public void testCompare_MenorSuperavitEsMenor() {
        int resultado = comparador.compare(ciudadMenorSuperavit, ciudadMayorSuperavit);
        assertTrue(resultado < 0, "La ciudad con menor superávit debería ser considerada menor");
    }

    @Test
    public void testCompare_IgualSuperavit_MenorIdEsMenor() {
        int resultado = comparador.compare(ciudadIgualSuperavitMenorId, ciudadIgualSuperavitMayorId);
        assertTrue(resultado < 0, "Si el superávit es igual, la ciudad con menor ID debería ser considerada menor (arg1 < arg2)");
    }

    @Test
    public void testCompare_IgualSuperavit_MayorIdEsMayor() {
        int resultado = comparador.compare(ciudadIgualSuperavitMayorId, ciudadIgualSuperavitMenorId);
        assertTrue(resultado > 0, "Si el superávit es igual, la ciudad con mayor ID debería ser considerada mayor (arg1 > arg2)");
    }

}